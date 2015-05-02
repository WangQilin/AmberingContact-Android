package qilin.caiqiaolinpan.database;

import android.provider.BaseColumns;

/**
 * Created by qilinwang on 1/5/15.
 */
public class TableData {

    public TableData() {

    }

    public static abstract class TableInfo implements BaseColumns {

        public static final String NAME = "name";
        public static final String PHONE = "phone";
        public static final String DATEOFBIRTH = "data_of_birth";
        public static final String PROFILEPICTURE = "profile_picture";
        public static final String DATABASE_NAME = "contact_info_database";
        public static final String TABLE_NAME = "contact_info_table";


    }

}