package tk.ubublik.redivansion.gamelogic.units;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

import tk.ubublik.redivansion.MainActivity;

/**
 * Created by Bublik on 31-Aug-17.
 */

public class Settings extends SQLiteOpenHelper {

    private static Settings instance;

    public Settings(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public static Settings getInstance() {
        if (instance==null){
            instance = new Settings(MainActivity.context, DB_NAME, null, DB_VERSION);
        }
        return instance;
    }

    private SavedLevel savedLevel;
    private boolean music;
    private boolean fx;

    private boolean firstLaunch = false;

    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "Settings.db";

    private static class FeedEntry implements BaseColumns {
        static final String TABLE_NAME = "settings";
        static final String COLUMN_NAME_LEVEL = "saved_level";
        static final String COLUMN_NAME_MUSIC = "music";
        static final String COLUMN_NAME_FX = "fx";
    }

    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + FeedEntry.TABLE_NAME + " (" +
                    FeedEntry._ID + " INTEGER PRIMARY KEY," +
                    FeedEntry.COLUMN_NAME_LEVEL + " BLOB," +
                    FeedEntry.COLUMN_NAME_MUSIC + " NUMERIC NOT NULL," +
                    FeedEntry.COLUMN_NAME_FX + " NUMERIC NOT NULL)";

    private static final String SQL_INSERT_DEFAULT_VALUE =
            "INSERT INTO "+FeedEntry.TABLE_NAME+" (" +
                    FeedEntry.COLUMN_NAME_LEVEL + ", "+
                    FeedEntry.COLUMN_NAME_MUSIC + ", "+
                    FeedEntry.COLUMN_NAME_FX + ")"+
                    "VALUES (" +
                    "NULL, "+
                    "1, "+
                    "1"+
                    ");";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + FeedEntry.TABLE_NAME;

    public void save(){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(FeedEntry.COLUMN_NAME_FX, fx);
        cv.put(FeedEntry.COLUMN_NAME_MUSIC, music);
        cv.put(FeedEntry.COLUMN_NAME_LEVEL, savedLevel.getBytes());
        db.update(FeedEntry.TABLE_NAME, cv, "1", null);
        db.close();
    }

    public void open(){
        SQLiteDatabase db = this.getReadableDatabase();
        String[] projection = {
                FeedEntry.COLUMN_NAME_LEVEL,
                FeedEntry.COLUMN_NAME_MUSIC,
                FeedEntry.COLUMN_NAME_FX
        };
        Cursor cursor = db.query(
                FeedEntry.TABLE_NAME,                     // The table to query
                projection,                               // The columns to return
                null,                                // The columns for the WHERE clause
                null,                            // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                null                                 // The sort order
        );
        if (cursor.getCount()!=1) throw new RuntimeException("Can't read settings from DB");
        cursor.moveToFirst();
        this.savedLevel = new SavedLevel(cursor.getBlob(0));
        this.music = cursor.getInt(1)==1;
        this.fx = cursor.getInt(2)==1;
        db.close();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
        db.execSQL(SQL_INSERT_DEFAULT_VALUE);
        setFirstLaunch(true);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }

    public boolean isFirstLaunch() {
        return firstLaunch;
    }

    public void setFirstLaunch(boolean firstLaunch) {
        this.firstLaunch = firstLaunch;
    }

    public SavedLevel getSavedLevel() {
        return savedLevel;
    }

    public void setSavedLevel(SavedLevel savedLevel) {
        this.savedLevel = savedLevel;
    }

    public boolean isMusic() {
        return music;
    }

    public void setMusic(boolean music) {
        this.music = music;
    }

    public boolean isFx() {
        return fx;
    }

    public void setFx(boolean fx) {
        this.fx = fx;
    }


}
