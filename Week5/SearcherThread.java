import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.CountDownLatch;


class SearcherThread extends Thread{
		private String myText;
		private String textToFind;
		// private CyclicBarrier barrier;
		private CountDownLatch latch;
		private int result=-1;

		public SearcherThread(String myText, String textToFind, CountDownLatch latch) {
			this.myText = myText;
			this.textToFind = textToFind;
			// this.barrier = barrier;
			this.latch = latch;
		}

		public String getMyText() {
			return myText;
		}

		public int getResult() {
			return result;
		}

		@Override
		public void run() {
			result=myText.indexOf(textToFind);
			// try {
				//  barrier.await();
			latch.countDown();
			System.err.println("Thread finishing at:"+System.currentTimeMillis());
			// } catch (InterruptedException | 
			// 		BrokenBarrierException e) {
			// }
		}
		
		
	}

