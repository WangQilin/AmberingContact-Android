package qilin.caiqiaolinpan;

import android.content.Context;
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
}
