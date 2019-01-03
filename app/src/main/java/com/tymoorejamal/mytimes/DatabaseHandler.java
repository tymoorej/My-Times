package com.tymoorejamal.mytimes;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;


public class DatabaseHandler extends SQLiteOpenHelper {

    // database constants
    private static final int DatabaseVersion = 1;
    private static final String DatabaseName = "mytimes.db";

    private static final String TableName = "Times";

    // columns
    private static final String ColumnID = "TID";
    private static final String ColumnLat = "LATITUDE";
    private static final String ColumnLon = "LONGITUDE";
    private static final String ColumnRating = "RATING";
    private static final String ColumnTitle = "TITLE";
    private static final String ColumnDescription = "DESCRIPTION";
    private static final String ColumnStartTime = "STARTTIME";
    private static final String ColumnEndTime = "ENDTIME";

    SQLiteDatabase database;

    public DatabaseHandler(Context context) {
        super(context, DatabaseName, null, DatabaseVersion);
        database = getWritableDatabase();
        onCreate(database);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(
                "CREATE TABLE IF NOT EXISTS " + TableName + " ( " +
                ColumnID + " INTEGER PRIMARY KEY, " +
                ColumnLat + " REAL, " +
                ColumnLon + " REAL," +
                ColumnRating + " INTEGER," +
                ColumnTitle + " TEXT NOT NULL," +
                ColumnDescription + " TEXT," +
                ColumnStartTime + " TEXT," +
                ColumnEndTime + " TEXT" +
                ")"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TableName);
        onCreate(sqLiteDatabase);
    }

    public void insertRow(double lat, double lon, String title, String desc, int rating, String stime, String etime){
        database.execSQL(
                "INSERT INTO " + TableName + " (" +
                        ColumnLat + ", " +
                        ColumnLon + ", " +
                        ColumnRating + ", " +
                        ColumnTitle + ", " +
                        ColumnDescription + ", " +
                        ColumnStartTime + ", " +
                        ColumnEndTime +
                        ")" +
                " VALUES (" +
                        Double.toString(lat) + ", " +
                        Double.toString(lon) + ", " +
                        Integer.toString(rating) + ", " +
                        "'" + title + "'" + ", " +
                        "'" + desc + "'" + ", " +
                        "'" + stime + "'" + ", " +
                        "'" + etime + "'" +
                        " )");
    }

    public void clearTable(){
        database.execSQL("DELETE FROM " + TableName);
    }
    public void dropTable(){
        database.execSQL("DROP TABLE IF EXISTS " + TableName);
    }


    public int getRowCount(){
        int i = 0;
        String[] columns = {DatabaseHandler.ColumnID};
        Cursor cursor = database.query(DatabaseHandler.TableName,columns,null,null,null,null,null);
        return cursor.getCount();
    }

    public int getLastInsertedRow(){
        String[] columns = {DatabaseHandler.ColumnID};
        Cursor cursor = database.query(DatabaseHandler.TableName,columns,DatabaseHandler.ColumnID,
                null,null,null,DatabaseHandler.ColumnID + " DESC");
        StringBuffer buffer= new StringBuffer();
        if (cursor.moveToNext()) {
            return cursor.getInt(cursor.getColumnIndex(DatabaseHandler.ColumnID));
        }
        else{
            return -1;
        }
    }

    public void removeRow(int tid){
        database.execSQL("DELETE FROM " + TableName + " WHERE " + ColumnID + " IS " + Integer.toString(tid));
    }

    public ArrayList<GoodTime> getRows(){
        String[] columns = {DatabaseHandler.ColumnID, DatabaseHandler.ColumnTitle,
                DatabaseHandler.ColumnDescription, DatabaseHandler.ColumnRating,
                DatabaseHandler.ColumnLat, DatabaseHandler.ColumnLon,
                DatabaseHandler.ColumnStartTime, DatabaseHandler.ColumnEndTime};
        Cursor cursor = database.query(DatabaseHandler.TableName,columns,null,null,null,null,null);
        ArrayList<GoodTime> goodTimes= new ArrayList<>();
        while (cursor.moveToNext()) {
            int tid = cursor.getInt(cursor.getColumnIndex(DatabaseHandler.ColumnID));
            String title = cursor.getString(cursor.getColumnIndex(DatabaseHandler.ColumnTitle));
            String description = cursor.getString(cursor.getColumnIndex(DatabaseHandler.ColumnDescription));
            int rating = cursor.getInt(cursor.getColumnIndex(DatabaseHandler.ColumnRating));
            double lat = cursor.getDouble(cursor.getColumnIndex(DatabaseHandler.ColumnLat));
            double lon = cursor.getDouble(cursor.getColumnIndex(DatabaseHandler.ColumnLon));
            String stime = cursor.getString(cursor.getColumnIndex(DatabaseHandler.ColumnStartTime));
            String etime = cursor.getString(cursor.getColumnIndex(DatabaseHandler.ColumnEndTime));
            GoodTime goodTime = new GoodTime(tid, title, description, rating, lat, lon, stime, etime);
            goodTimes.add(goodTime);
        }
        return goodTimes;
    }
}
