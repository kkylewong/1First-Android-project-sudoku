package sudoku.controller.activities;

import sudoku.model.gameData.DBAdapter;

import com.example.the_final_project_2.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

/*
 * use the built-in SQLite database with a cursor to populate a ListView.
 * Tutorial reference:
 * https://www.youtube.com/watch?v=gaOsl2TtMHs
 */
public class TheScoreList extends Activity {

	DBAdapter myDb;
	private String theName;
	private String theTime;
	private int theLevel;

	private Button menuBackBtn;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.db_main);

		MediaPlayer theplayer = new MediaPlayer();
		theplayer = MediaPlayer.create(this, R.raw.score);
		theplayer.start();

		openDB();
		populateListViewFromDB();

		// get data from GameActivity through Intent
		Intent intent = getIntent();
		theName = intent.getStringExtra("1name");
		theTime = intent.getStringExtra("2time");
		theLevel = intent.getIntExtra("3level", 0);

		menuBackBtn = (Button) findViewById(R.id.menuBackbutton);
		menuBackBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();// close activity
				System.exit(0);
			}
		});

		if (theName != null) {
			insertDB();
		}
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		closeDB();
	}

	private void openDB() {
		myDb = new DBAdapter(this);
		myDb.open();
	}

	private void closeDB() {
		myDb.close();
	}

	private void insertDB() {
		myDb.insertDB(theTime, theLevel, theName);
		populateListViewFromDB();
	}

	// UI button click function
	public void onClick_ClearAll(View v) {
		myDb.deleteAll();
		populateListViewFromDB();
	}

	private void populateListViewFromDB() {
		Cursor cursor = myDb.getAllRows();

		// Allow activity to manage lifetime of the cursor.
		// DEPRECATED! Runs on the UI thread, OK for small/short queries.
		startManagingCursor(cursor);

		// Setup mapping from cursor to view fields:
		String[] fromFieldNames = new String[] { DBAdapter.KEY_PLAYERNAME,
				DBAdapter.KEY_RECORDLEVEL, DBAdapter.KEY_GAMETIME };
		int[] toViewIDs = new int[] { R.id.item_name, R.id.item_icon,
				R.id.item_favcolour };

		// Create adapter to may columns of the DB onto elemesnt in the UI.
		SimpleCursorAdapter myCursorAdapter = new SimpleCursorAdapter(this, // Context
				R.layout.db_item_layout, // Row layout template
				cursor, // cursor (set of DB records to map)
				fromFieldNames, // DB Column names
				toViewIDs // View IDs to put information in
		);

		// Set the adapter for the list view
		ListView myList = (ListView) findViewById(R.id.listViewFromDB);
		myList.setAdapter(myCursorAdapter);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		switch (keyCode) {
		//when user press return button on phone
		case KeyEvent.KEYCODE_BACK:
			 finish();
			System.exit(0);
			break;
		default:
			break;
		}
		return true;
	}
}