/*
 * defined view for GameActivity
 */
package sudoku.model.gameLogic;

import sudoku.controller.dialogs.Dialog_Enter;
import sudoku.controller.interfaces.OnStateListener;
import com.example.the_final_project_2.R;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Paint.FontMetrics;
import android.graphics.Rect;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class GameActivityView extends View {

	private float width;
	private float height;
	//selectedX = event.getX() / width     clicked location
	private int selectedX;
	private int selectedY;
	private Bitmap back_btn;
	private Bitmap answer_btn;

	//state parameter
	public static final int WIN = 1;
	public static final int ANSWER = 6;
	public static final int QUIT =7;
	//state listener interface
	private OnStateListener stateListener = null;
	
	//parameter for refresh the view 
	private static final int REFRESH_VIEW = 1;
	private RefreshHandler refreshHandler = new RefreshHandler();

	private int theLevel;
	private GameLogic gameLogic;
	//parameter for checking if the tile is clicked by user,1 is clicked, 0 is not clicked
	private int checkClickedDrawRec = 0;
	
	private int thePracOrCalcu;// 1 is practice, 2 is calculate, 3 is random
	
	private int calcNumEnter=0;//num of textView for calculate level
	
	public GameActivityView(Context context, AttributeSet attrs) {
		super(context, attrs);
		back_btn = BitmapFactory.decodeResource(getResources(),
				R.drawable.green_button);
		answer_btn = BitmapFactory.decodeResource(getResources(),
				R.drawable.blue_button);
	}

	public void setLevel(int a) {
		theLevel = a;
	}
	
	public void setCalcNumEnter(int a){
		calcNumEnter=a;
		System.out.println("GameView - calcNumEnter--> "+ calcNumEnter);
	}

	//define OnStateListener is the same one, interface
	public void setOnStateListener(OnStateListener stateListener) {
		this.stateListener = stateListener;
	}
	
	//interface
	public void setMode(int stateMode) {
		this.stateListener.OnStateChanged(stateMode);
	}

	//define Game, set Game class is connected with this view
	public void setGameinView(int level) {
		theLevel = level;
		gameLogic = new GameLogic(theLevel);
	}
	
	public void setPracticeOrCalculate(int pOC){
			thePracOrCalcu = pOC;
		System.out.println("GameView -thePracOrCalcu-> "+thePracOrCalcu);
	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		// calculate current width and height
		this.width = w / 9f;
		this.height = (float) (h / 9f * 0.9);
		if(thePracOrCalcu==1||thePracOrCalcu==3){
			this.height = (float) (h / 9f * 0.9);
		}
		if(thePracOrCalcu==2){
			this.height = (float) (h / 9f * 0.7);
		}
		super.onSizeChanged(w, h, oldw, oldh);
	}

	// when android system needs to draw a view object, it will be called
	@Override
	protected void onDraw(Canvas canvas) {
		int ninthHeight =  (int) (getHeight() * 0.9);
		if(thePracOrCalcu==1||thePracOrCalcu==3){
			ninthHeight = (int) (getHeight() * 0.9);
		}
		if(thePracOrCalcu==2){
			ninthHeight= (int) (getHeight() * 0.7);
		}
		//  background paint
		Paint backgroundPaint = new Paint();
		if(theLevel < 100){
			backgroundPaint.setColor(getResources().getColor(R.color.sudo_background));
		}
		if(theLevel>100){
			backgroundPaint.setColor(getResources().getColor(R.color.sudo_answer_background));
		}
		
		// draw background
		canvas.drawRect(0, 0, getWidth(), ninthHeight, backgroundPaint);
		//line paint1
		Paint darkPaint = new Paint();
		darkPaint.setStrokeWidth(8);
		darkPaint.setColor(getResources().getColor(R.color.sudo_dark));
		//line paint2
		Paint highlightPaint = new Paint();
		highlightPaint.setColor(getResources().getColor(R.color.sudo_highlight));
		//line paint3
		Paint lightPaint = new Paint();
		lightPaint.setColor(getResources().getColor(R.color.sudo_light));

		for (int i = 0; i < 9; i++) {
			// two horizontal lines * 9
			canvas.drawLine(0, i * height, getWidth(), i * height, lightPaint);
			canvas.drawLine(0, i * height + 1, getWidth(), i * height + 1,
					highlightPaint);
			// two vertical lines * 9
			canvas.drawLine(i * width, 0, i * width, ninthHeight, lightPaint);
			canvas.drawLine(i * width + 1, 0, i * width + 1, ninthHeight,
					highlightPaint);
		}

		for (int i = 0; i < 9; i++) {
			if (i % 3 != 0) {
				continue;
			}
			// dark horizontal lines * 3
			canvas.drawLine(0, i * height, getWidth(), i * height, darkPaint);
			canvas.drawLine(0, i * height + 2, getWidth(), i * height + 2,
					highlightPaint);
			// dark vertical lines * 3
			canvas.drawLine(i * width, 0, i * width, ninthHeight, darkPaint);
			canvas.drawLine(i * width + 2, 0, i * width + 2, ninthHeight,
					highlightPaint);
		}

		// draw the game dynamic text
		Paint numberUpdatedPaint = new Paint();
		numberUpdatedPaint.setColor(Color.BLUE);
		numberUpdatedPaint.setStyle(Paint.Style.STROKE);
		numberUpdatedPaint.setTextSize((float) (height * 0.75));
		numberUpdatedPaint.setTextAlign(Paint.Align.CENTER);

		FontMetrics fm = numberUpdatedPaint.getFontMetrics();
		//set number in the middle of the tile
		float x = width / 2;
		float y = height / 2 - (fm.ascent + fm.descent) / 2;

		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				canvas.drawText(gameLogic.getTileString(i, j, "updated"), i * width
						+ x, j * height + y, numberUpdatedPaint);
			}
		}
		
		// draw the generated text in the beginning
		Paint numberOriginalPaint = new Paint();
		if(theLevel < 100){
			numberOriginalPaint.setColor(Color.BLACK);
		}
		if(theLevel>100){
			numberOriginalPaint.setColor(Color.WHITE);
		}
		numberOriginalPaint.setStyle(Paint.Style.STROKE);
		numberOriginalPaint.setTextSize((float) (height * 0.75));
		numberOriginalPaint.setTextAlign(Paint.Align.CENTER);

		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				canvas.drawText(gameLogic.getTileString(i, j, "original"), i * width
						+ x, j * height + y, numberOriginalPaint);
			}
		}

		Matrix matrix = new Matrix();
		matrix.setScale(0.5f, 0.5f);

		int initialWidth = (int) width;
		int initialHeight = (int) height;

		if (theLevel < 100 && thePracOrCalcu==1||thePracOrCalcu==3) {
			drawImage(canvas, back_btn, initialWidth, initialHeight * 9,
					initialWidth * 2, initialHeight, 0, 0);
			if(thePracOrCalcu!=3){
				drawImage(canvas, answer_btn, initialWidth * 6, initialHeight * 9,
						initialWidth * 2, initialHeight, 0, 0);
			}
		}

		//draw relevant red area when use click a tile
		if (checkClickedDrawRec == 1) {
			Paint recPaint = new Paint();
			recPaint.setColor(getResources().getColor(R.color.red));
			recPaint.setAlpha(0x80);
			canvas.drawRect(0, selectedY * height,
					9* width, (selectedY + 1) * height, recPaint);
			canvas.drawRect(selectedX * width, 0,
					(selectedX + 1) * width, 9 * height, recPaint);
			checkClickedDrawRec = 0;
		}
		super.onDraw(canvas);
	}
	
	/*
	 * reference from:
	 *  http://www.cnblogs.com/error404/archive/2012/02/05/2339255.html
	 *  draw bitmap whose size will be changed by requirement
	 */
	public static void drawImage(Canvas canvas, Bitmap blt, int x, int y,
			int w, int h, int bx, int by) {
		
		Rect src = new Rect();// Bitmap >>	original rect
		Rect dst = new Rect();// screen >> target rect

		src.left = bx;
		src.top = by;
		src.right = bx + w;
		src.bottom = by + h;

		dst.left = x;
		dst.top = y;
		dst.right = x + w;
		dst.bottom = y + h;
		
		canvas.drawBitmap(blt, null, dst, null);
		src = null;
		dst = null;
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if (event.getAction() != MotionEvent.ACTION_DOWN) {
			return super.onTouchEvent(event);
		}
		if (event.getY() <= height * 9) {
			//the number of clicked location/unit of tile
			selectedX = (int) (event.getX() / width);
			selectedY = (int) (event.getY() / height);

			int used[] = gameLogic.getUsedTilesByCoor(selectedX, selectedY);
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < used.length; i++) {
				sb.append(used[i]);

				checkClickedDrawRec = 1;//draw red rect to highlight the clicked tile
				invalidate();
			}
			Dialog_Enter keyDialog = new Dialog_Enter(getContext(), used, this);

			// prevent clicking non-zero tile from popping up dialog
			if (gameLogic.checkTile0(selectedX, selectedY)) {
				if(thePracOrCalcu==1||thePracOrCalcu==3){
					keyDialog.show();
				}
				if(thePracOrCalcu==2){
					setSelectedTile(calcNumEnter);
				}
				invalidate();
			}
		}
		//because theLevel for answers is over 100, this view is called in Answer activity
		if (theLevel < 100  && (thePracOrCalcu==1||thePracOrCalcu==3)) {
			if (event.getY() > height * 9 && event.getX() > width
					&& event.getX() < width * 3) {
//				Dialog_Quit quitDialog = new Dialog_Quit(getContext());
//				quitDialog.show();
				setMode(QUIT);
			}
			if (event.getY() > height * 9 && event.getX() > width * 6
					&& event.getX() < width * 8 && thePracOrCalcu!=3) {
				setMode(ANSWER);
			}
			if (event.getY() > height * 9 && event.getX() > width * 8) {
				invalidate();
			}
		}
		return true;
	}


	// fill in the number in empty tile
	public void setSelectedTile(int tile) {
		if (gameLogic.setTileIfValid(selectedX, selectedY, tile)) {
			refreshHandler.sleep(500);
		}
	}

	//condition to win, when there is no empty tile
	public boolean win() {
		boolean a = false;
		if (theLevel < 100) {
			if (gameLogic.checkAnyEmptyTile() == false) {
				a = true;
			}
		}
		return a;
	}

	class RefreshHandler extends Handler {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			if (msg.what == REFRESH_VIEW) {
				GameActivityView.this.invalidate();
				if (win()) {
					setMode(WIN);//send parameter to GameActivity
				}
			}
		}

		public void sleep(int delayTime) {
			this.removeMessages(0);
			Message message = new Message();
			message.what = REFRESH_VIEW;
			sendMessageDelayed(message, delayTime);
		}
	}
}