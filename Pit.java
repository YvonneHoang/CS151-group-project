public class Pit {
    private int amount;
    
    public Pit(int initialAmount)
    {
       amount = initialAmount; 
    }
    /*
     * method to return user the number of stones in pits.
     */
    public int getAmount() {
        return amount;
    }
    
    /**
     *  Add a stone to pit.
     */
    public void addStone()
    {
       amount++;
    }
    public int takeAll()
    {
       int result = amount;
       amount = 0;
       return result;
    }
    
    public void setStones(int stones)
    {
        amount = stones;
    }

}
