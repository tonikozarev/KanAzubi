package com.example.techgeek.kanazubi.SQL;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.techgeek.kanazubi.Model.User;

public class DatabaseHelper extends SQLiteOpenHelper {

    private SQLiteDatabase db;
    private static final int DB_VERSION = 1;
    //database with both tables
    private static final String DB_NAME = "KanAzubiDB.db";
    //users table
    private static final String TABLE_NAME = "users";
    private static final String COLUMB_ID = "ID";
    private static final String COLUMB_EMAIL = "Email";
    private static final String COLUMB_PASSWORD = "Password";
    private static final String COLUMB_NAME = "Name";
    //tasks table
    private static final String TABLE_NAME2 = "tasks";
    private static final String COLUMB_ID2 = "ID";
    private static final String COLUMB_TITLE = "Title";
    private static final String COLUMB_DESCR = "Description";
    private static final String COLUMB_RELEASED = "Released";
    private static final String COLUMB_TEAM = "Team";
    private static final String COLUMB_STATUS = "Status";
    private static final String COLUMB_USERID = "User_ID";

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String tableCreate = "create table " + TABLE_NAME + " (" + COLUMB_ID + " integer primary key not null, " + COLUMB_EMAIL + " text not null, " + COLUMB_PASSWORD + " text not null, " + COLUMB_NAME + " text not null);";
        db.execSQL(tableCreate);
        this.db = db;
    }

    public boolean checkIfExist(String email) {
        boolean exist = false;
        db = this.getReadableDatabase();
        String queryPass = "select " + COLUMB_EMAIL + " from " + TABLE_NAME;
        @SuppressLint("Recycle")
        Cursor crs = db.rawQuery(queryPass, null);
        String emailStr;
        if (crs.moveToFirst()) {
            do {
                emailStr = crs.getString(0);
                if (emailStr.equals(email)) {
                    exist = true;
                }
            } while (crs.moveToNext());
        }
        crs.close();
        db.close();
        return exist;
    }

    public int searchForID(String email) {
        db = this.getReadableDatabase();
        String queryPass = "select " + COLUMB_ID + " , " + COLUMB_EMAIL + " from " + TABLE_NAME;
        @SuppressLint("Recycle")
        Cursor crs = db.rawQuery(queryPass, null);
        String emailStr;
        int idStr = 0;
        if (crs.moveToFirst()) {
            do {
                emailStr = crs.getString(1);
                if (emailStr.equals(email)) {
                    idStr = crs.getInt(0);
                    break;
                }

            } while (crs.moveToNext());
        }
        crs.close();
        db.close();
        return idStr;
    }

    public String searchForName(String email) {
        db = this.getReadableDatabase();
        String queryPass = "select " + COLUMB_EMAIL + " , " + COLUMB_NAME + " from " + TABLE_NAME;
        @SuppressLint("Recycle")
        Cursor crs = db.rawQuery(queryPass, null);
        String emailStr, nameStr = "";
        if (crs.moveToFirst()) {
            do {
                emailStr = crs.getString(0);
                if (emailStr.equals(email)) {
                    nameStr = crs.getString(1);
                    break;
                }

            } while (crs.moveToNext());
        }
        crs.close();
        db.close();
        return nameStr;
    }

    public String searchForPass(String email) {
        db = this.getReadableDatabase();
        String queryPass = "select " + COLUMB_EMAIL + ", " + COLUMB_PASSWORD + " from " + TABLE_NAME;
        @SuppressLint("Recycle")
        Cursor crs = db.rawQuery(queryPass, null);
        String emailStr, passwordStr = "";
        if (crs.moveToFirst()) {
            do {
                emailStr = crs.getString(0);
                if (emailStr.equals(email)) {
                    passwordStr = crs.getString(1);
                    break;
                }
            } while (crs.moveToNext());
        }
        crs.close();
        db.close();
        return passwordStr;
    }

    public void insertUser(User user) {
        db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        String query = "SELECT * FROM " + TABLE_NAME;
        @SuppressLint("Recycle")
        Cursor crs = db.rawQuery(query, null);
        int count = crs.getCount() + 1; //start autoincrement id from '1'! ('0' is the default value for open tasks, which are not related to any user.)
        cv.put(COLUMB_ID, count);
        cv.put(COLUMB_EMAIL, user.getEmail());
        cv.put(COLUMB_NAME, user.getName());
        cv.put(COLUMB_PASSWORD, user.getPassword());
        db.insert(TABLE_NAME, null, cv);
        crs.close();
        db.close();
    }

    public void addTask(String title, String descr, String released, String team) {
        db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        String query = "SELECT * FROM " + TABLE_NAME2;
        @SuppressLint("Recycle")
        Cursor crs = db.rawQuery(query, null);
        int count = crs.getCount() + 1;
        cv.put(COLUMB_ID, count);
        cv.put(COLUMB_TITLE, title);
        cv.put(COLUMB_DESCR, descr);
        cv.put(COLUMB_RELEASED, released);
        cv.put(COLUMB_STATUS, "Offen");
        cv.put(COLUMB_TEAM, team);
        cv.put(COLUMB_USERID, 0);
        db.insert(TABLE_NAME2, null, cv);
        crs.close();
        db.close();
    }

    public Cursor queryList() {
        db = this.getReadableDatabase();
        //alternative: (select * from) without changing column position
        String query = "select " + COLUMB_ID2 + ", " + COLUMB_TITLE + ", " + COLUMB_TEAM + ", " + COLUMB_STATUS + ", " +
                COLUMB_USERID + ", " + COLUMB_RELEASED + ", " + COLUMB_DESCR + " from " + TABLE_NAME2;
        return db.rawQuery(query, null);
    }

    public void acceptTask(int userID, int taskNr) {
        db = this.getWritableDatabase();
        String acceptTask = "In Bearbeitung";
        String query = "UPDATE " + TABLE_NAME2 + " SET " + COLUMB_STATUS + " = '" +
                acceptTask + "'" + ", " + COLUMB_USERID +
                " = '" + userID + "'" + " WHERE " + COLUMB_ID2 + " = '" + taskNr + "'";
        db.execSQL(query);
        db.close();
    }

    public void doneTask(int taskNr) {
        db = this.getWritableDatabase();
        String doneTask = "Erledigt";
        String query = "UPDATE " + TABLE_NAME2 + " SET " + COLUMB_STATUS + " = '" +
                doneTask + "' WHERE " + COLUMB_ID2 + " = '" + taskNr + "'";
        db.execSQL(query);
        db.close();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String tableDrop = "DROP TABLE IF EXISTS " + TABLE_NAME;
        db.execSQL(tableDrop);
        this.onCreate(db);
    }
}