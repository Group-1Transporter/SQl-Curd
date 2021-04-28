package com.example.sql_curd_contact;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.strictmode.SqliteObjectLeakedViolation;

import java.util.ArrayList;

public class ContactDov  {
    public static boolean add(Contact contact,SqliteHelper helper){

        boolean status =false;
        ContentValues contentValues = new ContentValues();
        contentValues.put("name",contact.getName());
        contentValues.put("number",contact.getNumber());
        SQLiteDatabase db = helper.getWritableDatabase();
        db.insert("contact",null,contentValues);
        status = true;
        return status;
    }
    public static ArrayList<Contact> readContact(SqliteHelper helper){
        ArrayList<Contact> list = new ArrayList<>();
        String sql = "select id,name,number from contact";
        SQLiteDatabase database = helper.getReadableDatabase();
        Cursor c = database.rawQuery(sql,null);
        while (c.moveToNext()){
            int id = c.getInt(0);
            String name =c.getString(1);
            int number = c.getInt(2);
            Contact contact1 = new Contact(id,name,number);
            list.add(contact1);
        }
        return list;
    }
    public static boolean delete(Contact contact,SqliteHelper helper){
        boolean status= false;
        SQLiteDatabase db = helper.getWritableDatabase();
        db.delete("contact","id=?",new String[]{""+contact.getId()});
        status = true;
        return status;
    }
    public static boolean update(Contact contact,SqliteHelper helper) {
        boolean status = false;
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", contact.getName());
        contentValues.put("number", contact.getNumber());
        db.update("contact", contentValues, "id=?", new String[]{"" + contact.getId()});
        status = true;
        return status;
    }
}
