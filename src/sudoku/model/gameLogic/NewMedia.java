package sudoku.model.gameLogic;

import com.example.the_final_project_2.R;

import android.content.Context;
import android.media.MediaPlayer;

public class NewMedia extends MediaPlayer{

	private MediaPlayer player;
	private Context thecontext;
	public NewMedia(Context context, int numOfMusic) {
		super();
		this.thecontext = context;
		if(numOfMusic ==1){
			player = MediaPlayer.create(thecontext, R.raw.back1);
		}
		if(numOfMusic==2){
			player = MediaPlayer.create(thecontext, R.raw.back2);
		}
		player.setLooping(true);
	}

	@Override
	public void start() throws IllegalStateException {
		player.start();
		super.start();
	}

	@Override
	public void pause() throws IllegalStateException {
		player.pause();
		super.pause();
	}

	@Override
	public void release() {
		player.release();
		super.release();
	}

	@Override
	public void stop() throws IllegalStateException {
		player.stop();
		super.stop();
	}
}
