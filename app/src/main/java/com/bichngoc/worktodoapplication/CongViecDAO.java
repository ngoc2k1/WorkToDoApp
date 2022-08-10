package com.bichngoc.worktodoapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

public class CongViecDAO {
    private Database database;
    private SQLiteDatabase sqLiteDatabase;

    public CongViecDAO(Context context) {
        database = new Database(context);
        sqLiteDatabase = database.getWritableDatabase();
    }

    public void insert(CongViec congViec) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("TenCV", congViec.getTenCV());
        sqLiteDatabase.insert("CongViec", null, contentValues);
    }

    public ArrayList<CongViec> select() {
        ArrayList<CongViec> listCongViec = new ArrayList<>();
        Cursor cursor = sqLiteDatabase.rawQuery("select * from CongViec", null);//doc du lieu
        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String tenCV = cursor.getString(1);
                CongViec congViec = new CongViec(id, tenCV);
                listCongViec.add(congViec);
            }
            while (cursor.moveToNext());
        }
        return listCongViec;
    }

    public void delete(int id) {
        sqLiteDatabase.delete("CongViec", "Id = ?", new String[]{id + ""});
    }

    public void update(CongViec congViec) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("TenCV", congViec.getTenCV());
        sqLiteDatabase.update("CongViec", contentValues, "Id = ?", new String[]{congViec.getIdCV() + ""});
    }

}
