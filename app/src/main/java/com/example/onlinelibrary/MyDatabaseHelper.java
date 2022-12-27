package com.example.onlinelibrary;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class MyDatabaseHelper extends SQLiteOpenHelper{

    private Context context;
    public static final String DATABASE_NAME = "LibraryManager.db";
    public static final int DATABASE_VERSION = 1;

    public static final String BOOKS_TABLE = "Books";
    public static final String LIBRARY_TABLE = "Libraries";
    public static final String AUTHOR_TABLE = "Authors";

    public static final String BOOK_ID = "book_id";
    public static final String BOOK_NAME = "name";
    public static final String BOOK_YEAR = "year";
    public static final String BOOK_PUBLISHER = "publisher";

    public static final String AUTHOR_ID = "author_id";
    public static final String AUTHOR_LASTNAME = "lastname";
    public static final String AUTHOR_FIRSTNAME = "firstname";
    public static final String AUTHOR_COUNTRY = "country";

    public static final String LIBRARY_ID = "library_id";
    public static final String LIBRARY_NAME = "name";
    public static final String LIBRARY_ADDRESS = "address";

    public static final String USERS_TABLE = "Users";
    public static final String USER_USERNAME = "username";
    public static final String USER_PASSWORD = "password";



    public MyDatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String createBooksStatement = "CREATE TABLE " + BOOKS_TABLE + " (" +
                BOOK_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                BOOK_NAME + " TEXT, " +
                BOOK_YEAR + " INTEGER, " +
                BOOK_PUBLISHER + " TEXT);";
        String createAuthorStatement = "CREATE TABLE " + AUTHOR_TABLE + " (" +
                AUTHOR_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                AUTHOR_LASTNAME + " TEXT, " +
                AUTHOR_FIRSTNAME + " TEXT, " +
                AUTHOR_COUNTRY + " TEXT);";
        String createLibraryStatement = "CREATE TABLE " + LIBRARY_TABLE + " (" +
                LIBRARY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                LIBRARY_NAME + " TEXT, " +
                LIBRARY_ADDRESS + " TEXT);";

        String createUserStatement = "CREATE TABLE " + USERS_TABLE + " (" +
                USER_USERNAME + " TEXT Primary Key, " +
                USER_PASSWORD + " TEXT);";

        sqLiteDatabase.execSQL(createAuthorStatement);
        sqLiteDatabase.execSQL(createBooksStatement);
        sqLiteDatabase.execSQL(createLibraryStatement);
        sqLiteDatabase.execSQL(createUserStatement);

        ContentValues contentValues = new ContentValues();
        contentValues.put(USER_USERNAME, "admin");
        contentValues.put(USER_PASSWORD, "admin");
        long result = sqLiteDatabase.insert(USERS_TABLE, null, contentValues);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + BOOKS_TABLE);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + AUTHOR_TABLE);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + LIBRARY_TABLE);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + USERS_TABLE);
        onCreate(sqLiteDatabase);
    }

    public Boolean insertUser(String username, String password){
        SQLiteDatabase MyDB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(USER_USERNAME, username);
        contentValues.put(USER_PASSWORD, password);
        long result = MyDB.insert(USERS_TABLE, null, contentValues);
        return result != -1;
    }

    public Boolean checkusername(String username){
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("SELECT * FROM " + USERS_TABLE + " WHERE " + USER_USERNAME + " = ?", new String[] {username});
        return cursor.getCount() > 0;
    }

    public Boolean checkUsernamePassword(String username, String password){
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("SELECT * FROM " + USERS_TABLE + " WHERE " +
                USER_USERNAME + " = ?  AND " + USER_PASSWORD + " = ?", new String[] {username,password});
        return cursor.getCount() > 0;
    }
}
