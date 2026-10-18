
public class Table{

    private Javali[] array = new Javali[10];
    private int countJavalis = 0;
    private int in = 0;
    private int out = 0;


    public synchronized  void addFood(Javali javali){
        while(countJavalis == array.length){
            try{
                this.wait();
            } catch (InterruptedException e){ }
        } 
            array[in] = javali;
            in = (in + 1) % array.length;
            countJavalis ++;

            System.out.println("[COZINHEIRO " + javali.getIdCozinheiro() + "] Colocou o Javali nº " + javali.getNumOrdem() + " | Total na mesa: " + countJavalis);

            notifyAll();
    }

    public  synchronized Javali serveFood(int idGaules){
        while(countJavalis < 1){
            try {
                this.wait();
            } catch (InterruptedException e) { }
        }
        Javali javali = array[out];
        array[out] = null;
        out = (out + 1) % array.length;
        countJavalis --;

        System.out.println("  [GAULÊS " + idGaules + "] Comeu o Javali nº " + javali.getNumOrdem() + " (feito pelo Cozinheiro " + javali.getIdCozinheiro() + ") | Restam na mesa: " + countJavalis);

        notifyAll();
        return javali;
    }
}