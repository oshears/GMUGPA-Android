package com.osazeshears.gmugpa.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Osaze on 3/5/15.
 */
public class CourseDBHelper extends SQLiteOpenHelper {

    public CourseDBHelper(Context context) {
        super(context, CourseContract.DB_NAME, null, CourseContract.DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqlDB) {
        String sqlQuery =
                String.format("CREATE TABLE %s (" +
                                "_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                                "%s TEXT)", CourseContract.TABLE,
                        CourseContract.Columns.TASK);

        Log.d("CourseDBHelper","Query to form table: "+sqlQuery);
        sqlDB.execSQL(sqlQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqlDB, int i, int i2) {
        sqlDB.execSQL("DROP TABLE IF EXISTS "+CourseContract.TABLE);
        onCreate(sqlDB);
    }
}

