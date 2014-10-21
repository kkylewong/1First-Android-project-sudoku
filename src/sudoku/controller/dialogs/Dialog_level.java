package sudoku.controller.dialogs;

import sudoku.controller.interfaces.MsgFromDialog_levelToGame;

import com.example.the_final_project_2.R;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.TextView;

/*
 * user-defined dialog
 * dialog for selecting overall levels (easy or medium or hard) on Menu activity
 */
public class Dialog_level extends Dialog {

	private static final android.view.View.OnClickListener OnClickListener = null;
	private String e_m_h;// (easy or medium or hard)
	private MsgFromDialog_levelToGame msg = null;

	public Dialog_level(Context context, String e_m_h) {
		super(context);
		this.e_m_h = e_m_h;
	}
	
	private Animation scale = AnimationUtils.loadAnimation(getContext(), R.anim.scale_anim);

	private TextView e_m_h_textView;
	private ImageButton level1_btn, level2_btn, level3_btn, level4_btn,
			level5_btn, cancel_btn;

	public void setMsgFromDialog_levelToGame(MsgFromDialog_levelToGame msg) {
		this.msg = msg;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setTitle("Choose a level");
		setContentView(R.layout.dialog_level);
		e_m_h_textView = (TextView) findViewById(R.id.e_m_h_textView);
		level1_btn = (ImageButton) findViewById(R.id.levelImageButton1);
		level2_btn = (ImageButton) findViewById(R.id.levelImageButton2);
		level3_btn = (ImageButton) findViewById(R.id.levelImageButton3);
		level4_btn = (ImageButton) findViewById(R.id.levelImageButton4);
		level5_btn = (ImageButton) findViewById(R.id.levelImageButton5);
		cancel_btn = (ImageButton) findViewById(R.id.levelCancelimageButton);

		if (e_m_h == "easy") {
			e_m_h_textView.setText("EASY");
		}
		if (e_m_h == "medium") {
			e_m_h_textView.setText("MEDIUM");
		}
		if (e_m_h == "hard") {
			e_m_h_textView.setText("HARD");
			level3_btn.setVisibility(View.INVISIBLE);
			level4_btn.setVisibility(View.INVISIBLE);
			level5_btn.setVisibility(View.INVISIBLE);
		}
		btnClicked();
	}
	
	

	private void btnClicked() {
		level1_btn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				level1_btn.startAnimation(scale);//set up image button animation
				if (e_m_h == "easy") {
					msg.sendLevelMsg(11);
				}
				if (e_m_h == "medium") {
					msg.sendLevelMsg(21);
				}
				if (e_m_h == "hard") {
					msg.sendLevelMsg(31);
				}
				dismiss();
			}
		});

		level2_btn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				level2_btn.startAnimation(scale);//set up image button animation
				if (e_m_h == "easy") {
					msg.sendLevelMsg(12);
				}
				if (e_m_h == "medium") {
					msg.sendLevelMsg(22);
				}
				if (e_m_h == "hard") {
					msg.sendLevelMsg(32);
				}
				dismiss();
			}
		});

		level3_btn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				level3_btn.startAnimation(scale);//set up image button animation
				if (e_m_h == "easy") {
					msg.sendLevelMsg(13);
				}
				if (e_m_h == "medium") {
					msg.sendLevelMsg(23);
				}
				// if(e_m_h=="hard"){msg.sendLevelMsg(33);}
				dismiss();
			}
		});

		level4_btn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				level4_btn.startAnimation(scale);//set up image button animation
				if (e_m_h == "easy") {
					msg.sendLevelMsg(14);
				}
				if (e_m_h == "medium") {
					msg.sendLevelMsg(24);
				}
				// if(e_m_h=="hard"){msg.sendLevelMsg(34);}
				dismiss();
			}
		});

		level5_btn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				level5_btn.startAnimation(scale);//set up image button animation
				if (e_m_h == "easy") {
					msg.sendLevelMsg(15);
				}
				if (e_m_h == "medium") {
					msg.sendLevelMsg(25);
				}
				// if(e_m_h=="hard"){msg.sendLevelMsg(35);}
				dismiss();
			}
		});

		cancel_btn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				cancel_btn.startAnimation(scale);//set up image button animation
				dismiss();
			}
		});
	}

	
}
