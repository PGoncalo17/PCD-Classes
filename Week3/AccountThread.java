import java.util.Random;

public class AccountThread extends Thread {
    Random random = new Random();
    private final Account account;
    private double totalDepositsPerClient = 0;

    public AccountThread(Account account){
        this.account = account;
    }

    private double getTotalDepositsPerClient(){
        return totalDepositsPerClient;
    }

    @Override 
    public void run(){
        while(!Thread.interrupted()){
            int randomDeposit = random.nextInt(101);
            account.deposit(randomDeposit);
            totalDepositsPerClient += randomDeposit;
        }
    }

    public static void main(String[] args){
        int totalClients = 10;
        Account sharedAccount = new Account();
        AccountThread[] threads = new AccountThread[totalClients];

        for(int i = 0; i < totalClients; i++){
            threads[i] = new AccountThread(sharedAccount);
            threads[i].start();
        }
        try{
        sleep(10000);
        } catch (InterruptedException e) {}

        for(int i = 0; i < totalClients; i++){
            threads[i].interrupt();
        }

        for(int i = 0; i < totalClients; i++){
            try{
                threads[i].join();
            } catch(InterruptedException e) {}
        }

        double totalDeposited = 0;
        for(int i = 0; i < totalClients; i++){
            totalDeposited += threads[i].getTotalDepositsPerClient();
        }

        System.out.println("Account value: " + sharedAccount.getBalance() + "€");
        System.err.println("Total value deposited (All the clients): " + totalDeposited + "€");
    }
}