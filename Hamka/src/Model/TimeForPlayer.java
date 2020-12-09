package Model;

public class TimeForPlayer implements Runnable {

	private int second;
	private int mints;

	public int getSecond() {
		return second;
	}

	public void setSecond(int second) {
		this.second = second;
	}

	public void reset() {
		this.second = 0;
		this.mints = 0;
	}

	public TimeForPlayer() {
		this.second = 0;
		this.mints = 0;
	}

	@Override
	public void run() {
		while (Game.notFinished) {
			second++;
			if (second >= 60) {
				second = 0;
				mints++;
			}

			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

}
