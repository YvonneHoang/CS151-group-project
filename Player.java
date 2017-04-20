public class Player {
    private int stones;
    
    public Player()
    {
       stones = 0;
    }
    
    public void addStones(int num) {
        stones +=num;    
    }
    
    public int getAmount() {
        return stones;
    }
    
    public void setStones(int n)
    {
        stones = n;
    }
}
