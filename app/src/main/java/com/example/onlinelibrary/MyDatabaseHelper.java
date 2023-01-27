package com.example.onlinelibrary;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

public class MyDatabaseHelper extends SQLiteOpenHelper {

    private Context context;
    public static final String DATABASE_NAME = "LibraryManager.db";
    public static final int DATABASE_VERSION = 2;

    public static final String BOOKS_TABLE = "Books";
    public static final String LIBRARY_TABLE = "Libraries";
    public static final String AUTHOR_TABLE = "Authors";
    public static final String INVENTORY_TABLE = "Inventory";
    public static final String CREATIVE_TABLE = "Creative";

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

    public static final String INVENTORY_ID = "inventory_id";
    public static final String INVENTORY_BOOK = "book_id";
    public static final String INVENTORY_LIBRARY = "library_id";

    public static final String CREATIVE_ID = "creative_id";
    public static final String CREATIVE_BOOK = "book_id";
    public static final String CREATIVE_AUTHOR = "author_id";

    public static final String USERS_TABLE = "Users";
    public static final String USER_USERNAME = "username";
    public static final String USER_PASSWORD = "password";
    public static final String USER_PERMISSION = "permission";


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

        String createInventoryStatement = "CREATE TABLE " + INVENTORY_TABLE + " (" +
                INVENTORY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                INVENTORY_BOOK + " INTEGER, " +
                INVENTORY_LIBRARY + " INTEGER, " +
                "FOREIGN KEY(" + INVENTORY_BOOK + ") REFERENCES " + BOOKS_TABLE + "(" + BOOK_ID + "), " +
                "FOREIGN KEY(" + INVENTORY_LIBRARY + ") REFERENCES " + LIBRARY_TABLE + "(" + LIBRARY_ID + "));";

        String createCreativeStatement = "CREATE TABLE " + CREATIVE_TABLE + " (" +
                CREATIVE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                CREATIVE_BOOK + " INTEGER, " +
                CREATIVE_AUTHOR + " INTEGER, " +
                "FOREIGN KEY(" + CREATIVE_BOOK + ") REFERENCES " + BOOKS_TABLE + "(" + BOOK_ID + "), " +
                "FOREIGN KEY(" + CREATIVE_AUTHOR + ") REFERENCES " + AUTHOR_TABLE + "(" + AUTHOR_ID + "));";

        String createUserStatement = "CREATE TABLE " + USERS_TABLE + " (" +
                USER_USERNAME + " TEXT Primary Key, " +
                USER_PASSWORD + " TEXT," +
                USER_PERMISSION + " TEXT);";

        sqLiteDatabase.execSQL(createAuthorStatement);
        sqLiteDatabase.execSQL(createBooksStatement);
        sqLiteDatabase.execSQL(createLibraryStatement);
        sqLiteDatabase.execSQL(createCreativeStatement);
        sqLiteDatabase.execSQL(createUserStatement);
        sqLiteDatabase.execSQL(createInventoryStatement);

        ContentValues contentValues = new ContentValues();
        contentValues.put(USER_USERNAME, "admin");
        contentValues.put(USER_PASSWORD, "admin");
        contentValues.put(USER_PERMISSION, "admin");

        sqLiteDatabase.insert(USERS_TABLE, null, contentValues);

        contentValues.put(USER_USERNAME, "user");
        contentValues.put(USER_PASSWORD, "user");
        contentValues.put(USER_PERMISSION, "user");

