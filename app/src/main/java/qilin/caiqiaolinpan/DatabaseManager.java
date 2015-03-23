package qilin.caiqiaolinpan;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by qilwang on 3/23/15.
 */
public class DatabaseManager extends SQLiteOpenHelper {
    private static final String TAG = DatabaseManager.class.getName();

    private static final String DB_NAME = "contact_db";
    private static final String TABLE_NAME = "contact_table";
    private static final int DB_VERSION = 1;
    private static final String COLUMN_UID = "user_id";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_DOB = "date_of_birth";
    private static final String COLUMN_PHONE = "phone_number";
    private static final String COLUMN_DATE_ADDED = "date_added";

    // SQLite statement to create a table
    private static final String DB_CREATE = "create table " + TABLE_NAME + "("
            + COLUMN_UID + " integer primary key autoincrement, "
            + COLUMN_NAME + " text not null, "
            + COLUMN_DOB + " text, "
            + COLUMN_PHONE + " text, "
            + COLUMN_DATE_ADDED + "text);";

    public DatabaseManager(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DB_CREATE);
        Log.i(TAG, "Table created!");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(TAG, "Upgrading database from version "
                + oldVersion + " to " + newVersion
                + ", which will destroy all old data");

        // drop the old table
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);

        // create a new one
        onCreate(db);
    }
}
