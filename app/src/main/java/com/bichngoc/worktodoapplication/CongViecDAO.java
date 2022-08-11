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
        //khi khởi tạo ở MainActivity. ta truyền context(tài nguyên của Main) vào db -> db được khởi tạo
        // -> db cho phép sqlitedb có quyền thực hiện các truy cấn
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
        Cursor cursor = sqLiteDatabase.rawQuery("select * from CongViec", null);//con trỏ để lấy ra dl của bảng đó
        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String tenCV = cursor.getString(1);
                CongViec congViec = new CongViec(id, tenCV);
                listCongViec.add(congViec);
            }
            while (cursor.moveToNext());
        }
        cursor.close();
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
