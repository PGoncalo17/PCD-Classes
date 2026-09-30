public class Account {
    private double value;

    public synchronized void deposit(double amount){
        value = value + amount;
    }

    public synchronized double getBalance(){
        return value;
    }
}