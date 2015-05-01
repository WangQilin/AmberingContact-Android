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

    public String CREATE_QUERY = "CREATE TABLE " + TableInfo.TABLE_NAME + " (" + TableInfo.NAME + " TEXT NOT NULL, " + TableInfo.PHONE + " TEXT NOT NULL, " + TableInfo.DATEOFBIRTH + " TEXT, " + TableInfo.PROFILEPICTURE + " INT);";

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

    public void putInformation(DatabaseOperations dop, String name, String phone, String dateOfBirth, int profilePicture) {
        SQLiteDatabase sq = dop.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(TableInfo.NAME, name);
        cv.put(TableInfo.PHONE, phone);
        cv.put(TableInfo.DATEOFBIRTH, dateOfBirth);
        cv.put(TableInfo.PROFILEPICTURE, profilePicture);
        long result = sq.insert(TableInfo.TABLE_NAME, null, cv);
        Log.d(TAG, "One row inserted into database, result value:" + result);
    }

    public Cursor getInformation(DatabaseOperations dop) {
        SQLiteDatabase sq = dop.getReadableDatabase();
        String[] columns = {TableInfo.NAME, TableInfo.PHONE, TableInfo.DATEOFBIRTH, TableInfo.PROFILEPICTURE};
        Cursor cursor = sq.query(TableInfo.TABLE_NAME, columns, null, null, null, null, null);
        return cursor;
    }
}


