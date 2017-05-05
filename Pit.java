/**
 * A helper model class for MancalaModel that stores
 * number of stones in a pit.
 * 
 * @author Eric Au
 */
public class Pit 
{
    private int amount;
    
    /**
     * Finds the number of stones in pits.
     * @return number of stones
     */
    public int getAmount() 
    {
        return amount;
    }
    
    /**
     * Add a stone to the pit.
     */
    public void addStone()
    {
       amount++;
    }
    /**
     * Removes all stones in pit.
     * @return number of stones that was in the pit
     */
    public int take()
    {
       int result = amount;
       amount = 0;
       return result;
    }
    
    /**
     * Sets number of stones in pit.
     * @param stones initial number to be set
     */
    public void setStones(int stones)
    {
        amount = stones;
    }

}