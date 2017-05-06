import java.util.*;
import javax.swing.event.*;

/**
 * Model class that stores all game data for an instance of
 * a Mancala game.
 * 
 * Also provides methods defining legal player actions.
 * 
 * @author Eric Au
 */

public class MancalaModel
{
    private ArrayList<ChangeListener> cListeners;
    private char currentPlayer;
    private Pit[] a;   
    private Pit[] b;   
    private Player mA;
    private Player mB;
    private boolean undoTruth;
    private char undoWho;
    
    //store what to undo
    private int[] undoValue;
    private int undoCount;
    private int undoReset;
    
    /**
     * Sets values to default values and creates empty
     * Pit objects.
     */
    public MancalaModel()
    {
        currentPlayer = 'a';
        a = new Pit[6];
        b = new Pit[6];
        for(int i =0; i < 6;i++)
        {
            a[i] = new Pit();
        }
        for(int i =0; i < 6;i++)
        {
            b[i] = new Pit();
        }
        mA = new Player();
        mB = new Player();
        undoTruth = false;
        undoCount = 3;
        undoReset = 0;
        undoValue = new int[14];
        cListeners = new ArrayList<ChangeListener>();
    }
    
    /**
     * Sets initial number of stones to fill pits with at
     * beginning of the game. 
     * precondition: only called at beginning of a mancala game.
     * @param numberOfStones number of stones to put in pits.
     * <br>Should be no more than 4 stones max.
     */
    public void setStoneCount(int numberOfStones) 
    {
        for(int i =0; i < 6;i++)
            a[i].setStones(numberOfStones);
        for(int i =0; i < 6;i++)
        	b[i].setStones(numberOfStones);
        for(ChangeListener l : cListeners)
            l.stateChanged(new ChangeEvent(this));
    }
    
    /**
     * Saves current state of board, such as stones in a pit/mancala
     * and whether or not player can undo.
     */
    public void saveState()
    {
        //loop for your own stones
        for(int i =0; i <6;i++)
            undoValue[i] = getPitValue('a',i);
        
        undoValue[6] = getMancalaValue('a');
        
        //second loop for opp pits plus 1 for where your points are stored 
        for(int j = 7; j<13;j++)
            undoValue[j] = getPitValue('b',j-7);           

        undoValue[13] = getMancalaValue('b');
        
        undoWho = currentPlayer;
        
        if(undoCount > 0)
            undoTruth = true;
    }
    
    /**
     * Updates all views.
     */
    public void update()
    {
        for(ChangeListener l : cListeners)
            l.stateChanged(new ChangeEvent(this));
    }

    /**
     * Defines logic for when a player selects a pit (performing their turn).
     * @param p the player the selected pit belongs to
     * @param pitNum the number/position of the pit
     */
    public void performTurn(char p, int pitNum) {
        int pitValue = getPitValue(currentPlayer, pitNum);
        if(pitValue==0|| p!= currentPlayer) {
            //nothing happens and we close the loop
            return;
        }
        saveState();
        char node = p;

        
        int tempPit = pitNum+1;
        boolean goAgain = false;
        
        while (pitValue!=0) {
            
            while (tempPit < 6 && pitValue != 0)
            {
                getPits(node)[tempPit].addStone();
                pitValue--;  
                tempPit++;
            }
            // case 1 your mancala
            if (pitValue > 0)
            {
                getPlayer(node).addStones(1);
                pitValue--;
                goAgain = true;
            }
            // case 2 opp's mancala
            if (pitValue > 0)
            {
                node = getOppPlayer(node);
                tempPit = 0;
                goAgain = false;
                while (tempPit < 6 && pitValue != 0)
                {
                    getPits(node)[tempPit].addStone();
                    pitValue--;
                    tempPit++;
                }
            
            }
            //loop again for remaining stones
            if(pitValue >0) {
                tempPit = 0;
                node = getOppPlayer(node);
            }
        }
        
        //case where your turn ends, go again or not
        if(!goAgain) {
            if (currentPlayer == 'a')
                currentPlayer = 'b';
            else
                currentPlayer = 'a';
            
            //ended on empty pit
            if (node == p && getPitValue(p, tempPit-1) == 1)
            {
                char oppPlayer = getOppPlayer(p);
                int oppPit = oppPit(tempPit-1);
                int giveValue = getPitValue(oppPlayer, oppPit) + 1;
                getPlayer(p).addStones(giveValue);
                getPits(p)[tempPit-1].take();
                getPits(oppPlayer)[oppPit].take();
            }
        }
       getPits(p)[pitNum].take();
        update(); 
    }
    /**
     * Resets state of the board to previously saved state,
     * undoing the most recent turn.
     */
    public void undo()
    {
        if(undoTruth==false||undoCount==0)
            return;

        currentPlayer = undoWho;
        for(int i=0; i < 6;i++)
            getPits('a')[i].setStones(undoValue[i]);                 
        getPlayer('a').setStones(undoValue[6]);
       
        for(int j =7; j < 13; j++)
            getPits('b')[j-7].setStones(undoValue[j]);
        getPlayer('b').setStones(undoValue[13]);
        
        undoTruth = false;
        undoCount--;
        undoReset--;

        update();
    }
    
