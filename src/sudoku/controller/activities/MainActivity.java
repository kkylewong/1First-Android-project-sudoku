package sudoku.controller.activities;

import com.example.the_final_project_2.R;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
//This is the main activity of this application
public class MainActivity extends Activity {
	private ImageButton play_btn;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.home);
		play_btn = (ImageButton) findViewById(R.id.imagePlayButton);
		play_btn.setOnClickListener(listener);
		Animation scale = AnimationUtils.loadAnimation(this, R.anim.scale_anim);
		//set up image button animation
		play_btn.startAnimation(scale);
	}

	//!!!!!!问题：点answer得时候无字 在gameview没设置
	private OnClickListener listener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			Intent intent = new Intent();
			switch (v.getId()) {
			case R.id.imagePlayButton:
				Animation scaleOut = AnimationUtils.loadAnimation(MainActivity.this,
				R.anim.scale_anim_out);
				play_btn.startAnimation(scaleOut);
				intent.setClass(MainActivity.this, FirstMenu.class);
				break;
			}
			startActivity(intent);
		};
	};
}
