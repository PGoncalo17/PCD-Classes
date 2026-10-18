
public class Gaules extends Thread {
    private Table table;
    private int idGaules;
    private int maxJavalis = 10;

    public Gaules(Table table, int idGaules){
        this.table = table;
        this.idGaules = idGaules;
    }
    
    public int getIdGaules(){
        return idGaules;
    }
    
    @Override 
    public void run(){
        try {
            for(int i = 0; i < maxJavalis; i++){
                table.serveFood(this.getIdGaules());
                Thread.sleep(150);
            }
        } catch (Exception e) { }
    }
}