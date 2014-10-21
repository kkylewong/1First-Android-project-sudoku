/*
 * 
 */
package sudoku.controller.activities;

import sudoku.controller.dialogs.Dialog_level;
import sudoku.controller.interfaces.MsgFromDialog_levelToGame;
import sudoku.controller.interfaces.OnLevelListener;
import sudoku.model.gameLogic.NewMedia;
import sudoku.model.gameLogic.SecondMenuView;

import com.example.the_final_project_2.R;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;

public class SecondMenu extends Activity implements OnLevelListener,
		MsgFromDialog_levelToGame {

	private ImageButton scoreList_btn;
	private SecondMenuView menuView;
	private Dialog_level dialogLevel;
//	private MediaPlayer player;
	private NewMedia newMedia;
	

	private int pracOrCalcu;
	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {

			switch (msg.what) {
			case 1:
				if (pracOrCalcu != 3) {
					dialogLevel = new Dialog_level(SecondMenu.this, "easy");
					dialogLevel.setMsgFromDialog_levelToGame(SecondMenu.this);
					dialogLevel.show();
				}
				if (pracOrCalcu == 3) {
					Intent intent = new Intent(SecondMenu.this, GameActivity.class);
					intent.putExtra("theLevel", 1);
					intent.putExtra("practiceOrCalcuate", pracOrCalcu);
					System.out.println("secondMenu- practiceOrCalcuate-->" + pracOrCalcu);
					startActivity(intent);
				}
				break;
			case 2:
				if (pracOrCalcu != 3) {
					dialogLevel = new Dialog_level(SecondMenu.this, "medium");
					dialogLevel.setMsgFromDialog_levelToGame(SecondMenu.this);
					dialogLevel.show();
				}
				if (pracOrCalcu == 3) {
					Intent intent = new Intent(SecondMenu.this, GameActivity.class);
					intent.putExtra("theLevel", 2);
					intent.putExtra("practiceOrCalcuate", pracOrCalcu);
					System.out.println("secondMenu- practiceOrCalcuate-->" + pracOrCalcu);
					startActivity(intent);
				}
				break;
			case 3:
				dialogLevel = new Dialog_level(SecondMenu.this, "hard");
				dialogLevel.setMsgFromDialog_levelToGame(SecondMenu.this);
				dialogLevel.show();
				break;
			}
			

		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.second_menu);
		scoreList_btn = (ImageButton) findViewById(R.id.scoreList_btn);
		menuView = (SecondMenuView) findViewById(R.id.menuView);
		menuView.setOnLevelListener(this);
		scoreList_btn.setOnClickListener(listener);
		
//		player = MediaPlayer.create(this, R.raw.back1);
//		player.setLooping(true);
//		player.start();
		newMedia=new NewMedia(this, 1);
		newMedia.start();
		
		Intent intent = getIntent();
		pracOrCalcu = intent.getIntExtra("practiceOrCalculate", 0);
		// 0 is default value when no parameter
		menuView.setPracOrCalcu(pracOrCalcu);
		
		if(pracOrCalcu==1||pracOrCalcu==3){
			scoreList_btn.setVisibility(View.GONE);
		}

	}

	@Override
	protected void onPause() {
		super.onPause();
		newMedia.pause();
	}

	@Override
	protected void onStop() {
		super.onStop();
		newMedia.pause();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		newMedia.pause();
	}

	@Override
	protected void onResume() {
		super.onResume();
		newMedia.start();
	}

	private OnClickListener listener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			Intent intent = new Intent();
			switch (v.getId()) {

			case R.id.scoreList_btn:
				Animation shake = AnimationUtils.loadAnimation(SecondMenu.this,
						R.anim.shake);
				scoreList_btn.startAnimation(shake);
				intent.setClass(SecondMenu.this, TheScoreList.class);
				break;

			}
			startActivity(intent);
		};
	};

	// Receive level parameter
	@Override
	public void OnlevelChanged(int Statelevel) {
		switch (Statelevel) {
		case SecondMenuView.EASY:
			handler.sendEmptyMessage(1);
			break;
		case SecondMenuView.MEDIUM:
			handler.sendEmptyMessage(2);
			break;
		case SecondMenuView.HARD:
			handler.sendEmptyMessage(3);
			break;
		}
	}

	@Override
	public void sendLevelMsg(int Level) {
		Intent intent = new Intent(SecondMenu.this, GameActivity.class);
		intent.putExtra("theLevel", Level);
		intent.putExtra("practiceOrCalcuate", pracOrCalcu);
		System.out.println("secondMenu- practiceOrCalcuate-->" + pracOrCalcu);
		startActivity(intent);
	}
}
