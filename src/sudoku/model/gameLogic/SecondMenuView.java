/*
 * 
 */
package sudoku.model.gameLogic;

import sudoku.controller.interfaces.OnLevelListener;

import com.example.the_final_project_2.R;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.FontMetrics;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class SecondMenuView extends View {

	private float height;
	// when motionEvent up, =1, when motionEvent down, =2. 
	//when =2, color of the tile is changed 
	private int easy_int = 1;
	private int medium_int = 1;
	private int hard_int = 1;

	private OnLevelListener levelListener = null;
	//parameter of overall levels
	public static final int EASY = 1;
	public static final int MEDIUM = 2;
	public static final int HARD = 3;
	
	public int pracOrCalcu=0;//if pracOrCalcu==3, means random, no hard level
	
//	private int pracOrCalcl = 1; // 1 is practice, 2 is calculate;

	public SecondMenuView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	
	public void setPracOrCalcu(int a){
		pracOrCalcu=a;//if pracOrCalcu==3, means random, no hard level
	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		this.height = h / 3f;
		super.onSizeChanged(w, h, oldw, oldh);
	}
	
	//interface
	public void setLevel(int Statelevel) {
		this.levelListener.OnlevelChanged(Statelevel);
	}
	
	//interface
	public void setOnLevelListener(OnLevelListener levelListener) {
		this.levelListener = levelListener;
	}
	
//	public void setPracticeOrCalculate(String pOrC){
//		if(pOrC=="practice"){
//			pracOrCalcl=1;
//		}
//		if(pOrC=="calculate"){
//			pracOrCalcl=2;
//		}
//		
//	}

	@Override
	protected void onDraw(Canvas canvas) {
		Paint level1P = new Paint();
		if (easy_int == 1) {
			//motionEvent down or original
			if(pracOrCalcu==1){level1P.setColor(getResources().getColor(R.color.menu_level1));}
			if(pracOrCalcu==2){level1P.setColor(getResources().getColor(R.color.menu_level1_2));}
			if(pracOrCalcu==3){level1P.setColor(getResources().getColor(R.color.menu_level1_3));}
		} else if (easy_int == 2) {
			// motionEvent up
			if(pracOrCalcu==1){level1P.setColor(getResources().getColor(R.color.menu_level1_light));}
			if(pracOrCalcu==2){level1P.setColor(getResources().getColor(R.color.menu_level1_2_light));}
			if(pracOrCalcu==3){level1P.setColor(getResources().getColor(R.color.menu_level1_3_light));}
		}
		canvas.drawRect(0, 0, getWidth(), height, level1P);

		Paint level2P = new Paint();
		if (medium_int == 1) {
			if(pracOrCalcu==1){level2P.setColor(getResources().getColor(R.color.menu_level2));}
			if(pracOrCalcu==2){level2P.setColor(getResources().getColor(R.color.menu_level2_2));}
			if(pracOrCalcu==3){level2P.setColor(getResources().getColor(R.color.menu_level2_3));}
		}
		if (medium_int == 2) {
			if(pracOrCalcu==1){level2P.setColor(getResources().getColor(R.color.menu_level2_light));}
			if(pracOrCalcu==2){level2P.setColor(getResources().getColor(R.color.menu_level2_2_light));}
			if(pracOrCalcu==3){level2P.setColor(getResources().getColor(R.color.menu_level2_3_light));}
		}
		canvas.drawRect(0, height, getWidth(), height * 2, level2P);

		Paint level3P = new Paint();
		if (hard_int == 1) {
			if(pracOrCalcu==1){level3P.setColor(getResources().getColor(R.color.menu_level3));}
			if(pracOrCalcu==2){level3P.setColor(getResources().getColor(R.color.menu_level1_3));}
		}
		if (hard_int == 2) {
			if(pracOrCalcu==1){level3P.setColor(getResources().getColor(R.color.menu_level3_light));}
			if(pracOrCalcu==2){level3P.setColor(getResources().getColor(R.color.menu_level1_3_light));}
		}
		if(pracOrCalcu==3){
			level3P.setColor(getResources().getColor(R.color.gray));
		}
		canvas.drawRect(0, height * 2, getWidth(), getHeight(), level3P);

		// draw text, easy, medium and hard
		Paint numberPaint = new Paint();
		numberPaint.setColor(Color.WHITE);
		numberPaint.setStyle(Paint.Style.STROKE);
		numberPaint.setTextSize((float) (height * 0.4));
		numberPaint.setTextAlign(Paint.Align.CENTER);

		FontMetrics fm = numberPaint.getFontMetrics();
		float x = getWidth() / 2;
		float y = height / 2 - (fm.ascent + fm.descent) / 2;
		canvas.drawText("EASY", x, y, numberPaint);
		canvas.drawText("MEDIUM", x, height + y, numberPaint);
		canvas.drawText("HARD", x, height * 2 + y, numberPaint);

		super.onDraw(canvas);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		int eventaction = event.getAction();
		
		switch (eventaction) {
			case MotionEvent.ACTION_DOWN: {
				if (event.getY() < height) {
					easy_int = 2;// change color
				}
				if (event.getY() > height && event.getY() < height * 2) {
					medium_int = 2;
				}
				if (event.getY() > height * 2 && pracOrCalcu!=3) {
					hard_int = 2;
				}
				this.invalidate();
				break;
			}
			case MotionEvent.ACTION_UP: {
				if (event.getY() < height) {
					easy_int = 1;// back to original color
					setLevel(EASY);
				}
				if (event.getY() > height && event.getY() < height * 2) {
					medium_int = 1;
					setLevel(MEDIUM);
				}
				if (event.getY() > height * 2 && pracOrCalcu!=3) {
					hard_int = 1;
					setLevel(HARD);
				}
				this.invalidate();
				break;
		  	}
		}
		this.invalidate();
		return true;
	}
}
