package sudoku.controller.dialogs;

import sudoku.model.gameLogic.GameActivityView;

import com.example.the_final_project_2.R;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

/*
 * user-defined dialog
 * this dialog will be showed when empty tile is clicked
 */
public class Dialog_Enter extends Dialog {
	// used to save the button objectives to represent dialog
	private final View keys[] = new View[9];
	private ImageButton delete_btn;
	private ImageButton cancel_btn;
	private int used[];
	private GameActivityView gameView;

	// second parameter: used numbers
	public Dialog_Enter(Context context, int[] used, GameActivityView gameView) {
		super(context);
		this.used = used;
		this.gameView = gameView;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setTitle("Enter a Number:");
		setContentView(R.layout.dialog_enter);
		findViews();
		for (int i = 0; i < used.length; i++) {
			if (used[i] != 0) {
				keys[used[i] - 1].setVisibility(View.INVISIBLE);
			}
		}
		setListeners();// button monitors
		setCancel();
		setDelete();

	}

	private void findViews() {
		// Button images reference
		// http://www.clker.com/clipart-button-for-numbers-3.html
		keys[0] = findViewById(R.id.num1ImageButton);
		keys[1] = findViewById(R.id.num2ImageButton);
		keys[2] = findViewById(R.id.num3ImageButton);
		keys[3] = findViewById(R.id.num4ImageButton);
		keys[4] = findViewById(R.id.num5ImageButton);
		keys[5] = findViewById(R.id.num6ImageButton);
		keys[6] = findViewById(R.id.num7ImageButton);
		keys[7] = findViewById(R.id.num8ImageButton);
		keys[8] = findViewById(R.id.num9ImageButton);
		cancel_btn = (ImageButton) findViewById(R.id.cancelImageButton);
		delete_btn = (ImageButton) findViewById(R.id.deleteImageButton);
	}

	// num is the entered number
	// then, inform gameView to renew whole data and view again
	private void returnResult(int num) {
		gameView.setSelectedTile(num);
		dismiss();
	}

	private void setCancel() {
		cancel_btn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				dismiss();
			}
		});
	}

	// the method of delete is to set the tile to zero
	private void setDelete() {
		delete_btn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				returnResult(0);
			}
		});
	}

	private void setListeners() {
		// scan whole keys
		for (int i = 0; i < keys.length; i++) {
			final int t = i + 1;
			keys[i].setOnClickListener(new View.OnClickListener() {
				public void onClick(View v) {
					returnResult(t);
				}
			});
		}
	}
}
