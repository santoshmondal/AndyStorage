package com.affixus.andy.hellonov10;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

/**
 * Created by santoshm on 11/11/15.
 */
public class MySqliteHelper extends SQLiteOpenHelper {
    private Context context;

    private static final int VERSION = 5;
    private static final String DATABASE_NAME = "mysqlite";
    private static final String TABLE_NAME = "USERS";
    private static final String COL_ID = "ID";
    private static final String COL_NAME = "NAME";



    public MySqliteHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try{
            String sql = "CREATE TABLE " + TABLE_NAME
                    + " (" + COL_ID + " INTEGER PRIMARYKEY AUTOINCRMENT, "
                    + COL_NAME + " VARCHAR(255) "
                    + " )";
            db.execSQL(sql);

            Toast.makeText(context, "SUCCESS DB CREATE", Toast.LENGTH_SHORT).show();
        } catch(Exception e) {
            e.printStackTrace();
            Toast.makeText(context, "ERROR DB CREATE", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        try{
            String sql = "DROP TABLE IF EXISTS " + TABLE_NAME;

            db.execSQL(sql);
            onCreate(db);

            Toast.makeText(context, "SUCCESS DB UPGRADE ", Toast.LENGTH_SHORT).show();
        } catch(Exception e) {
            e.printStackTrace();
            Toast.makeText(context, "ERROR DB UPGRADE ", Toast.LENGTH_SHORT).show();
        }
    }

    public void insert(String name) {
        try{
            SQLiteDatabase dbref = this.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(COL_NAME, name);

            dbref.insert(TABLE_NAME, null, values);
            dbref.close();
        } catch(Exception e){
            e.printStackTrace();
        }
    }

    public void selectAll() {
        try {
            StringBuffer sb = new StringBuffer("");
            SQLiteDatabase dbref = this.getWritableDatabase();

            String[] columns = {COL_NAME};
            Cursor cursor = dbref.query(TABLE_NAME, columns, null, null, null, null, null);
            if (cursor.moveToNext()) {
                do {
                    String name = cursor.getString(cursor.getColumnIndex(COL_NAME));
                    sb.append(name).append("\n");
                } while (cursor.moveToNext());
            }
            dbref.close();

            Toast.makeText(context, sb.toString(), Toast.LENGTH_SHORT).show();
        } catch(Exception e){
            e.printStackTrace();
            Toast.makeText(context, "ERROR SELECT ALL", Toast.LENGTH_SHORT).show();
        }
    }
}
