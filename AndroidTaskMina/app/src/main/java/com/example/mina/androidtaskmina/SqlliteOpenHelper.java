package com.example.mina.androidtaskmina;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

public class SqlliteOpenHelper extends SQLiteOpenHelper {
    private static final String Database_Name = "pagesDataBase";
    private static final int Database_Version =1;

    public SqlliteOpenHelper(Context context) {
        super(context, Database_Name, null, Database_Version);

    }
    @Override
    public void onOpen(SQLiteDatabase db){
        super.onOpen(db);
        if (!db.isReadOnly()) {
            // Enable foreign key constraints
            db.execSQL("PRAGMA foreign_keys=ON;");
        }
    }
    @Override
    public void onCreate(SQLiteDatabase db){

        // create contacts table
        db.execSQL("CREATE TABLE users ("+
                "id TEXT PRIMARY KEY, " +
                "name TEXT)");
        db.execSQL("CREATE TABLE ReadPages  ( " +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "fromm INTEGER, " +
                "too INTEGER ," + "user_id TEXT," + "FOREIGN KEY(user_id) references users(id))");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        // Drop older contacts table if existed
        db.execSQL("DROP TABLE IF EXISTS users");
        db.execSQL("DROP TABLE IF EXISTS ReadPages");

        // create fresh contacts table
        this.onCreate(db);
    }


    // Books table name
    private static final String TABLE_USERS = "users";
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String[] COLUMNS = {KEY_ID, KEY_NAME};

    // add  (insert)
    public void adduser(userObject user) {

        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();

        // 2. create ContentValues to add key "column"/value
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, user.getName()); // get name
        values.put(KEY_ID, user.getId()); // get number

        // 3. insert
        db.insert(TABLE_USERS, // table
                null,
                values); // key/value -> keys = column names/ values = column values

        // 4. close
        db.close();
    }

    public ArrayList<userObject> getAllusers() {
        ArrayList<userObject> users = new ArrayList<>();

        // 1. build the query
        String query = "SELECT  * FROM " + TABLE_USERS;

        // 2. get reference to Readable DB
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query,null);

        // 3. go over each row, build  and add it to list
        userObject object = null;
        if (cursor.moveToFirst()) {    // 2 func a) check if cursor empty or not then move it to the first row
            do {
                object = new userObject();
                object.setId(cursor.getString(0));
                object.setName(cursor.getString(1));


                // Add contact to contacts
                users.add(object);
            } while (cursor.moveToNext());
        }

        // Log.d("getAllContacts()", contacts.toString());
        db.close();
        // return contacts
        return users;
    }
    //////////////////////////pages/////////////////////////////
    private static final String TABLE_ReadPages= "ReadPages";
    private static final String user_id = "user_id";
    private static final String fromm = "fromm";
    private static final String too = "too";
    private static final String[] COLUMNS2 = {user_id, fromm,too};

    public void adduserPages(pagesObject pObject) {

        Log.d("jessuuss",pObject.getuID());

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(user_id, pObject.getuID());
        values.put(fromm, pObject.getFrom());
        values.put(too, pObject.getTo());

        db.insert(TABLE_ReadPages,
                null,
                values);

        db.close();
    }

   public ArrayList<pagesObject> getAllpages(){
        ArrayList<pagesObject> pageArr = new ArrayList<>();

        // 1. build the query
        String query = "SELECT  * FROM " + TABLE_ReadPages;

        // 2. get reference to Readable DB
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query,null);

        pagesObject object = null;
        if (cursor.moveToFirst()) {
            do {
                object = new pagesObject();
                object.setFrom(Integer.parseInt(cursor.getString(1)));
                object.setTo(Integer.parseInt(cursor.getString(2)));
                object.setuID(cursor.getString(3));

                pageArr.add(object);
            } while (cursor.moveToNext());
        }

        db.close();

        return pageArr;
    }
    public  void delete(){

        SQLiteDatabase database=this.getWritableDatabase();
        database.execSQL("delete from "+ TABLE_ReadPages);

    }
}