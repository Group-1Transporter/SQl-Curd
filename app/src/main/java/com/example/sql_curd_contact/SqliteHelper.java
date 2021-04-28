package com.example.sql_curd_contact;

import android.app.DownloadManager;
import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class SqliteHelper extends SQLiteOpenHelper {
    public SqliteHelper(@Nullable Context context) {
        super(context, "contcatdb",null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String sql ="create table contact(id Integer Primary Key AUTOINCREMENT,name Varchar(50),number int(11))";
        db.execSQL(sql);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
