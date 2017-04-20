package mancala;

import java.util.*;
import javax.swing.event.*;


public class MancalaModel {
    private ArrayList<ChangeListener> cListeners;
    private char player;
    private Pit[] a;   
    private Pit[] b;   
    private Player mA;
    private Player mB;
    private boolean undoTruth;
    private char undoMove;
    private int[] undoValues;
    private int undoCount;
    
    /*
     * constructor
     * @param pick number of stones in each pit
     * 
     */
    public MancalaModel(int numberOfStones) {
        
        a = new Pit[6];
        for(int i =0; i < 6;i++) {
            a[i] = new Pit(numberOfStones);
        }
        b = new Pit[6];
        for(int i =0; i < 6;i++) {
            b[i] = new Pit(numberOfStones);
        }
        player = 'a';
        mA = new Player();
        mB = new Player();
        undoTruth = false;
        undoCount = 3;
        undoValues = new int[14];
       // undoReset = 0;
        cListeners = new ArrayList<ChangeListener>();
    }
    
    public void performTurn(char currentPlayer, int pitNum) {
        int pitValue = getPitValue(player, pitNum);
        if(pitValue==0|| player != currentPlayer) {
            //nothing happens and we close the loop
            return;
        }

        
        saveState(); 
        

        
        //move stones to the next pit if possible
        
        int tempPit = pitNum+1;
        boolean goAgain = false;
        
        while (pitValue!=0) {
            
            while (tempPit < 6 && pitValue != 0)
            {
                getPits(currentPlayer)[tempPit].addStone();
                pitValue--;  
                tempPit++;
            }
            // case 1 your mancala
            if (pitValue > 0)
            {
                getPlayer(currentPlayer).addStones(1);
                pitValue--;
                goAgain = true;
            }
            // case 2 opp's mancala
            if (pitValue > 0)
            {
                currentPlayer = getOppPlayer(currentPlayer);
                tempPit = 0;
                goAgain = false;
                while (tempPit < 6 && pitValue != 0)
                {
                    getPits(currentPlayer)[tempPit].addStone();
                    pitValue--;
                    tempPit++;
                }
            
            }
            //loop again for remaining stones
            if(pitValue >0) {
                tempPit = 0;
                currentPlayer = getOppPlayer(currentPlayer);
            }
        }
        
        //case where your turn ends, go again or not
        if(!goAgain) {
            if (player == 'a')
                player = 'b';
            else
                player = 'a';
            
            if (player == currentPlayer && getPitValue(currentPlayer, tempPit-1) == 1)
            {
                char oppPlayer = getOppPlayer(currentPlayer);
                int oppPit = oppPit(tempPit - 1);
                int giveValue = getPitValue(oppPlayer, oppPit) + 1;
                getPlayer(currentPlayer).addStones(giveValue);
                getPits(currentPlayer)[tempPit-1].takeAll();
                getPits(oppPlayer)[oppPit].takeAll();
            }
        }
        getPits(currentPlayer)[pitNum].takeAll();
        update(); 
    }
    
    public void undo()
    {
        
        if(undoTruth==false || undoCount==0) {
            return;
        }
        player = undoMove;
        for (int i = 0; i < 6; i++)
            getPits('a')[i].setStones(undoValues[i]);
        getPlayer('a').setStones(undoValues[6]);
        for (int i = 7; i < 13; i++)
            getPits('b')[i-7].setStones(undoValues[i]);
        getPlayer('b').setStones(undoValues[13]);
        undoTruth = false;
        undoCount--;
        update();
        
    }
    
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
            {
                mB.addStones(b[i].takeAll());
            }
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
                {
                mA.addStones(a[i].takeAll());
                }
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
    
    
    public char getCurrentPlayer()
    {
        return player;
    }

    
    public int getPitValue(char player, int pitNumber)
    {
       if (player == 'a')
          return a[pitNumber].getAmount();
       else if (player == 'b')
          return b[pitNumber].getAmount();
       return -1;
    }
    
    /*
     * return undo status
     * @return true or false
     */
    public boolean undoTruth() {
        return undoTruth;
    }
    
    public int getUndoCount()
    {
        return undoCount;
    }
    
    /*
     * a method to update views
     */
    public void update() {
        for(ChangeListener l : cListeners)
        {
            l.stateChanged(new ChangeEvent(this));
        }
    }
    
    public void attach(ChangeListener c)
    {
       cListeners.add(c);
    }
    
    
    /*
     * another helper method to return the pits
     */
    
    private Pit [] getPits(char side)
    {
        if (side == 'a')
            return a;
        else if (side == 'b')
            return b;
        
        return null;
    }
    
    private Player getPlayer(char player)
    {
        if (player == 'a')
            return mA;
        else if (player == 'b')
            return mB;
        
        return null;
    }

    public int getPlayerValue(char player)
    {
       if (player == 'a')
          return mA.getAmount();
       else if (player == 'b')
          return mB.getAmount();
       
       System.out.println("Error: getMancalaValue()");
       return -1;
    }
    
    

    
    private char getOppPlayer(char player) {
        return (player == 'a' ? 'b' : 'a');
    }
    
    private void saveState()
    {
        for (int i = 0; i < 6; i++)
            undoValues[i] = getPitValue('a', i);
        undoValues[6] = getPlayerValue('a');
        for (int i = 7; i < 13; i++)
            undoValues[i] = getPitValue('b', i-7);
        undoValues[13] = getPlayerValue('b');
        undoMove = player;  
        if (undoCount > 0)
            undoTruth = true;
    }
    
}
