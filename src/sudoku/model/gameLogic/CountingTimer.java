/*
 * timer class
 */
package sudoku.model.gameLogic;

import android.os.SystemClock;
import android.widget.Chronometer;

public class CountingTimer {

	private Chronometer countingTimer;

	public CountingTimer(Chronometer countingTimer) {
		super();
		this.countingTimer = countingTimer;
	}

	public void startTimer() {
		// reset timer
		countingTimer.setBase(SystemClock.elapsedRealtime());
		// start timer
		countingTimer.start();
	}

	public void stopTimer() {
		countingTimer.stop();
	}

	public void resetTimer() {
		// reset timer
		countingTimer.setBase(SystemClock.elapsedRealtime());
	}

	public String getTimer() {
		return countingTimer.getText() + "";
	}
}
