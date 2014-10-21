/*
 * Game view controller
 * its content view is game_view.xml
 */
package sudoku.controller.activities;

import sudoku.controller.dialogs.Dialog_Answer;
import sudoku.controller.dialogs.Dialog_Win;
import sudoku.controller.interfaces.OnReturnNameListener;
import sudoku.controller.interfaces.OnStateListener;
import sudoku.model.gameLogic.CountingTimer;
import sudoku.model.gameLogic.GameActivityView;
import sudoku.model.gameLogic.NewMedia;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.View;
import android.view.View.OnClickListener;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.widget.Chronometer;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.the_final_project_2.R;

public class GameActivity extends Activity implements OnStateListener,
		OnReturnNameListener {
	public static GameActivity instance = null;// for stopping the activity
	private Chronometer countingTimer;// timer parameter
	private CountingTimer theTimer;
	private GameActivityView gameView;// custom view
	private Dialog_Win winDialog;// dialog when win
	private Dialog_Answer answerDialog;// dialog when answer button is checked
	private String playerName;// player's name where user enter in win dialog
	private int theGameLevel;// parameter for the level of the game
	private int pracOrRecoOrRand;// define model is practice, record or random

	private ImageButton btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9,
			btn_clear;
	private TextView selectedNumber;
	private TextView selectedNumberLabel;
	private NewMedia newMedia;

	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 0:// receive message 0 when win
				if (pracOrRecoOrRand == 1) {
					setAlertDialog();
				}
				if (pracOrRecoOrRand == 2) {
					winDialog = new Dialog_Win(GameActivity.this,
							theTimer.getTimer() + "");
					winDialog.setOnReturnNameListener(GameActivity.this);
					winDialog.show();
				}
				theTimer.stopTimer();
				break;
			case 6:// receive message 6 when answer button is clicked
				answerDialog = new Dialog_Answer(GameActivity.this,
						theGameLevel);
				answerDialog.show();
				break;
			case 7:// quit
				setAlertDialog();
				break;
			}
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.game_view);
		// get the timer object
		countingTimer = (Chronometer) this.findViewById(R.id.chronometer);
		// get level data from Dialog_level (SecondMenu activity)
		Intent intent = getIntent();
		// 11 is the default value when no value
		theGameLevel = intent.getIntExtra("theLevel", 11);
		System.out.println("GameActivity--THELEVEL-->> " + theGameLevel);// test
		gameView = (GameActivityView) findViewById(R.id.myView);
		// define OnStateListener(interface) is this one
		gameView.setOnStateListener(this);
		gameView.setGameinView(theGameLevel);

		pracOrRecoOrRand = intent.getIntExtra("practiceOrCalcuate", 0);
		// 0 is default value when no parameter
		System.out.println("GameActivity -pracOrCalcu-->" + pracOrRecoOrRand);

		findView();
		gameView.setPracticeOrCalculate(pracOrRecoOrRand);

		instance = this;
		// set timer
		theTimer = new CountingTimer(countingTimer);
		theTimer.startTimer();

		newMedia = new NewMedia(this, 2);
		newMedia.start();
	}

	private void findView() {
		btn1 = (ImageButton) findViewById(R.id.gameImageButton1);
		btn2 = (ImageButton) findViewById(R.id.gameImageButton2);
		btn3 = (ImageButton) findViewById(R.id.gameImageButton3);
		btn4 = (ImageButton) findViewById(R.id.gameImageButton4);
		btn5 = (ImageButton) findViewById(R.id.gameImageButton5);
		btn6 = (ImageButton) findViewById(R.id.gameImageButton6);
		btn7 = (ImageButton) findViewById(R.id.gameImageButton7);
		btn8 = (ImageButton) findViewById(R.id.gameImageButton8);
		btn9 = (ImageButton) findViewById(R.id.gameImageButton9);
		btn_clear = (ImageButton) findViewById(R.id.gameImageButton_clear);
		selectedNumber = (TextView) findViewById(R.id.selectedNum);
		selectedNumberLabel = (TextView) findViewById(R.id.numLableTextView);
		if (pracOrRecoOrRand == 1 || pracOrRecoOrRand == 3) {
			btn1.setVisibility(View.GONE);
			btn2.setVisibility(View.GONE);
			btn3.setVisibility(View.GONE);
			btn4.setVisibility(View.GONE);
			btn5.setVisibility(View.GONE);
			btn6.setVisibility(View.GONE);
			btn7.setVisibility(View.GONE);
			btn8.setVisibility(View.GONE);
			btn9.setVisibility(View.GONE);
			btn_clear.setVisibility(View.GONE);
			selectedNumber.setVisibility(View.GONE);
			selectedNumberLabel.setVisibility(View.GONE);
		}
		btn1.setOnClickListener(listener);
		btn2.setOnClickListener(listener);
		btn3.setOnClickListener(listener);
		btn4.setOnClickListener(listener);
		btn5.setOnClickListener(listener);
		btn6.setOnClickListener(listener);
		btn7.setOnClickListener(listener);
		btn8.setOnClickListener(listener);
		btn9.setOnClickListener(listener);
		btn_clear.setOnClickListener(listener);
	}

	private OnClickListener listener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			ImageButton btn = (ImageButton) v;
			switch (btn.getId()) {
			case R.id.gameImageButton1:
				selectedNumber.setText("1");
				gameView.setCalcNumEnter(1);
				break;
			case R.id.gameImageButton2:
				selectedNumber.setText("2");
				gameView.setCalcNumEnter(2);
				break;
			case R.id.gameImageButton3:
				selectedNumber.setText("3");
				gameView.setCalcNumEnter(3);
				break;
			case R.id.gameImageButton4:
				selectedNumber.setText("4");
				gameView.setCalcNumEnter(4);
				break;
			case R.id.gameImageButton5:
				selectedNumber.setText("5");
				gameView.setCalcNumEnter(5);
				break;
			case R.id.gameImageButton6:
				selectedNumber.setText("6");
				gameView.setCalcNumEnter(6);
				break;
			case R.id.gameImageButton7:
				selectedNumber.setText("7");
				gameView.setCalcNumEnter(7);
				break;
			case R.id.gameImageButton8:
				selectedNumber.setText("8");
				gameView.setCalcNumEnter(8);
				break;
			case R.id.gameImageButton9:
				selectedNumber.setText("9");
				gameView.setCalcNumEnter(9);
				break;
			case R.id.gameImageButton_clear:
				selectedNumber.setText("0");
				gameView.setCalcNumEnter(0);
				break;
			}
		}
	};

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		newMedia.start();
	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		newMedia.stop();
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		newMedia.release();
	}

	public void stopTimer() {
		theTimer.stopTimer();
	}

	public String getTime() {
		return theTimer.getTimer();
	}

	@Override
	public void OnStateChanged(int StateMode) {
		switch (StateMode) {
		case GameActivityView.WIN:
			handler.sendEmptyMessage(0);
			newMedia.stop();
			MediaPlayer theplayer = new MediaPlayer();
			theplayer = MediaPlayer.create(this, R.raw.win);
			theplayer.start();
			break;
		case GameActivityView.ANSWER:
			handler.sendEmptyMessage(6);
			break;
		case GameActivityView.QUIT:
			handler.sendEmptyMessage(7);
			break;
		}
	}

	@Override
	public void returnName(String name) {
		playerName = name;
		Intent intent = new Intent(GameActivity.this, TheScoreList.class);
		// add values into the intent object
		intent.putExtra("1name", playerName);
		intent.putExtra("2time", getTime());
		intent.putExtra("3level", theGameLevel);
		// start the Intent object，achieve the jump
		startActivity(intent);
	}

	/*
	 * set the dialog when user press return button on phone
	 */
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		switch (keyCode) {
		case KeyEvent.KEYCODE_BACK:
			// show dialog
			setAlertDialog();
			break;
		default:
			break;
		}
		return true;
	}

	public void setAlertDialog() {
		AlertDialog.Builder builder = new AlertDialog.Builder(this)
				.setTitle("Warning!")
				.setMessage("Quit?")
				.setPositiveButton("YES",
						new android.content.DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								finish();
								System.exit(0);
							}
						})
				.setNegativeButton("NO",
						new android.content.DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog,
									int which) {
							}
						});
		builder.create().show();
	}

}
