import javax.swing.JFrame;

public class DemoTrack extends Thread {

	public static void main(String[] args) {
		
		int numCars = 10;
		int numSteps = 100;

		JFrame frame = new JFrame("Demo Track");
		Track track = new Track(numCars, numSteps);
		
		for(int i = 0; i < numCars; i++){
			Car car = new Car(i, numSteps);
			car.addObserver(track);
			Thread t = new Thread(car);
			Car.addThread(t);
			t.start();
		}
		
		frame.add(track);
		frame.setSize(500, 300);
		frame.setVisible(true);
	}

}
