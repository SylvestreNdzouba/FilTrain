package com.example.filtrain;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class HelperClass extends SQLiteOpenHelper {

    //public static final String databaseName = "FilTrain.db";
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "FilTrain2.db";

    public HelperClass(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
/*
    public HelperClass(@Nullable Context context) {
        super(context, "FilTrain.db", null, 1);
    }
*/
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        /*sqLiteDatabase.execSQL("create Table filtrain(nom TEXT, prenom TEXT, " +
                "email TEXT primary key, password TEXT)");
        */
        String query =  "CREATE TABLE " + DBContract.Form.TABLE_NAME + " (" +
                DBContract.Form._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                DBContract.Form.COLUMN_NAME + " TEXT," +
                DBContract.Form.COLUMN_PRENAME + " TEXT," +
                DBContract.Form.COLUMN_EMAIL + " TEXT," +
                DBContract.Form.COLUMN_PASSWORD + " TEXT)";
        sqLiteDatabase.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        //sqLiteDatabase.execSQL("drop Table if exists filtrain");
        String query = "DROP TABLE IF EXISTS " + DBContract.Form.TABLE_NAME;
        sqLiteDatabase.execSQL(query);
        onCreate(sqLiteDatabase);
    }

    public void insertData(String nom, String prenom, String email, String password){
        SQLiteDatabase myDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
/*
        contentValues.put("nom", nom);
        contentValues.put("prenom", prenom);
        contentValues.put("email", email);
        contentValues.put("password", password);
*/
        //SQLiteDatabase db = this.getWritableDatabase();
        //ContentValues values = new ContentValues();
        contentValues.put(DBContract.Form.COLUMN_NAME, nom);
        contentValues.put(DBContract.Form.COLUMN_PRENAME, prenom);
        contentValues.put(DBContract.Form.COLUMN_EMAIL, email);
        contentValues.put(DBContract.Form.COLUMN_PASSWORD, password);
        myDatabase.insert(DBContract.Form.TABLE_NAME, null, contentValues);
        myDatabase.close();
/*
        long result = myDatabase.insert(DBContract.Form.TABLE_NAME, null, contentValues);
        if (result == -1){
            return false;
        } else {
            return true;
        }*/
    }

    public User getUserData(String username) {
        SQLiteDatabase db = this.getReadableDatabase();
        User user = null;
        Cursor cursor = db.query(DBContract.Form.TABLE_NAME, new String[] { DBContract.Form._ID,
                        DBContract.Form.COLUMN_NAME, DBContract.Form.COLUMN_PRENAME,
                        DBContract.Form.COLUMN_EMAIL,DBContract.Form.COLUMN_PASSWORD },
                DBContract.Form.COLUMN_NAME + "=?", new String[] { username },
                null, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            int id = Integer.parseInt(cursor.getString(0));
            String nom = cursor.getString(1);
            String prenom = cursor.getString(2);
            String email = cursor.getString(3);
            String password = cursor.getString(4);
            user = new User(nom, prenom, email, password);
        }
        cursor.close();
        db.close();
        return user;
    }

    public List<User> selectAll() {
        List<User> users = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String[] projection = {
                DBContract.Form._ID,
                DBContract.Form.COLUMN_NAME,
                DBContract.Form.COLUMN_PRENAME,
                DBContract.Form.COLUMN_EMAIL,
                DBContract.Form.COLUMN_PASSWORD
        };

        Cursor cursor = db.query(
                DBContract.Form.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                null
        );

        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndexOrThrow(DBContract.Form._ID));
            String nom = cursor.getString(cursor.getColumnIndexOrThrow(DBContract.Form.COLUMN_NAME));
            String prenom = cursor.getString(cursor.getColumnIndexOrThrow(DBContract.Form.COLUMN_PRENAME));
            String email = cursor.getString(cursor.getColumnIndexOrThrow(DBContract.Form.COLUMN_EMAIL));
            String password = cursor.getString(cursor.getColumnIndexOrThrow(DBContract.Form.COLUMN_PASSWORD));

            User user = new User(nom, prenom, email, password);

            users.add(user);
        }
        return users;
    }
// à remettre en commentaire
    public Boolean checkEmail(String email){
        SQLiteDatabase myDatabase = this.getWritableDatabase();
        Cursor cursor = myDatabase.rawQuery("Select * from " +DBContract.Form.TABLE_NAME+" where email =?",
                new String[]{email});

        if (cursor.getCount() >0){
            return true;
        } else {
            return false;
        }
    }

    public Boolean checkEmailPassword(String email, String password) {
        SQLiteDatabase myDatabase = this.getWritableDatabase();
        Cursor cursor = myDatabase.rawQuery("Select * from " +DBContract.Form.TABLE_NAME+" where email =? and password =?",
                new String[]{email, password});

        if (cursor.getCount()>0){
            return true;
        } else {
            return false;
        }
    }
//à remettre en commentaire
    public boolean verifConnexion(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(DBContract.Form.TABLE_NAME, new String[]{DBContract.Form._ID},
                DBContract.Form.COLUMN_NAME + "=? AND " + DBContract.Form.COLUMN_PASSWORD + "=?",
                new String[]{username, password}, null, null, null, null);

        boolean result = cursor.moveToFirst();

        cursor.close();
        db.close();

        return result;
    }

    public boolean verifUsername(String username) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(DBContract.Form.TABLE_NAME, null,
                DBContract.Form.COLUMN_NAME + "=?",
                new String[]{username}, null, null, null, null);

        boolean result = cursor.moveToFirst();
        cursor.close();
        db.close();
        return result;
    }

    public void deleteForm (String username ){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(DBContract.Form.TABLE_NAME, DBContract.Form.COLUMN_NAME + "=?", new String[] { username });
        db.close();
    }

    public void deleteFormID (int id){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(DBContract.Form.TABLE_NAME, DBContract.Form._ID + "=?", new String[] { String.valueOf(id) });
        db.close();
    }


}
