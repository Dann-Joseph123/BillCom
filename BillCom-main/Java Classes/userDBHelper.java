package com.secondapplication.app;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;
public class userDBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "users.db";
    public static final int DATABASE_VERSION = 1;
    public static final String USERS_TABLE = "USERS_TABLE";
    //public static final String COLUMN_ID = "ID";
    public static final String COLUMN_USERNAME = "USERNAME";
    public static final String COLUMN_PASSWORD = "PASSWORD";
    public static final String COLUMN_BIRTHYEAR = "BIRTHYEAR";
    public static final String COLUMN_BIRTHMONTH = "BIRTHMONTH";
    public static final String COLUMN_BIRTHDATE = "BIRTHDATE";

    public userDBHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableStatement = "CREATE TABLE " + USERS_TABLE +
                " (" + COLUMN_USERNAME + " TEXT PRIMARY KEY, " +
                COLUMN_PASSWORD + " TEXT, " +
                COLUMN_BIRTHYEAR + " INT, " +
                COLUMN_BIRTHMONTH + " INT, " +
                COLUMN_BIRTHDATE + " INT)";

        db.execSQL(createTableStatement);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public boolean addOne(User user){
        SQLiteDatabase db = this.getWritableDatabase();     //gets a writable db
        ContentValues cv = new ContentValues();             //content values acts like a hashmap

        cv.put(COLUMN_USERNAME, user.getUserName());
        cv.put(COLUMN_PASSWORD, user.getPassword());
        cv.put(COLUMN_BIRTHYEAR, user.getBirthYear());
        cv.put(COLUMN_BIRTHMONTH, user.getBirthMonth());
        cv.put(COLUMN_BIRTHDATE, user.getBirthDate());

        long insert = db.insert(USERS_TABLE, null, cv);

        return insert != -1;
    }

    public Boolean checkUsername(String username){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("Select * from " + USERS_TABLE + " where " + COLUMN_USERNAME + " = ?", new String[] {username});
        return cursor.getCount() > 0;
    }

    public Boolean checkUsernamePassword(String username, String password){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("Select * from " + USERS_TABLE + " where " + COLUMN_USERNAME + " = ? and " + COLUMN_PASSWORD + " = ?", new String[] {username,password});
        return cursor.getCount() > 0;
    }

    public Boolean checkUsernameBirthday(String username, String birthYear, String birthMonth, String birthDate){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("Select * from " + USERS_TABLE + " where " + COLUMN_USERNAME
                + " = ? and " + COLUMN_BIRTHYEAR + " = ? and " + COLUMN_BIRTHMONTH + " = ? and "
                + COLUMN_BIRTHDATE + " = ?", new String[] {username,birthYear,birthMonth,birthDate});
        return cursor.getCount() > 0;
    }

    public long queryNumEntries(){
        SQLiteDatabase db = this.getReadableDatabase();
        return DatabaseUtils.queryNumEntries(db, USERS_TABLE);  //return number of users in db
    }

    //@SuppressLint("Range")
    public User getSingleUserInfo(String username){
        SQLiteDatabase db = this.getWritableDatabase();
        User user = null;

        Cursor cursor = db.rawQuery("SELECT * FROM " + USERS_TABLE + " WHERE " + COLUMN_USERNAME + " = ?", new String[]{username});
        if (cursor.moveToFirst()){
            user = new User(cursor.getString(0), cursor.getString(1), cursor.getInt(2), cursor.getInt(3), cursor.getInt(4));
        }
        return user;
    }

    public boolean deleteOne(User user){
        //find appliance in appliance.db, if found, return true. else false.

        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(USERS_TABLE, COLUMN_USERNAME + "=?", new String[]{user.getUserName()}) > 0;
    }

    public boolean updateUserDetails(User userToUpdate, User updatedUser){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        String userToEdit = String.valueOf(userToUpdate.getUserName()); //selected user

        cv.put(COLUMN_USERNAME, updatedUser.getUserName());
        //cv.put(COLUMN_PASSWORD, applianceToUpdate.getElectricCapacity());
        cv.put(COLUMN_BIRTHYEAR, updatedUser.getBirthYear());
        cv.put(COLUMN_BIRTHMONTH, updatedUser.getBirthMonth());
        cv.put(COLUMN_BIRTHDATE, updatedUser.getBirthDate()); //energy per day
        Cursor cursor = db.rawQuery("Select * from " + USERS_TABLE + " where " + COLUMN_USERNAME + " = ?", new String[]{userToEdit});

        if(cursor.getCount()>0){
            long result = db.update(USERS_TABLE, cv, COLUMN_USERNAME + "=?", new String[]{userToEdit});
            return result != -1;
        }else{ return false; }
    }

    public boolean updateUserPassword(String userToUpdate, String updatedPassword){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        //String userToEdit = userToUpdate; //selected user

        cv.put(COLUMN_PASSWORD, updatedPassword);
        Cursor cursor = db.rawQuery("Select * from " + USERS_TABLE + " where " + COLUMN_USERNAME + " = ?", new String[]{userToUpdate});

        if(cursor.getCount()>0){
            long result = db.update(USERS_TABLE, cv, COLUMN_USERNAME + "=?", new String[]{userToUpdate});
            return result != -1;
        }else{ return false; }
    }
}

