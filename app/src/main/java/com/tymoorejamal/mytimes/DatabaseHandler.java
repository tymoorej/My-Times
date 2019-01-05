package com.tymoorejamal.mytimes;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Base64;
import android.util.Log;

import java.util.ArrayList;


public class DatabaseHandler extends SQLiteOpenHelper {

    // database constants
    private static final int DatabaseVersion = 1;
    private static final String DatabaseName = "mytimes.db";

    // Times Name
    private static final String TimesTableName = "Times";

    // Times columns
    private static final String TimesColumnID = "TID";
    private static final String TimesColumnLat = "LATITUDE";
    private static final String TimesColumnLon = "LONGITUDE";
    private static final String TimesColumnRating = "RATING";
    private static final String TimesColumnTitle = "TITLE";
    private static final String TimesColumnDescription = "DESCRIPTION";
    private static final String TimesColumnStartTime = "STARTTIME";
    private static final String TimesColumnEndTime = "ENDTIME";

    // Images Name
    private static final String ImagesTableName = "Images";

    // Images columns
    private static final String ImagesTimesColumnID = "TID";
    private static final String ImagesColumnID = "IID";
    private static final String ImagesImage = "Image";

    SQLiteDatabase database;

    public DatabaseHandler(Context context) {
        super(context, DatabaseName, null, DatabaseVersion);
        database = getWritableDatabase();
        onCreate(database);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(
                "CREATE TABLE IF NOT EXISTS " + TimesTableName + " ( " +
                        TimesColumnID + " INTEGER PRIMARY KEY, " +
                        TimesColumnLat + " REAL, " +
                        TimesColumnLon + " REAL," +
                        TimesColumnRating + " INTEGER," +
                        TimesColumnTitle + " TEXT NOT NULL," +
                        TimesColumnDescription + " TEXT," +
                        TimesColumnStartTime + " TEXT," +
                        TimesColumnEndTime + " TEXT" +
                ")"
        );
        sqLiteDatabase.execSQL(
                "CREATE TABLE IF NOT EXISTS " + ImagesTableName + " ( " +
                        ImagesTimesColumnID + " INTEGER, " +
                        ImagesColumnID + " INTEGER UNIQUE NOT NULL, " +
                        ImagesImage + " BLOB," +
                        "PRIMARY KEY(" + ImagesTimesColumnID + ","  + ImagesColumnID + "), " +
                        "FOREIGN KEY(" + ImagesTimesColumnID + ") REFERENCES " + TimesTableName + "(" + TimesColumnID + ")" + " ON DELETE CASCADE" +
                        ")"
        );

        sqLiteDatabase.execSQL("PRAGMA foreign_keys=on;");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TimesTableName);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + ImagesTableName);
        onCreate(sqLiteDatabase);
    }

    public void TimesInsertRow(double lat, double lon, String title, String desc, int rating, String stime, String etime){
        database.execSQL(
                "INSERT INTO " + TimesTableName + " (" +
                        TimesColumnLat + ", " +
                        TimesColumnLon + ", " +
                        TimesColumnRating + ", " +
                        TimesColumnTitle + ", " +
                        TimesColumnDescription + ", " +
                        TimesColumnStartTime + ", " +
                        TimesColumnEndTime +
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

    public void ImagesInsertRow(int tid, String ImageData){
        database.execSQL(
                "INSERT INTO " + ImagesTableName + " (" +
                        ImagesTimesColumnID + ", " +
                        ImagesColumnID + ", " +
                        ImagesImage +
                        ")" +
                        " VALUES (" +
                        Integer.toString(tid) + ", " +
                        Integer.toString(ImagesGetLastInsertedRow() + 1) + ", " +
                        "'" + "test" + "'" +
                        " )");
    }

    public void TimesClearTable(){
        database.execSQL("DELETE FROM " + TimesTableName);
    }
    public void TimesDropTable(){
        database.execSQL("DROP TABLE IF EXISTS " + TimesTableName);
    }

    public void ImagesClearTable(){
        database.execSQL("DELETE FROM " + ImagesTableName);
    }
    public void ImagesDropTable(){
        database.execSQL("DROP TABLE IF EXISTS " + ImagesTableName);
    }


    public int TimesGetRowCount(){
        int i = 0;
        String[] columns = {DatabaseHandler.TimesColumnID};
        Cursor cursor = database.query(DatabaseHandler.TimesTableName,columns,null,null,null,null,null);
        return cursor.getCount();
    }

    public int ImagesGetRowCount(){
        int i = 0;
        String[] columns = {DatabaseHandler.ImagesColumnID};
        Cursor cursor = database.query(DatabaseHandler.ImagesTableName,columns,null,null,null,null,null);
        return cursor.getCount();
    }

    public int TimesGetLastInsertedRow(){
        String[] columns = {DatabaseHandler.TimesColumnID};
        Cursor cursor = database.query(DatabaseHandler.TimesTableName,columns,null,
                null,null,null,DatabaseHandler.TimesColumnID + " DESC");
        if (cursor.moveToNext()) {
            return cursor.getInt(cursor.getColumnIndex(DatabaseHandler.TimesColumnID));
        }
        else{
            return -1;
        }
    }

    public int ImagesGetLastInsertedRow(){
        String[] columns = {DatabaseHandler.ImagesTimesColumnID, DatabaseHandler.ImagesColumnID};
        Cursor cursor = database.query(DatabaseHandler.ImagesTableName,columns, null,
                null,null,null,DatabaseHandler.ImagesColumnID + " DESC");
        if (cursor.moveToNext()) {
            return cursor.getInt(cursor.getColumnIndex(DatabaseHandler.ImagesColumnID));
        }
        else{
            return -1;
        }
    }

    public void TimesRemoveRow(int tid){
        database.execSQL("DELETE FROM " + TimesTableName + " WHERE " + TimesColumnID + " IS " + Integer.toString(tid));
    }

    public ArrayList<GoodTime> TimesGetRows(){
        String[] columns = {DatabaseHandler.TimesColumnID, DatabaseHandler.TimesColumnTitle,
                DatabaseHandler.TimesColumnDescription, DatabaseHandler.TimesColumnRating,
                DatabaseHandler.TimesColumnLat, DatabaseHandler.TimesColumnLon,
                DatabaseHandler.TimesColumnStartTime, DatabaseHandler.TimesColumnEndTime};
        Cursor cursor = database.query(DatabaseHandler.TimesTableName,columns,null,null,null,null,null);
        ArrayList<GoodTime> goodTimes= new ArrayList<>();
        while (cursor.moveToNext()) {
            int tid = cursor.getInt(cursor.getColumnIndex(DatabaseHandler.TimesColumnID));
            String title = cursor.getString(cursor.getColumnIndex(DatabaseHandler.TimesColumnTitle));
            String description = cursor.getString(cursor.getColumnIndex(DatabaseHandler.TimesColumnDescription));
            int rating = cursor.getInt(cursor.getColumnIndex(DatabaseHandler.TimesColumnRating));
            double lat = cursor.getDouble(cursor.getColumnIndex(DatabaseHandler.TimesColumnLat));
            double lon = cursor.getDouble(cursor.getColumnIndex(DatabaseHandler.TimesColumnLon));
            String stime = cursor.getString(cursor.getColumnIndex(DatabaseHandler.TimesColumnStartTime));
            String etime = cursor.getString(cursor.getColumnIndex(DatabaseHandler.TimesColumnEndTime));
            GoodTime goodTime = new GoodTime(tid, title, description, rating, lat, lon, stime, etime, ImagesGetImages(tid));
            goodTimes.add(goodTime);
        }
        return goodTimes;
    }

    public ArrayList<byte[]> ImagesGetImages(int tid){
        String[] columns = {DatabaseHandler.ImagesTimesColumnID, DatabaseHandler.ImagesImage};
        Cursor cursor = database.query(DatabaseHandler.ImagesTableName, columns,DatabaseHandler.ImagesTimesColumnID + " IS " + tid,null,null,null,null);
        ArrayList<byte[]> images= new ArrayList<>();
        while (cursor.moveToNext()) {
            String imageData = cursor.getString(cursor.getColumnIndex(DatabaseHandler.ImagesImage));
            images.add(stringToByte(imageData));
        }
        return images;
    }

    private byte[] stringToByte(String s){
        return Base64.decode(s, Base64.NO_WRAP);
    }

}
