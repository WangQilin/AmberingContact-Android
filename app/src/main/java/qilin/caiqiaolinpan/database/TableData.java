package qilin.caiqiaolinpan.database;

import android.provider.BaseColumns;

/**
 * Created by qilinwang on 1/5/15.
 */
public class TableData {

    public TableData() {

    }

    public static abstract class TableInfo implements BaseColumns {

        public static final String USER_NAME = "user_name";
        public static final String PASSWORD = "password";
        public static final String DATABASE_NAME = "user_info";
        public static final String TABLE_NAME = "reg_info";


    }

}
