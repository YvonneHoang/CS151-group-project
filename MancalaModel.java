
import java.util.*;

import javax.swing.JOptionPane;
import javax.swing.event.*;


public class MancalaModel {
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
    
    /*
     * constructor
     * @param pick number of stones in each pit
     * 
     */
    public MancalaModel() {
        currentPlayer = 'a';
        a = new Pit[6];
        b = new Pit[6];
        for(int i =0; i < 6;i++) {
            a[i] = new Pit();
        }
        for(int i =0; i < 6;i++) {
            b[i] = new Pit();
        }
        mA = new Player();
        mB = new Player();
        undoTruth = false;
        undoCount = 3;
        undoReset = 0;
        undoValue = new int[14];
       // undoReset = 0;
        cListeners = new ArrayList<ChangeListener>();
    }
    
    public void setStoneCount(int numberOfStones) {
        for(int i =0; i < 6;i++) {
            a[i].setStones(numberOfStones);
        }
        for(int i =0; i < 6;i++) {
            b[i].setStones(numberOfStones);
        }
        
        for(ChangeListener l : cListeners)
            l.stateChanged(new ChangeEvent(this));
    }
    
    public void saveState()
    {
        //loop for your own stones
        for(int i =0; i <6;i++) {
            undoValue[i] = getPitValue('a',i);
        }
        undoValue[6] = getMancalaValue('a');
        
        //second loop for opp pits plus 1 for where your points are stored
        
        for(int j = 7; j<13;j++) {
            undoValue[j] = getPitValue('b',j-7);           
        }
        undoValue[13] = getMancalaValue('b');
        undoWho = currentPlayer;
        if(undoCount > 0) {
            undoTruth = true;
        }
    }
    
    /*
     * a method to update views
     */
    public void update() {
        for(ChangeListener l : cListeners)
            l.stateChanged(new ChangeEvent(this));
        for(int i = 0; i < 6; i++)
        {
            Pit p = a[i];
            System.out.println("A[" + i + "] = " + p.getAmount());
        }
        for(int i = 0; i < 6; i++)
        {
            Pit p = b[i];
            System.out.println("B[" + i + "] = " + p.getAmount());
        }
        System.out.println("M[" +mA.getAmount() + "] " + "M2[" + mB.getAmount() + "]" );
    }

    public void performTurn(char p, int pitNum) {
        int pitValue = getPitValue(currentPlayer, pitNum);
        if(pitValue==0|| p!= currentPlayer) {
            //nothing happens and we close the loop
            return;
        }       
        saveState(); 
        
        //pointer to current player acts like a node?
        char node = p;
        
        getPits(currentPlayer)[pitNum].take();
        
        //move stones to the next pit if possible
       
        
        
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
            
            if (currentPlayer == p && getPitValue(p, tempPit-1) == 1)
            {
                char oppPlayer = getOppPlayer(p);
                int oppPit = oppPit(tempPit - 1);
                int giveValue = getPitValue(oppPlayer, oppPit) + 1;
                getPlayer(currentPlayer).addStones(giveValue);
                getPits(currentPlayer)[tempPit-1].take();
                getPits(oppPlayer)[oppPit].take();
            }
        }
      //  getPits(currentPlayer)[pitNum].take();
        update(); 
    }
    
    public void undo()
    {
        if(undoTruth==false||undoCount==0) {
            return;
        }
        currentPlayer = undoWho;
        for(int i=0; i < 6;i++) {
            getPits('a')[i].setStones(undoValue[i]);           
        }
        getPlayer('a').setStones(undoValue[6]);
        for(int j =7; j < 13; j++) {
            getPits('b')[j-7].setStones(undoValue[j]);
        }
        getPlayer('b').setStones(undoValue[13]);
        undoTruth = false;
        undoCount--;
        undoReset--;
        System.out.println(undoReset);
        update();

    }
    
    public void reset() {
        for(int i =0; i < 6;i++) {
            a[i] = new Pit();
        }
        for(int i =0; i < 6;i++) {
            b[i] = new Pit();
        }
        mA = new Player();
        mB = new Player();
        undoTruth = false;
        undoCount = 3;
        int stones = 0;
        while(stones == 0 && stones < 5)
        {
            String input = JOptionPane.showInputDialog(null, "Enter the number of stones to be placed in each pit (max 4):");
            if(Character.isDigit(input.charAt(0)))
                stones = Integer.parseInt(input);
        }
        setStoneCount(stones);
        update();
        
    }
    
    /*
     * helper method to get opposite pit number
     */
    public int oppPit(int pit) {
        switch (pit) {
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
    
    public char getOppPlayer(char player) {
        return (player == 'a' ? 'b' : 'a');
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
                mB.addStones(b[i].take());
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
                mA.addStones(a[i].take());
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
    
    public void endTurn() {
        
    }
    
    public char getCurrentPlayer()
    {
        return currentPlayer;
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
    
   
    
    public void attach(ChangeListener c)
    {
       cListeners.add(c);
    }
    
    
    /*
     * another helper method to return the pits
     */
    
    public Pit [] getPits(char side)
    {
        if (side == 'a')
            return a;
        else if (side == 'b')
            return b;
        
        return null;
    }
    /*
     * helper method to get current player
     */
    public Player getPlayer(char player)
    {
        if (player == 'a')
            return mA;
        else if (player == 'b')
            return mB;
        
        return null;
    }
/*
 * another helper method to get mancala value
 */
    public int getMancalaValue(char player)
    {
       if (player == 'a')
          return mA.getAmount();
       else if (player == 'b')
          return mB.getAmount();
       return -1;
    }
}
