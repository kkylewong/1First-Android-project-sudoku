package sudoku.controller.dialogs;

import sudoku.controller.activities.GameActivity;
import sudoku.controller.interfaces.OnReturnNameListener;

import com.example.the_final_project_2.R;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/*
 * user-defined dialog
 * dialog will be showed when win
 */
public class Dialog_Win extends Dialog {

	private TextView timeInfo;
	private Button name_confirm_btn;
	private String finishedTime;
	private EditText nameEditText;
	private OnReturnNameListener onNameL = null;

	public Dialog_Win(Context context, String time) {
		super(context);
		finishedTime = time;
	}

	public void setOnReturnNameListener(OnReturnNameListener onNameL) {
		this.onNameL = onNameL;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setTitle("YOU WIN!");
		setContentView(R.layout.dialog_win);
		setCancelable(false);
		timeInfo = (TextView) findViewById(R.id.timeInfo);

		timeInfo.setText("You used:" + finishedTime + "minutes!");

		nameEditText = (EditText) findViewById(R.id.nameEnter_editText);

		name_confirm_btn = (Button) findViewById(R.id.name_confirm_btn);
		name_confirm_btn.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				String name = nameEditText.getText().toString();
				//set name is not null;
				if(name.equals(""))
				   {
				      Toast t =Toast.makeText(getContext(), "Please enter name", 3000);
				      t.show();   
				   }else{
						onNameL.returnName(name);
						GameActivity.instance.finish();
						dismiss();
				   }
				
			}
		});
	}

	public void setFinishedTime(String a) {
		finishedTime = a;
	}
}
