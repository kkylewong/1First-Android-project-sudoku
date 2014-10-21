package sudoku.model.gameData;

import com.example.the_final_project_2.R;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/*
 * Sqlite database for storing game records
 * Tutorial reference:
 * https://www.youtube.com/watch?v=gaOsl2TtMHs
 */
public class DBAdapter {

	private static final String TAG = "DBAdapter";
	public static final String KEY_ROWID = "_id";
	public static final int COL_ROWID = 0;
	// three rows
	public static final String KEY_PLAYERNAME = "playerName";
	public static final String KEY_RECORDLEVEL = "recordLeve";
	public static final String KEY_GAMETIME = "gameTime";

	public static final int COL_NAME = 1;
	public static final int COL_STUDENTNUM = 2;
	public static final int COL_FAVCOLOUR = 3;

	public static final String[] ALL_KEYS = new String[] { KEY_ROWID,
			KEY_PLAYERNAME, KEY_RECORDLEVEL, KEY_GAMETIME };

	// name and the table
	public static final String DATABASE_NAME = "MyDb";
	public static final String DATABASE_TABLE = "amainTable";
	// DB version
	public static final int DATABASE_VERSION = 2;

	private static final String DATABASE_CREATE_SQL = "create table "
			+ DATABASE_TABLE + " (" + KEY_ROWID
			+ " integer primary key autoincrement, " + KEY_PLAYERNAME
			+ " text not null, " + KEY_RECORDLEVEL + " integer not null, "
			+ KEY_GAMETIME + " string not null" + ");";

	// Context of application who uses.
	private final Context context;

	private DatabaseHelper myDBHelper;
	private SQLiteDatabase db;

	int[] imageIDs = { R.drawable.easy_good, R.drawable.easy_ok,
			R.drawable.easy_bad, R.drawable.medium_good, R.drawable.medium_ok,
			R.drawable.medium_bad, R.drawable.hard_good, R.drawable.hard_ok,
			R.drawable.hard_bad };

	public DBAdapter(Context ctx) {
		this.context = ctx;
		myDBHelper = new DatabaseHelper(context);
	}

	// Open the database connection.
	public DBAdapter open() {
		db = myDBHelper.getWritableDatabase();
		return this;
	}

	// Close the database connection.
	public void close() {
		myDBHelper.close();
	}

	public void insertDB(String theTime, int theLevel, String theName) {
		int imageId = imageIDs[0];
		char x = theTime.charAt(1);
		char y = theTime.charAt(0);
		// the string of theTime is like: "00:00"
		// when y = theTime.charAt(0), it means y is the first char of "00:00"
		// 1st
		if (y == '0') {
			// game time is lower than 5 minutes
			// 2nd
			if (x == '0' || x == '1' || x == '2' || x == '3' || x == '4') {
				// 3rd
				if (theLevel < 20) {
					// the integer of easy theLeve is lower than 20
					imageId = imageIDs[0];
				}
				// 3rd
				if (theLevel > 20 && theLevel < 30) {
					// the integer of medium theLeve is between 20 and 30
					imageId = imageIDs[3];
				}
				// 3rd
				if (theLevel > 30 && theLevel < 40) {
					// the integer of hard theLeve is between 30 and 40
					imageId = imageIDs[6];
				}
				// 2nd
			} else if (x == '5' || x == '6' || x == '7' || x == '8' || x == '9') {
				// 3rd
				if (theLevel < 20) {
					imageId = imageIDs[1];
				}
				// 3rd
				if (theLevel > 20 && theLevel < 30) {
					imageId = imageIDs[4];
				}
				// 3rd
				if (theLevel > 30 && theLevel < 40) {
					imageId = imageIDs[7];
				}
			}
			// 1st
		} else if (y == '1' || y == '2' || y == '3' || y == '4' || y == '5'
				|| y == '6' || y == '7' || y == '8' || y == '9') {
			// 2nd
			if (theLevel < 20) {
				imageId = imageIDs[2];
			}
			// 2nd
			if (theLevel > 20 && theLevel < 30) {
				imageId = imageIDs[5];
			}
			// 2nd
			if (theLevel > 30 && theLevel < 40) {
				imageId = imageIDs[8];
			}
		}
		insertRow(theName, imageId, theTime);
	}

	// Add a new set of values to the database.
	public long insertRow(String playerName, int recordLevel, String gameTime) {
		// Create row's data:
		ContentValues initialValues = new ContentValues();
		initialValues.put(KEY_PLAYERNAME, playerName);
		initialValues.put(KEY_RECORDLEVEL, recordLevel);
		initialValues.put(KEY_GAMETIME, gameTime);
		// Insert it into the database.
		return db.insert(DATABASE_TABLE, null, initialValues);
	}

	// Delete a row from the database, by rowId (primary key)
	public boolean deleteRow(long rowId) {
		String where = KEY_ROWID + "=" + rowId;
		return db.delete(DATABASE_TABLE, where, null) != 0;
	}

	// Delete all
	public void deleteAll() {
		Cursor c = getAllRows();
		long rowId = c.getColumnIndexOrThrow(KEY_ROWID);
		if (c.moveToFirst()) {
			do {
				deleteRow(c.getLong((int) rowId));
			} while (c.moveToNext());
		}
		c.close();
	}

	// Return all data in the database.
	public Cursor getAllRows() {
		String where = null;
		Cursor c = db.query(true, DATABASE_TABLE, ALL_KEYS, where, null, null,
				null, null, null);
		if (c != null) {
			c.moveToFirst();
		}
		return c;
	}

	// Get a specific row (by rowId)
	public Cursor getRow(long rowId) {
		String where = KEY_ROWID + "=" + rowId;
		Cursor c = db.query(true, DATABASE_TABLE, ALL_KEYS, where, null, null,
				null, null, null);
		if (c != null) {
			c.moveToFirst();
		}
		return c;
	}

	// Change an existing row to be equal to new data.
	public boolean updateRow(long rowId, String playerName, int recordLevel,
			String gameTime) {
		String where = KEY_ROWID + "=" + rowId;

		// Create row's data:
		ContentValues newValues = new ContentValues();
		newValues.put(KEY_PLAYERNAME, playerName);
		newValues.put(KEY_RECORDLEVEL, recordLevel);
		newValues.put(KEY_GAMETIME, gameTime);

		// Insert it into the database.
		return db.update(DATABASE_TABLE, newValues, where, null) != 0;
	}

	/**
	 * Private class which handles database creation and upgrading. Used to
	 * handle low-level database access.
	 */
	private static class DatabaseHelper extends SQLiteOpenHelper {
		DatabaseHelper(Context context) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
		}

		@Override
		public void onCreate(SQLiteDatabase _db) {
			_db.execSQL(DATABASE_CREATE_SQL);
		}

		@Override
		public void onUpgrade(SQLiteDatabase _db, int oldVersion, int newVersion) {
			Log.w(TAG, "Upgrading application's database from version "
					+ oldVersion + " to " + newVersion
					+ ", which will destroy all old data!");

			// Destroy old database:
			_db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE);

			// Recreate new database:
			onCreate(_db);
		}
	}
}
