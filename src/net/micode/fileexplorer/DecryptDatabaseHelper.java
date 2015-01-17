package net.micode.fileexplorer;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Administrator on 2015/1/17 0017.
 */
public class DecryptDatabaseHelper extends SQLiteOpenHelper {
    private final static String DATABASE_NAME = "decryptDB.db";

    private final static int DATABASE_VERSION = 1;

    private final static String TABLE_NAME = "decrypt";

    public final static String FIELD_ID = "_id";

    public final static String FIELD_TITLE = "title";

    public final static String FIELD_LOCATION = "location";
    public DecryptDatabaseHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "Create table " + TABLE_NAME + "(" + FIELD_ID + " integer primary key autoincrement,"
                + FIELD_TITLE + " text, " + FIELD_LOCATION + " text );";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql = " DROP TABLE IF EXISTS " + TABLE_NAME;
        db.execSQL(sql);
        onCreate(db);
    }

    public Cursor query() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, null, null, null, null, null, null);
        return cursor;
    }
    public long insert(String title, String location) {
        
        SQLiteDatabase db = this.getWritableDatabase();
        long ret = db.insert(TABLE_NAME, null, createValues(title, location));
        return ret;
    }

    public void delete(String location) {
        SQLiteDatabase db = this.getWritableDatabase();
        String where = FIELD_LOCATION + "=?";
        String[] whereValue = {
                location
        };
        db.delete(TABLE_NAME, where, whereValue);
    }
    private ContentValues createValues(String title, String location) {
        ContentValues cv = new ContentValues();
        cv.put(FIELD_TITLE, title);
        cv.put(FIELD_LOCATION, location);
        return cv;
    }
}
