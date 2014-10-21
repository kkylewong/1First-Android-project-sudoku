/*
 * its content view is first_menu.xml
 */
package sudoku.controller.activities;

import com.example.the_final_project_2.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;

public class FirstMenu extends Activity {

	private ImageButton praticeBtn, recordBtn, randomButton;
	// if pracOrCalcOrRand= 1: practicing game model,
	// if 2: recording model, if 3: random model
	private int pracOrRecoOrRand;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.first_menu);
		praticeBtn = (ImageButton) findViewById(R.id.practiceImageButton);
		recordBtn = (ImageButton) findViewById(R.id.recordImageButton);
		randomButton = (ImageButton) findViewById(R.id.randomImageButton);
		praticeBtn.setOnClickListener(listener);
		recordBtn.setOnClickListener(listener);
		randomButton.setOnClickListener(listener);
	}

	private OnClickListener listener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			Intent intent = new Intent(FirstMenu.this, SecondMenu.class);
			Animation scale = AnimationUtils.loadAnimation(FirstMenu.this,
					R.anim.scale_anim);// animation
			switch (v.getId()) {
			case R.id.practiceImageButton:
				pracOrRecoOrRand = 1;// 1: practice
				intent.putExtra("practiceOrCalculate",
						pracOrRecoOrRand);
				praticeBtn.startAnimation(scale);// add animation
				break;
			case R.id.recordImageButton:
				pracOrRecoOrRand = 2;// 2: record
				intent.putExtra("practiceOrCalculate",
						pracOrRecoOrRand);
				recordBtn.startAnimation(scale);// add animation
				break;
			case R.id.randomImageButton:
				pracOrRecoOrRand = 3;// 3: random
				intent.putExtra("practiceOrCalculate",
						pracOrRecoOrRand);
				randomButton.startAnimation(scale);// add animation
				break;
			}
			startActivity(intent);
		};
	};
}
