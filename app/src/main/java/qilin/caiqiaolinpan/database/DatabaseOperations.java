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

    private static final String TAG = "DatabaseOperations";

    // index number used as primary key
    public static int currentIndexNumber = 0;

    public static final int database_version = 1;

    // create table not exists, and pay attention to SQLite data types
    private String CREATE_QUERY = "CREATE TABLE IF NOT EXISTS " +
            TableInfo.TABLE_NAME + " (" +
            TableInfo.INDEX + " INTEGER NOT NULL, " +
            TableInfo.NAME + " TEXT NOT NULL, " +
            TableInfo.PHONE + " TEXT NOT NULL, " +
            TableInfo.DATEOFBIRTH + " TEXT, " +
            TableInfo.PROFILEPICTUREID + " INTEGER, " +
            TableInfo.PROFILEPICTUREURI + " TEXT);";

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

    public void putInformation(DatabaseOperations dop, int index, String name, String phone, String dateOfBirth, int profilePictureId, String profilePictureUri) {
        SQLiteDatabase sq = dop.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(TableInfo.INDEX, index);
        cv.put(TableInfo.NAME, name);
        cv.put(TableInfo.PHONE, phone);
        cv.put(TableInfo.DATEOFBIRTH, dateOfBirth);
        cv.put(TableInfo.PROFILEPICTUREID, profilePictureId);
        cv.put(TableInfo.PROFILEPICTUREURI, profilePictureUri);

        long result = sq.insert(TableInfo.TABLE_NAME, null, cv);

        Log.d(TAG, "One row inserted into database, result value:" + result);
        Log.i(TAG, "##### information: " + "\n" +
                "##### index_number: " + index + "\n" +
                "##### name: " + name + "\n" +
                "##### phone: " + phone + "\n" +
                "##### date of birth: " + dateOfBirth + "\n" +
                "##### profilePictureId: " + profilePictureId + "\n" +
                "##### profilePictureUri: " + profilePictureUri + "\n" +
                "##### current index number: " + currentIndexNumber);
    }

    public Cursor getInformation(DatabaseOperations dop) {
        SQLiteDatabase sq = dop.getReadableDatabase();
        String[] columns = {TableInfo.INDEX, TableInfo.NAME, TableInfo.PHONE, TableInfo.DATEOFBIRTH, TableInfo.PROFILEPICTUREID, TableInfo.PROFILEPICTUREURI};
        Cursor cursor = sq.query(TableInfo.TABLE_NAME, columns, null, null, null, null, null);
        return cursor;
    }
}


