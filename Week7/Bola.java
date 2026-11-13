import java.awt.Color;
import java.util.Observable;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class Bola extends Observable implements DrawableBall, Runnable {
	private float estado=0;
	private Color color=new Color((int)(Math.random()*256), 
			(int)(Math.random()*256), (int)(Math.random()*256));
	private CyclicBarrier barreira;

	public Bola(CyclicBarrier barreira) {
		this.barreira = barreira;
	}


	@Override
	public void run() {
		float incremento = 0.01f + (float) (Math.random() * 0.09f);
		
		try {
			while (!bolaAtingiuLimite()) {
				estado += incremento;
				if (estado > 1.0f) {
					estado = 1.0f;
				}

				setChanged();
				notifyObservers();

				Thread.sleep(100);
			}

			barreira.await();

		} catch (InterruptedException | BrokenBarrierException e) {
			Thread.currentThread().interrupt();
		}
	}

	public boolean bolaAtingiuLimite(){
		return estado>=1;
	}

	@Override
	public Color getColor() {
		return color;
	}

	@Override
	public float getX() {
		return estado;
	}

	@Override
	public int getSize() {
		return 10;
	}

}
