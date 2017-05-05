/**
 * A helper model class for MancalaModel that stores
 * number of stones in a player's mancala.
 * 
 * @author Eric Au
 */
public class Player 
{
    private int stones = 0;
    
    /**
     * Adds a certain number of stones to the mancala.
     * @param num the number of stones to be added
     */
    public void addStones(int num) 
    {
        stones +=num;    
    }
    
    /**
     * Finds amount of stones in mancala.
     * @return number of stones in mancala
     */
    public int getAmount() 
    {
        return stones;
    }
    
    /**
     * Sets number of stones in mancala.
     * @param n number of stones to be set
     */
    public void setStones(int n)
    {
        stones = n;
    }
}