    /**
     * Resets the board to default values in preparation
     * of another game.
     */
    public void reset() {
        for(int i =0; i < 6;i++)
            a[i] = new Pit();
        
        for(int i =0; i < 6;i++)
            b[i] = new Pit();
        
        mA = new Player();
        mB = new Player();
        undoTruth = false;
        undoCount = 3;
        
        update();
        
    }
    
    /**
     * Helper method to get the number of the opposite pit.
     */
    private int oppPit(int pit)
    {
        switch (pit)
        {
	        case 0:
	            return 5;
	        case 1:
	            return 4;
	        case 2:
	            return 3;
	        case 3:
	            return 2;
	        case 4:
	            return 1;
	        case 5:
	            return 0;
	        default:
	            return -1;       
        }
    }
    
    /**
     * Helper method to get the identity of the opposing player.
     * @param player current player
     * @return the other player
     * <br>'a' if current player is 'b', 'b' if current player is a
     */
    private char getOppPlayer(char player) 
    {
        return (player == 'a' ? 'b' : 'a');
    }
    
    /**
     * Checks if game has ended, and if so, finds the winning player
     * by comparing mancala amounts.
     * @return identity of the winning player
     * postcondition: 'a' if player a has won, 'b' if player b has won,
     * 				  'c' if game has not ended and there is no winner
     */
    public char checkWinner()
    {
        // Check if A side has no stones
        boolean gameOver = true;
        for (int i = 0; i < 6; i++)
        {
            if (a[i].getAmount() != 0)
                gameOver = false;
        }
        if (gameOver)
        {
            for (int i = 0; i < 6; i++)
                mB.addStones(b[i].take());
        }
        // Check if B side has no stones
        if (gameOver == false)
        {
            gameOver = true;
            for (int i = 0; i < 6; i++)
            {
                if (b[i].getAmount() != 0)
                    gameOver = false;
            }    
            if (gameOver)
            {
                for (int i = 0; i < 6; i++)
                	mA.addStones(a[i].take());
            }
        }
        // Game is over
        if (gameOver)
        {
            update();
            if (mA.getAmount() > mB.getAmount())
                return 'a';
            else
                return 'b';
        }       
        return 'c';
    }
    
    /**
     * Helper method that finds the player whose turn it is
     * @return player 'a' or player 'b'
     */
    private char getCurrentPlayer()
    {
        return currentPlayer;
    }

    /**
     * Finds the number of stones in a pit.
     * @param player the owner of the pit
     * @param pitNumber the position of the pit
     * @return the number of stones inside
     */
    public int getPitValue(char player, int pitNumber)
    {
       if (player == 'a')
          return a[pitNumber].getAmount();
       else if (player == 'b')
          return b[pitNumber].getAmount();
       return -1;
    }
    
	/**
	 * Finds the number of stones in a player's mancala
	 * @param player the player in question
	 * @return number of stones in the player's mancala
	 */
    public int getMancalaValue(char player)
    {
       if (player == 'a')
          return mA.getAmount();
       else if (player == 'b')
          return mB.getAmount();
       return -1;
    }
    
    /**
     * Returns whether an undo is possible.
     * @return true if undo possible, else false
     */
    public boolean undoTruth()
    {
        return undoTruth;
    }
    
    /**
     * Finds the number of undos the player has
     * performed.
     * @return number of undos previously done
     */
    public int getUndoCount()
    {
        return undoCount;
    }
    
    /**
     * Adds a ChangeListener to the list of what model
     * will update when there is a change.
     * @param c the ChangeListener to be added
     */
    public void attach(ChangeListener c)
    {
       cListeners.add(c);
    }
    
    
    /**
     * A helper method that finds all the pits belonging
     * to a given player.
     * @param side the player
     */
    private Pit[] getPits(char side)
    {
        if (side == 'a')
            return a;
        else if (side == 'b')
            return b;
        
        return null;
    }
    
    /**
     * A helper method to find a player's mancala, given a char id.
     * @return a Player object that contains details of the player's mancala.
     */
    private Player getPlayer(char player)
    {
        if (player == 'a')
            return mA;
        else if (player == 'b')
            return mB;
        
        return null;
    }
}
