
public class Main extends Thread{


    public static void main(String[] args) {
        int numThreads = 5;
        Table sharedTable = new Table();

        Cozinheiro[] threadsC = new Cozinheiro[numThreads];
        Gaules[] threadsG = new Gaules[numThreads];

        for(int i =0; i < numThreads; i++){
            threadsC[i] = new Cozinheiro(sharedTable, i+1);
            threadsG[i] = new Gaules(sharedTable, i+1);

            threadsC[i].start();
            threadsG[i].start();
        }   

        for(int i = 0; i < numThreads; i++){
            try {
                threadsC[i].join();
                threadsG[i].join();
            } catch (Exception e) { }
        }
        
        System.out.println("\nO Banquete terminou com sucesso!");
    }
}