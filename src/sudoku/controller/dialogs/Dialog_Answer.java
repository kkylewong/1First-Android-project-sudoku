package sudoku.controller.dialogs;

import sudoku.model.gameLogic.GameActivityView;

import com.example.the_final_project_2.R;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

/*
 * user-defined dialog
 * this dialog will be showed when Answer button is clicked
 */
public class Dialog_Answer extends Dialog {

	private int gameLevel;
	private GameActivityView myView;
	private ImageButton backImageButton;

	public Dialog_Answer(Context context, int level) {
		super(context);
		// game Level in GameBase class is over 100
		gameLevel = level + 100;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setTitle("The Answer");
		setContentView(R.layout.dialog_answer);
		myView = (GameActivityView) findViewById(R.id.myView);
		myView.setGameinView(gameLevel);
		backImageButton = (ImageButton) findViewById(R.id.backImageButton);
		backImageButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				dismiss();
			}
		});
	}

}
