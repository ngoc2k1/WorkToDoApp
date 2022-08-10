package com.bichngoc.worktodoapplication;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class Database extends SQLiteOpenHelper {
    public Database(@Nullable Context context) {
        super(context, "work", null, 1);

    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String data = "CREATE TABLE CongViec(Id INTEGER PRIMARY KEY AUTOINCREMENT,TenCV VARCHAR(200))";
        sqLiteDatabase.execSQL(data);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("drop table if exists CongViec");
        onCreate(sqLiteDatabase);
    }
}