        sqLiteDatabase.insert(USERS_TABLE, null, contentValues);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + BOOKS_TABLE);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + AUTHOR_TABLE);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + LIBRARY_TABLE);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + USERS_TABLE);
        onCreate(sqLiteDatabase);
    }

    public Cursor getInventory() {
        SQLiteDatabase MyDB = this.getReadableDatabase();
        Cursor cursor = MyDB.query(INVENTORY_TABLE, new String[]{INVENTORY_ID + " as _id", INVENTORY_LIBRARY, INVENTORY_BOOK}, null, null, null, null, null);
        return cursor;
    }

    public Cursor getInventoryMatch(){
        SQLiteDatabase MyDB = this.getReadableDatabase();
        String query = "select " +
                "i." + INVENTORY_ID + " as _id, " +
                "i." + INVENTORY_LIBRARY + ", " +
                "i." + INVENTORY_BOOK + ", " +
                "l." + LIBRARY_NAME +  " as libraryName, " +
                "l." + LIBRARY_ADDRESS + ", " +
                "b." + BOOK_NAME + " as bookName, " +
                "b." + BOOK_YEAR + ", " +
                "b." + BOOK_PUBLISHER +
                " from " +
                INVENTORY_TABLE + " as i, " +
                LIBRARY_TABLE + " as l, " +
                BOOKS_TABLE + " as b " +
                " where " +
                "i." + INVENTORY_BOOK + " = " + "b." + BOOK_ID +
                " and " +
                "i." + INVENTORY_LIBRARY + " = " + "l." + LIBRARY_ID;
        Cursor cursor = MyDB.rawQuery(query, null);

        return cursor;
    }

    public Boolean insertInventory(Integer library, Integer book) {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(INVENTORY_BOOK, book);
        contentValues.put(INVENTORY_LIBRARY, library);
        long result = MyDB.insert(INVENTORY_TABLE, null, contentValues);
        return result != -1;
    }

    public Cursor getInventoryByID(long id) {
        Log.i("id_inventory:", String.valueOf(id));
        SQLiteDatabase MyDB = this.getReadableDatabase();

        String query = "select " + INVENTORY_ID + " as _id, " + INVENTORY_LIBRARY + ", " +
                INVENTORY_BOOK + " from " + INVENTORY_TABLE + " where " +
                INVENTORY_ID + " = " + id;
        Cursor cursor = MyDB.rawQuery(query, null);

        return cursor;
    }

    public void updateInventory(String id, String library, String book) {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        String query = "update " + INVENTORY_TABLE + " set " + INVENTORY_BOOK + " = '" + book +
                "', " + INVENTORY_LIBRARY + " = '" + library + "' where " + INVENTORY_ID +
                " = " + id;
        Log.i("query: ", query);
        MyDB.execSQL(query);
    }
    public void deleteInventory(String id){
        SQLiteDatabase MyDB = this.getWritableDatabase();
        String query = " delete from " + INVENTORY_TABLE + " where " + INVENTORY_ID + " = " + id;
        MyDB.execSQL(query);
    }

    public Cursor getCreative() {
        SQLiteDatabase MyDB = this.getReadableDatabase();
        Cursor cursor = MyDB.query(CREATIVE_TABLE, new String[]{CREATIVE_ID + " as _id", CREATIVE_BOOK, CREATIVE_AUTHOR}, null, null, null, null, null);
        return cursor;
    }

    public Cursor getCreativeMatch(){
        SQLiteDatabase MyDB = this.getReadableDatabase();
        String query = "select " +
                "c." + CREATIVE_ID + " as _id, " +
                "c." + CREATIVE_BOOK + ", " +
                "c." + CREATIVE_AUTHOR + ", " +
                "b." + BOOK_NAME +  " as bookName, " +
                "b." + BOOK_YEAR + ", " +
                "b." + BOOK_PUBLISHER + ", " +
                "a." + AUTHOR_FIRSTNAME + ", " +
                "a." + AUTHOR_LASTNAME + ", " +
                "a." + AUTHOR_COUNTRY +
                " from " +
                CREATIVE_TABLE + " as c, " +
                BOOKS_TABLE + " as b, " +
                AUTHOR_TABLE + " as a " +
                " where " +
                "c." + CREATIVE_BOOK + " = " + "b." + BOOK_ID +
                " and " +
                "c." + CREATIVE_AUTHOR + " = " + "a." + AUTHOR_ID;
        Cursor cursor = MyDB.rawQuery(query, null);

        return cursor;
    }

    public Boolean insertCreative(Integer book, Integer author) {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(CREATIVE_BOOK, book);
        contentValues.put(CREATIVE_AUTHOR, author);
        long result = MyDB.insert(CREATIVE_TABLE, null, contentValues);
        return result != -1;
    }

    public Cursor getCreativeByID(long id) {
        SQLiteDatabase MyDB = this.getReadableDatabase();
        String query = "select " + CREATIVE_ID + " as _id, " + CREATIVE_BOOK + ", " +
                CREATIVE_AUTHOR + " from " + CREATIVE_TABLE + " where " +
                CREATIVE_ID + " = " + id;
        Cursor cursor = MyDB.rawQuery(query, null);
        return cursor;
    }

    public void updateCreative(String id, String book, String author) {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        String query = "update " + CREATIVE_TABLE + " set " + CREATIVE_BOOK + " = '" + book +
                "', " + CREATIVE_AUTHOR + " = '" + author + "' where " + CREATIVE_ID +
                " = " + id;
        Log.i("query: ", query);
        MyDB.execSQL(query);
    }
    public void deleteCreative(String id){
        SQLiteDatabase MyDB = this.getWritableDatabase();
        String query = " delete from " + CREATIVE_TABLE + " where " + CREATIVE_ID + " = " + id;
        MyDB.execSQL(query);
    }

    public Cursor getLibrary() {
        SQLiteDatabase MyDB = this.getReadableDatabase();
        Cursor cursor = MyDB.query(LIBRARY_TABLE, new String[]{LIBRARY_ID + " as _id", LIBRARY_NAME, LIBRARY_ADDRESS}, null, null, null, null, null);
        Log.i("cursor_library", cursor.getColumnName(1).toString());
        return cursor;
    }

    public Cursor getLibraryByID(long id) {
        Log.i("id_library:", String.valueOf(id));
        SQLiteDatabase MyDB = this.getReadableDatabase();
//        Cursor cursor = MyDB.query(LIBRARY_TABLE,new String[]{LIBRARY_ID+" as _id",LIBRARY_NAME,LIBRARY_ADDRESS},"_id = " + id,null,null,null,null);
////        Log.i("by id "+id+": ",String.valueOf(cursor.getType(1)));
//        Log.i("by id "+id+": ",String.valueOf(cursor.getCount()));

        String query = "select " + LIBRARY_ID + " as _id, " + LIBRARY_NAME + ", " +
                LIBRARY_ADDRESS + " from " + LIBRARY_TABLE + " where " +
                LIBRARY_ID + " = " + id;
        Cursor cursor = MyDB.rawQuery(query, null);
//        Log.i("by id "+id+": ",String.valueOf(cursor.getColumnName(0)));
//        Log.i("by id "+id+": ",String.valueOf(cursor.getColumnName(1)));
//        Log.i("by id "+id+": ",String.valueOf(cursor.getCount()));

//        cursor.moveToFirst();
//        Log.i("by id "+id+": ",String.valueOf(cursor.getColumnIndex(LIBRARY_NAME)));
//        Log.i("by id "+id+": ",cursor.getString(cursor.getColumnIndex(LIBRARY_NAME)));

        return cursor;
    }

    public Boolean insertLibrary(String name, String address) {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(LIBRARY_NAME, name);
        contentValues.put(LIBRARY_ADDRESS, address);
        long result = MyDB.insert(LIBRARY_TABLE, null, contentValues);
        return result != -1;
    }

    public void updateLibrary(Long id, String name, String address) {
        SQLiteDatabase MyDB = this.getWritableDatabase();
//        ContentValues contentValues = new ContentValues();
//        contentValues.put(LIBRARY_NAME,name);
//        contentValues.put(LIBRARY_ADDRESS,address);
        String query = "update " + LIBRARY_TABLE + " set " + LIBRARY_NAME + " = '" + name +
                "', " + LIBRARY_ADDRESS + " = '" + address + "' where " + LIBRARY_ID +
                " = " + id;
        Log.i("query: ", query);
        MyDB.execSQL(query);
//        return result != -1;
    }

    public void deleteLibrary(Long id) {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        String query = " delete from " + LIBRARY_TABLE + " where " + LIBRARY_ID + " = " + id;
        MyDB.execSQL(query);
//        long result = MyDB.delete(LIBRARY_TABLE,"? = ?", new String[]{LIBRARY_ID,String.valueOf(id)});
//        return result != -1;
    }

    public Cursor getBook() {
        SQLiteDatabase MyDB = this.getReadableDatabase();
        Cursor cursor = MyDB.query(BOOKS_TABLE, new String[]{BOOK_ID + " as _id", BOOK_NAME,
                BOOK_YEAR, BOOK_PUBLISHER}, null, null, null, null, null);
        return cursor;
    }

    public Cursor getBookByID(long id) {
        SQLiteDatabase MyDB = this.getReadableDatabase();
        String query = "select " + BOOK_ID + " as _id, " + BOOK_NAME + ", " +
                BOOK_YEAR + ", " + BOOK_PUBLISHER + " from " + BOOKS_TABLE + " where " +
                BOOK_ID + " = " + id;
        Cursor cursor = MyDB.rawQuery(query, null);
        return cursor;
    }

    public Boolean insertBook(String name, String year, String publisher) {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(BOOK_NAME, name);
        contentValues.put(BOOK_YEAR, year);
        contentValues.put(BOOK_PUBLISHER, publisher);
        long result = MyDB.insert(BOOKS_TABLE, null, contentValues);
        return result != -1;
    }

    public void updateBook(Long id, String name, String year, String publisher) {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        String query = "update " + BOOKS_TABLE + " set " + BOOK_NAME + " = '" + name + "', " +
                BOOK_YEAR + " = '" + year + "', " + BOOK_PUBLISHER + " = '" + publisher +
                "' where " + BOOK_ID + " = " + id;
        Log.i("query: ", query);
        MyDB.execSQL(query);
    }

    public void deleteBook(Long id) {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        String query = " delete from " + BOOKS_TABLE + " where " + BOOK_ID + " = " + id;
        MyDB.execSQL(query);
    }

    public Cursor getAuthor() {
        SQLiteDatabase MyDB = this.getReadableDatabase();
        Cursor cursor = MyDB.query(AUTHOR_TABLE, new String[]{AUTHOR_ID + " as _id", AUTHOR_FIRSTNAME,
                        AUTHOR_LASTNAME, AUTHOR_COUNTRY}, null, null, null,
                null, null);
        return cursor;
    }

    public Cursor getAuthorByID(long id) {
        SQLiteDatabase MyDB = this.getReadableDatabase();
        String query = "select " + AUTHOR_ID + " as _id, " + AUTHOR_LASTNAME + ", " +
                AUTHOR_FIRSTNAME + ", " + AUTHOR_COUNTRY + " from " + AUTHOR_TABLE + " where " +
                AUTHOR_ID + " = " + id;
        Log.i("author_query",query);
        Cursor cursor = MyDB.rawQuery(query, null);
        return cursor;
    }

    public Boolean insertAuthor(String firstname, String lastname, String country){
        SQLiteDatabase MyDB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(AUTHOR_FIRSTNAME, firstname);
        contentValues.put(AUTHOR_LASTNAME, lastname);
        contentValues.put(AUTHOR_COUNTRY,country);
        long result = MyDB.insert(AUTHOR_TABLE, null, contentValues);
        return result != -1;
    }

    public void updateAuthor(Long id, String lastname, String firstname, String country){
        SQLiteDatabase MyDB = this.getWritableDatabase();
        String query = "update " + AUTHOR_TABLE + " set " + AUTHOR_LASTNAME + " = '" + lastname +
                "', " + AUTHOR_FIRSTNAME + " = '" + firstname + "', " + AUTHOR_COUNTRY + " = '" +
                country + "' where " + AUTHOR_ID + " = " + id;
        Log.i("query: ",query);
        MyDB.execSQL(query);
    }

    public void deleteAuthor(Long id){
        SQLiteDatabase MyDB = this.getWritableDatabase();
        String query = " delete from " + AUTHOR_TABLE + " where " + AUTHOR_ID + " = " + id;
        MyDB.execSQL(query);
    }

    public Boolean insertUser(String username, String password, String permissions){
        SQLiteDatabase MyDB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(USER_USERNAME, username);
        contentValues.put(USER_PASSWORD, password);
        contentValues.put(USER_PERMISSION,permissions);
        long result = MyDB.insert(BOOKS_TABLE, null, contentValues);
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
