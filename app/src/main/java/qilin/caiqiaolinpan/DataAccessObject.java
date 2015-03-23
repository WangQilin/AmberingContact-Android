package qilin.caiqiaolinpan;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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
        DatabaseModel dbModel = cursorToDatabaseModel(cursor);

        return dbModel;
    }

    public void deleteEntry(DatabaseModel dbModel) {
        long id = dbModel.getUserId();
        db.delete(DatabaseHelper.TABLE_NAME, DatabaseHelper.COLUMN_UID + " = " + id, null);
    }

    public List<DatabaseModel> getAllDatabaseModel() {
        List<DatabaseModel> dbModels = new ArrayList<DatabaseModel>();

        Cursor cursor = db.query(DatabaseHelper.TABLE_NAME, allColumns, null, null, null, null, null);
        cursor.moveToFirst();

        while(!cursor.isAfterLast()) {
            DatabaseModel dbModel = cursorToDatabaseModel(cursor);
            dbModels.add(dbModel);
            cursor.moveToNext();
        }
        cursor.close();
        return dbModels;
    }

    private DatabaseModel cursorToDatabaseModel(Cursor cursor) {
        DatabaseModel dbModel = new DatabaseModel();
        dbModel.setUserId(cursor.getLong(0));
        dbModel.setName(cursor.getString(1));
        dbModel.setPhone(cursor.getString(2));
        dbModel.setDateOfBirth(cursor.getString(3));
        dbModel.setDateAdded(cursor.getString(4));
        return dbModel;
    }
}
