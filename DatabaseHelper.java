package com.example.priyanka_dash.ecomapp.sql;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.priyanka_dash.ecomapp.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Priyanka_Dash on 12/6/2017.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "UserDetails";
    private static final String TABLE_USER = "User";
    private static final String COLUMN_USER_ID = "user_id";
    private static final String COLUMN_USER_NAME = "name";
    private static final String COLUMN_USER_EMAIL = "email";
    private static final String COLUMN_USER_PASSWORD = "password";
    private static final String COLUMN_USER_CONFIRMPASSWORD = "confirmPassword";

    private String CREATE_USER_TABLE = "CREATE TABLE " + TABLE_USER + "("
            + COLUMN_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COLUMN_USER_NAME + "TEXT, "
            + COLUMN_USER_EMAIL + "TEXT, "
            + COLUMN_USER_PASSWORD + "TEXT, "
            + COLUMN_USER_CONFIRMPASSWORD + " TEXT" + ")";

    private String DROP_USER_TABLE = "DROP TABLE IF EXISTS " + TABLE_USER;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_USER_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DROP_USER_TABLE);
        onCreate(db);
    }

    public void addUser(User user){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_USER_NAME,user.getName());
        contentValues.put(COLUMN_USER_EMAIL,user.getEmail());
        contentValues.put(COLUMN_USER_PASSWORD,user.getPassword());
        contentValues.put(COLUMN_USER_CONFIRMPASSWORD,user.getConfirmPassword());

        db.insert(TABLE_USER,null,contentValues);
        db.close();
    }

    public List<User> getAllUser(){
        String[] columns = {
                COLUMN_USER_ID,COLUMN_USER_NAME, COLUMN_USER_EMAIL, COLUMN_USER_PASSWORD, COLUMN_USER_CONFIRMPASSWORD
        };
        String sortOrder =
                COLUMN_USER_NAME + " ASC";
        List<User> userList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_USER,columns,null,null,null,null,sortOrder);

        if(cursor.moveToFirst()){
            do{
                User user = new User();
                user.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_USER_ID))));
                user.setName(cursor.getString(cursor.getColumnIndex(COLUMN_USER_NAME)));
                user.setEmail(cursor.getString(cursor.getColumnIndex(COLUMN_USER_EMAIL)));
                user.setPassword(cursor.getString(cursor.getColumnIndex(COLUMN_USER_PASSWORD)));
                user.setConfirmPassword(cursor.getString(cursor.getColumnIndex(COLUMN_USER_CONFIRMPASSWORD)));

                userList.add(user);
            } while (cursor.moveToNext());
        } cursor.close();
        db.close();
        return userList;

    }

    public void updateUser(User user){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_USER_NAME,user.getName());
        values.put(COLUMN_USER_EMAIL,user.getEmail());
        values.put(COLUMN_USER_PASSWORD,user.getPassword());
        values.put(COLUMN_USER_CONFIRMPASSWORD,user.getConfirmPassword());

        sqLiteDatabase.update(TABLE_USER,values,COLUMN_USER_ID + "=?", new String[]{String.valueOf(user.getId())});
        sqLiteDatabase.close();
    }

    public boolean checkUser(String email){
        String[] columns = {COLUMN_USER_ID};
        SQLiteDatabase db = this.getReadableDatabase();
        String selection = COLUMN_USER_EMAIL + "=?";

        String[] selectionArgs = {email};

        Cursor cur = db.query(TABLE_USER,columns,selection,selectionArgs,null,null,null);
        int cursorCount = cur.getCount();
        cur.close();
        db.close();
        if(cursorCount>0){
            return true;
        } return false;
    }

    public boolean checkUser(String email, String password) {

        // array of columns to fetch
        String[] columns = {COLUMN_USER_ID};
        SQLiteDatabase db = this.getReadableDatabase();
        // selection criteria
        String selection = COLUMN_USER_EMAIL + " = ?" + " AND " + COLUMN_USER_PASSWORD + " = ?";

        // selection arguments
        String[] selectionArgs = {email, password};
        Cursor cursor = db.query(TABLE_USER, //Table to query
                columns,                    //columns to return
                selection,                  //columns for the WHERE clause
                selectionArgs,              //The values for the WHERE clause
                null,                       //group the rows
                null,                       //filter by row groups
                null);                      //The sort order

        int cursorCount = cursor.getCount();

        cursor.close();
        db.close();
        if (cursorCount > 0) {
            return true;
        }

        return false;
    }

    }
