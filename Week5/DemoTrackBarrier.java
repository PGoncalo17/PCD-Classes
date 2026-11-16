import javax.swing.JFrame;

public class DemoTrackBarrier extends Thread {

	public static void main(String[] args) {
		
		int numCars = 10;
		int numSteps = 100;
        CarBarrier.initRace(numCars);

		JFrame frame = new JFrame("Demo Track - Race with Barrier");
		Track track = new Track(numCars, numSteps);
		
		for(int i = 0; i < numCars; i++){
			CarBarrier car = new CarBarrier(i, numSteps);
			car.addObserver(track);
			Thread t = new Thread(car);
			t.start();
		}
		
		frame.add(track);
        frame.setSize(500, Math.max(300, numCars * 40));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
	}

}
