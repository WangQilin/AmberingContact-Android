package qilin.caiqiaolinpan;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by qilwang on 3/23/15.
 */
public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String TAG = DatabaseHelper.class.getName();

    private static final String DB_NAME = "contact db";
    private static final int DB_VERSION = 1;

    public static final String TABLE_NAME = "contact table";
    public static final String COLUMN_UID = "user id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_DOB = "date of birth";
    public static final String COLUMN_PHONE = "phone number";

    // SQLite statement to create a table
    private static final String DB_CREATE = "create table " + TABLE_NAME + "("
            + COLUMN_UID + " integer primary key autoincrement, "
            + COLUMN_NAME + " text not null, "
            + COLUMN_DOB + " text, "
            + COLUMN_PHONE + " text);";

    public DatabaseHelper(Context context) {
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
