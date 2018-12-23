package com.tymoorejamal.mytimes;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


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
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(
                "CREATE TABLE " + TableName + " ( " +
                ColumnID + " INTEGER PRIMARY KEY, " +
                ColumnLat + " REAL, " +
                ColumnLon + " REAL," +
                ColumnRating + " INTEGER," +
                ColumnTitle + " TEXT," +
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

    public int getRowCount(){
        int i = 0;
        String[] columns = {DatabaseHandler.ColumnID, DatabaseHandler.ColumnLat, DatabaseHandler.ColumnLon};
        Cursor cursor = database.query(DatabaseHandler.TableName,columns,null,null,null,null,null);
        StringBuffer buffer= new StringBuffer();
        while (cursor.moveToNext()) {
            i++;
//            int cid =cursor.getInt(cursor.getColumnIndex(DatabaseHandler.ColumnID));
//            String lat = cursor.getString(cursor.getColumnIndex(DatabaseHandler.ColumnLat));
//            String lon =cursor.getString(cursor.getColumnIndex(DatabaseHandler.ColumnLon));
//            buffer.append(cid+ "   " + lat + "   " + lon +" \n");
        }
//        return buffer.toString();
        return i;
    }
}
