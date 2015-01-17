package net.micode.fileexplorer;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by chenkezheng on 9/30/14.
 */
public class PrivateDatabaseHelper extends SQLiteOpenHelper{
    private PrivateDatabaseHelper(Context context){
        super(context, "shilyData", null, 1);
    }

    private static PrivateDatabaseHelper instance = null;

    public static PrivateDatabaseHelper getInstance(Context context){Log.d("shanlihou", "../../mifile//src/net/micode/fileexplorer/PrivateDatabaseHelper.java: getInstance");
        if(instance == null){
            return instance = new PrivateDatabaseHelper(context);
        }
        else{
            return instance;
        }
    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {Log.d("shanlihou", "../../mifile//src/net/micode/fileexplorer/PrivateDatabaseHelper.java: onCreate");Log.d("shanlihou", "dbCreate");
        String sql = "Create table " + "shilyData" + "(" + "ID" + " integer primary key autoincrement,"
                + "name" + " text, " + "path" + " text );";
        sqLiteDatabase.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i2) {Log.d("shanlihou", "../../mifile//src/net/micode/fileexplorer/PrivateDatabaseHelper.java: onUpgrade");
        String sql = " DROP TABLE IF EXISTS " + "shilyData";
        sqLiteDatabase.execSQL(sql);
        onCreate(sqLiteDatabase);
    }

    public Cursor query() {Log.d("shanlihou", "../../mifile//src/net/micode/fileexplorer/PrivateDatabaseHelper.java: query");
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query("shilyData", null, null, null, null, null, null);
        return cursor;
    }

    public long insert(String title, String location) {Log.d("shanlihou", "../../mifile//src/net/micode/fileexplorer/PrivateDatabaseHelper.java: insert");
        Log.d("shanlihou", "title:" + title + " location:" + location);
        SQLiteDatabase db = this.getWritableDatabase();
        long ret = db.insert("shilyData", null, createValues(title, location));
        return ret;
    }

    public void delete(long id) {Log.d("shanlihou", "../../mifile//src/net/micode/fileexplorer/PrivateDatabaseHelper.java: delete");
        SQLiteDatabase db = this.getWritableDatabase();
        String where = "ID" + "=?";
        String[] whereValue = {
                Long.toString(id)
        };
        db.delete("shilyData", where, whereValue);
    }

    public void delete(String location) {Log.d("shanlihou", "../../mifile//src/net/micode/fileexplorer/PrivateDatabaseHelper.java: delete");
        SQLiteDatabase db = this.getWritableDatabase();
        String where = "path" + "=?";
        String[] whereValue = {
                location
        };
        db.delete("shilyData", where, whereValue);
    }

    private ContentValues createValues(String title, String location) {Log.d("shanlihou", "../../mifile//src/net/micode/fileexplorer/PrivateDatabaseHelper.java: createValues");
        ContentValues cv = new ContentValues();
        cv.put("name", title);
        cv.put("path", location);
        return cv;
    }
}
