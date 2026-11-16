
public class Javali{
    private final String cozinheiro;
    private final int numOrdem;

    public Javali(String cozinheiro, int numOrdem){
        this.cozinheiro = cozinheiro;
        this.numOrdem = numOrdem;
    }

    public String getIdCozinheiro(){
        return cozinheiro;
    }

    public int getNumOrdem(){
        return numOrdem;
    }

    @Override
    public String toString() {
        return "Javali #" + numOrdem + " (Cozinheiro: " + cozinheiro + ")";
    }
}