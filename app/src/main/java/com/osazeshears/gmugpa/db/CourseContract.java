package com.osazeshears.gmugpa.db;
import android.provider.BaseColumns;

/**
 * Created by Osaze on 3/5/15.
 */
public class CourseContract {
    //Class of variables used to access data in the database

    public static final String DB_NAME = "com.osazeshears.gmugpa.db.course";
    public static final int DB_VERSION = 1;
    public static final String TABLE = "tasks";

    public class Columns{
        public static final String TASK = "course";
        public static final String _ID = BaseColumns._ID;

        }
}
