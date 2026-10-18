
public class Cozinheiro extends Thread {
    private int idCozinheiro;
    private int maxJavalis = 10;
    private Table table;

    public Cozinheiro(Table table, int idCozinheiro){
        this.table = table;
        this.idCozinheiro = idCozinheiro;
    }

    private int getIdCozinheiro(){
        return idCozinheiro;
    }


    @Override 
    public void run(){
        try {
            for(int i = 0; i < maxJavalis; i ++){
                Javali j = new Javali(this.getIdCozinheiro(), i + 1);
                table.addFood(j);
                Thread.sleep(100);
            } 
        } catch (Exception e) { }
    }
}