package com.dong.tourshanghai.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Intro:
 * <p>
 * Programmer: dong
 * Date: 15/9/14.
 */
public class SpotsSQLiteOpenHelper extends SQLiteOpenHelper {
    public SpotsSQLiteOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE localspots(spot_id integer PRIMARY KEY, spot_name varchar(100)," +
                "spot_lat double, spot_lon double, spot_addr varchar(200), spot_trans varchar(200), " +
                "spot_imgurl varchar(200), spot_intro varchar(2000), spot_fee varchar(50), " +
                "spot_time varchar(50)), spot_type varchar(10)";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // 待编写
        if (oldVersion == 1) {

        }
    }
}
