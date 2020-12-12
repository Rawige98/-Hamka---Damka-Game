package Model;

import java.awt.Color;

public class TimeForPlayer implements Runnable {

	private int second;
	private int mints;
	ColorTilesHandler handler;

	public int getSecond() {
		return second;
	}

	public int getMints() {
		return mints;
	}

	public void setMints(int mints) {
		this.mints = mints;
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

	public TimeForPlayer(ColorTilesHandler handler) {
		this.second = 0;
		this.mints = 0;
		this.handler = handler;
	}

	@Override
	public void run() {
		while (Game.notFinished) {
			second++;
			if (second >= 60) {
				second = 0;
				mints++;
			}
			if(second == 30 && mints ==0) 
				handler.showColor(Color.green);
			if(second == 30 && mints == 1)
				handler.showColor(Color.orange);
			
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}
	
}
