package hakanyildiz.co.assingment3.MyClasses;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.ArrayAdapter;

import java.util.ArrayList;

/**
 * Created by hakan on 23.03.2016.
 */
public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String TAG = "HAKKE";
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "Assignment2";
    private static final String TABLE_USER = "user";
    private static final String TABLE_DICTIONARY = "dictionary";

    private static final String CREATE_TABLE_USER = "create table user (id INTEGER PRIMARY KEY, email TEXT , password TEXT)";
    private static final String CREATE_TABLE_DICTIONARY ="create table dictionary (id INTEGER PRIMARY KEY, turkishWord TEXT, englishWord TEXT)";
    SQLiteDatabase db;
    Cursor cursor;
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_USER);
        db.execSQL(CREATE_TABLE_DICTIONARY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_DICTIONARY);
    }

    // DB METHODS

    //USER PART
    public boolean insertUser(User user)
    {
        Log.d(TAG ,"DatabaseHelper => insertUser");
        boolean result = false;
        db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("email" , user.getEmail());
        values.put("password" , user.getPassword());

        long user_id = db.insert(TABLE_USER,null,values);

        if(user_id > 0)
        {
            result = true;
        }

        db.close();
        return result;
    }

    public boolean authentication(String email, String password)
    {        Log.d(TAG ,"DatabaseHelper => authentication");

        db = this.getReadableDatabase();
        boolean result = false;

        String query = "select * from user where email = " + email + " and password = " + password;

        //Cursor cursor = db.rawQuery(query,null);
        cursor = db.rawQuery("select * from user where email = ? and password = ?" , new String[]{email,password});

        Log.d(TAG, " cursor : " + cursor.toString());
        cursor.moveToFirst();

        if(cursor.getCount() == 1)
        {
            result = true;
        }

            cursor.close();
            db.close();
        return result;
    }

    //DICTIONARY PART

    public boolean insertDictionary(Dictionary dictionary){
        Log.d(TAG ,"DatabaseHelper => insertDictionary");

        boolean result = false;

        db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("turkishWord" , dictionary.getTurkishWord());
        values.put("englishWord", dictionary.getEnglishWord());

        long id = db.insert(TABLE_DICTIONARY,null,values);
        if(id > 0)
        {
            result = true;
        }

        db.close();
        return result;
    }

    public boolean updateDictionary(String turkishWord, String englishWord, int id)
    {
        boolean valid = false;
        db = this.getWritableDatabase();

        //updating datas
        ContentValues values = new ContentValues();
        values.put("turkishWord", turkishWord );
        values.put("englishWord" , englishWord);

        //db.update(table_name, ContentValues values , String whereClause , String[] whereArgs)
        String whereClause = "id = ?";
        String[] whereArgs = new String[]{String.valueOf(id)};

        int result = db.update(TABLE_DICTIONARY,values,whereClause,whereArgs);

        if(result > 0)
        {
            valid  = true;
        }

        return valid;
    }

    public boolean deleteDictionary(int id)
    {
        boolean valid =false;
        db = this.getWritableDatabase();

        String whereClause = "id = ?";
        String[] whereArgs = new String[]{String.valueOf(id)};
        //delete(String table, String whereClause, String[] whereArgs)
        int result = db.delete(TABLE_DICTIONARY,whereClause,whereArgs);

        if(result > 0 )
        {
            valid = true;
        }

        return valid;
    }


    public ArrayAdapter getAllDictionaries(Context context)
    {
        Log.d(TAG ,"DatabaseHelper => getAllDictionaries");

        String[] columns = new String[]{"id",   "turkishWord", "englishWord"};

        String query = "SELECT * FROM " + TABLE_DICTIONARY;
        db = this.getReadableDatabase();

        cursor = db.rawQuery(query,null);

        String arr[] = new String[cursor.getCount()];

        int counter=0;
        for(cursor.moveToFirst();!cursor.isAfterLast();cursor.moveToNext())
        {
            arr[counter] = cursor.getString(cursor.getColumnIndex("id")) + "   " +
                    cursor.getString(cursor.getColumnIndex("turkishWord")) + "   " +
                    cursor.getString(cursor.getColumnIndex("englishWord"));
            counter++;
        }

        ArrayAdapter AA = new ArrayAdapter(context,android.R.layout.simple_list_item_1,arr);

        return AA;
    }


    public String findWord(String aranan)
    {
        Log.d(TAG ,"DatabaseHelper => findWord");

        String resultWordType = "";
        String result = "";
        db = this.getReadableDatabase();
        String query = "select * from " + TABLE_DICTIONARY;

        String[] type = aranan.split("-");

        //type[0] bize aranan kelimenin hangi dilden oldugunu söyler.
        Log.d(TAG, "findWord => type[0]: " + type[0]);

        if(type[0].equals("tr"))
        {
            query += " where turkishWord = ?";
            resultWordType = "englishWord";
        }
        else //type[0] == en
        {
            query += " where englishWord = ?";
            resultWordType = "turkishWord";
        }
        cursor = db.rawQuery(query, new String[]{type[1]});

        Log.d(TAG, "findWord => query " + query);
        Log.d(TAG, "findWord => type[1] " + type[1]);
        Log.d(TAG, "findWord => cursorGetCount: " + cursor.getCount());

        if(cursor.getCount() != 0)
        {
            cursor.moveToFirst();

            result = cursor.getString(cursor.getColumnIndex(resultWordType));
        }

        return result;
    }





}
