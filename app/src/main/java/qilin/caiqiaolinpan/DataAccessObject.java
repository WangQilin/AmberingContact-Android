package qilin.caiqiaolinpan;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.sql.SQLException;

/**
 * Created by qilwang on 3/23/15.
 */
public class DataAccessObject {
    private SQLiteDatabase db;
    private DatabaseHelper dbHelper;
    private String[] allColumns = {
            dbHelper.COLUMN_UID,
            dbHelper.COLUMN_NAME,
            dbHelper.COLUMN_PHONE,
            dbHelper.COLUMN_DOB,
            dbHelper.COLUMN_DATE_ADDED
    };

    public DataAccessObject(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    public void open() throws SQLException {
        db = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public DatabaseModel createDatabaseModel(String name, String phone, String dob, String dateAdded) {
        ContentValues values = new ContentValues();

        values.put(dbHelper.COLUMN_NAME, name);
        values.put(dbHelper.COLUMN_PHONE, phone);
        values.put(dbHelper.COLUMN_DOB, dob);
        values.put(dbHelper.COLUMN_DATE_ADDED, dateAdded);

        long insertId = db.insert(dbHelper.TABLE_NAME, null, values);

        Cursor cursor = db.query(dbHelper.TABLE_NAME, allColumns, dbHelper.COLUMN_UID + " = " + insertId, null, null, null, null);
        cursor.moveToFirst();
        //todo
        DatabaseModel dbModel = null;

        return dbModel;
    }
}
