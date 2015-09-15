package com.dong.tourshanghai.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.dong.tourshanghai.entity.SpotsListEntity;

import java.util.ArrayList;

/**
 * Intro:
 * <p>
 * Programmer: dong
 * Date: 15/9/14.
 */
public class SpotsCollectionDao {

    private SpotsSQLiteOpenHelper mOpenHelper;

    public SpotsCollectionDao(Context context) {
        mOpenHelper = new SpotsSQLiteOpenHelper(context, "tourSH.db", null, 1);
    }

    /**
     * 收藏一个景点
     *
     * @param spotModel
     */
    public void insert(SpotsListEntity.SpotModel spotModel) {
        SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        if (db.isOpen()) {
            db.execSQL("INSERT INTO localspots(spot_id, spot_name, spot_lat, spot_lon, spot_addr, " +
                    "spot_trans, spot_imgurl, spot_intro, spot_fee, spot_time, spot_type) " +
                    "VALUES(?,?,?)", new Object[]{
                    spotModel.spot_id, spotModel.spot_name, spotModel.spot_lat, spotModel.spot_lon,
                    spotModel.spot_addr, spotModel.spot_trans, spotModel.spot_imgurl, spotModel.spot_intro,
                    spotModel.spot_fee, spotModel.spot_time, spotModel.spot_type
            });
            db.close();
        }
    }

    /**
     * 删除一个景点
     *
     * @param spot_id
     */
    public void delete(int spot_id) {
        SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        if (db.isOpen()) {
            db.execSQL("DELETE FROM localspots WHERE spot_id = ?", new Integer[]{spot_id});
            db.close();
        }
    }

    /**
     * 删除所有收藏的景点
     */
    public void deleteAll() {
        SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        if (db.isOpen()) {
            db.execSQL("DELETE * FROM localspots");
            db.close();
        }
    }

    /**
     * 查询所有收藏的景点
     *
     * @return
     */
    public ArrayList<SpotsListEntity.SpotModel> queryAll() {
        SQLiteDatabase db = mOpenHelper.getReadableDatabase();
        if (db.isOpen()) {
            Cursor cursor = db.rawQuery("SELECT * FROM localspots", null);
            if (cursor != null && cursor.getCount() > 0) {
                ArrayList<SpotsListEntity.SpotModel> spotsList = new ArrayList<SpotsListEntity.SpotModel>();
                while (cursor.moveToNext()) {
                    SpotsListEntity.SpotModel spot = new SpotsListEntity.SpotModel();
                    spot.spot_id = cursor.getInt(cursor.getColumnIndex("spot_id"));
                    spot.spot_name = cursor.getString(cursor.getColumnIndex("spot_name"));
                    spot.spot_lat = cursor.getDouble(cursor.getColumnIndex("spot_lat"));
                    spot.spot_lon = cursor.getDouble(cursor.getColumnIndex("spot_lon"));
                    spot.spot_addr = cursor.getString(cursor.getColumnIndex("spot_addr"));
                    spot.spot_trans = cursor.getString(cursor.getColumnIndex("spot_trans"));
                    spot.spot_imgurl = cursor.getString(cursor.getColumnIndex("spot_imgurl"));
                    spot.spot_intro = cursor.getString(cursor.getColumnIndex("spot_intro"));
                    spot.spot_fee = cursor.getString(cursor.getColumnIndex("spot_fee"));
                    spot.spot_time = cursor.getString(cursor.getColumnIndex("spot_time"));
                    spot.spot_type = cursor.getInt(cursor.getColumnIndex("spot_type"));
                    spotsList.add(spot);
                }
                cursor.close();
                db.close();
                return spotsList;
            }
            db.close();
        }
        return null;
    }

}
