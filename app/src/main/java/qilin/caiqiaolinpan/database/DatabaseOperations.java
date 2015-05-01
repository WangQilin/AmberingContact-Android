package qilin.caiqiaolinpan.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import qilin.caiqiaolinpan.database.TableData.TableInfo;

/**
 * Created by qilinwang on 1/5/15.
 */
public class DatabaseOperations extends SQLiteOpenHelper {

    private final String TAG = this.getClass().getName();
    public static final int database_version = 1;
    public String CREATE_QUERY = "CREATE TABLE " + TableInfo.TABLE_NAME + " (" + TableInfo.USER_NAME + " TEXT, " + TableInfo.PASSWORD + " Text);";

    public DatabaseOperations(Context context) {
        super(context, TableInfo.DATABASE_NAME, null, database_version);
        Log.d(TAG, "DatabaseOperations created");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_QUERY);
        Log.d(TAG, "table created");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void putInformation(DatabaseOperations dop, String username, String password) {
        // todo: investigate if there's a need to pass in dop, looks weird!
        SQLiteDatabase sq = dop.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(TableInfo.USER_NAME, username);
        cv.put(TableInfo.PASSWORD, password);
        long result = sq.insert(TableInfo.TABLE_NAME, null, cv);
        Log.d(TAG, "One row inserted into database, result value:" + result);
    }

    public Cursor getInformation(DatabaseOperations dop) {
        SQLiteDatabase sq = dop.getReadableDatabase();
        String[] columns = {TableInfo.USER_NAME, TableInfo.PASSWORD};
        Cursor cursor = sq.query(TableInfo.TABLE_NAME, columns, null, null, null, null, null);
        return cursor;
    }
}


