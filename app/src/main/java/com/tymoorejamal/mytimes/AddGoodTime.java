package com.tymoorejamal.mytimes;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TimePicker;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;


public class AddGoodTime extends AppCompatActivity {

    double userLat;
    double userLon;

    static final int REQUEST_IMAGE_CAPTURE = 1;
    ArrayList<byte[]> images = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("testwidget","In onCreate" );
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_good_time);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        getUserLocation();

        Button back = findViewById(R.id.b_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddGoodTime.this, MainActivity.class);
                startActivity(intent);
            }
        });

        Button add = findViewById(R.id.b_add);
        add.setOnClickListener(new View.OnClickListener() {

            private void goBack(){
                Intent intent = new Intent(AddGoodTime.this, MainActivity.class);
                startActivity(intent);
            }

            private void displaySuccessMessage(){
                Toast toast = Toast.makeText(AddGoodTime.this, "Successfully Added!", Toast.LENGTH_SHORT);
                toast.show();
            }

            private void logInsertion(DatabaseHandler databaseHandler){
                Log.d("InsertingData", "Row Count: " + Integer.toString(databaseHandler.getRowCount()));
                Log.d("InsertingData", "Last Inserted Row: " + Integer.toString(databaseHandler.getLastInsertedRow()));
                ArrayList<GoodTime> rows = databaseHandler.getRows();
                for (int i =0; i<rows.size(); i++){
                    Log.d("InsertingData", rows.get(i).toString());
                }
            }

            private boolean checkValidTitle(String titleText){
                if (titleText == null || titleText.equals("") || titleText.length() == 0) {
                    Toast toast = Toast.makeText(AddGoodTime.this, "Please add a Title", Toast.LENGTH_SHORT);
                    toast.show();
                    return false;
                }
                else{
                    return true;
                }
            }

            private void insertDataToDB(DatabaseHandler databaseHandler,EditText title,EditText description, RatingBar rating, String stime, String etime){
                databaseHandler.insertRow(userLat, userLon, title.getText().toString(),
                        description.getText().toString(), (int) rating.getRating(),
                        stime, etime);
            }

            @Override
            public void onClick(View view) {
                EditText title = findViewById(R.id.titleText);
                EditText description = findViewById(R.id.descText);
                RatingBar rating = findViewById(R.id.ratingBar);
                Button stime = findViewById(R.id.stimeButton);
                Button etime = findViewById(R.id.etimeButton);
                String titleText = title.getText().toString().replaceAll("\\s+", "");
                if(checkValidTitle(titleText) == false){
                    return;
                }

                DatabaseHandler databaseHandler = new DatabaseHandler(AddGoodTime.this);
                insertDataToDB(databaseHandler, title, description, rating, stime.getText().toString(), etime.getText().toString());
                logInsertion(databaseHandler);
                goBack();
                displaySuccessMessage();
            }
        });


        Button sTime = findViewById(R.id.stimeButton);
        sTime.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Calendar datecalendar = Calendar.getInstance();
                int day = datecalendar.get(Calendar.DAY_OF_MONTH);
                int month = datecalendar.get(Calendar.MONTH);
                int year = datecalendar.get(Calendar.YEAR);
                DatePickerDialog datepicker = new DatePickerDialog( AddGoodTime.this, new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker datepicker, int mYear, int mMonth, int mDay){
                        Button sTime = findViewById(R.id.stimeButton);
                        sTime.setText(mYear + "-" + (mMonth + 1) + "-" + mDay);

                        Calendar datecalendar = Calendar.getInstance();
                        int hour = datecalendar.get(Calendar.HOUR_OF_DAY);
                        int minute = datecalendar.get(Calendar.MINUTE);

                        TimePickerDialog timepicker = new TimePickerDialog(AddGoodTime.this, new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker timePicker, int mhour, int mminute) {
                                Button sTime = findViewById(R.id.stimeButton);
                                sTime.setText( sTime.getText() + " " + mhour + ":" + mminute);
                            }
                        },hour, minute, false);
                        timepicker.show();
                    }

                }, year, month, day);

                datepicker.show();
            }
        });





        Button eTime = findViewById(R.id.etimeButton);
        eTime.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Calendar datecalendar = Calendar.getInstance();
                int day = datecalendar.get(Calendar.DAY_OF_MONTH);
                int month = datecalendar.get(Calendar.MONTH);
                int year = datecalendar.get(Calendar.YEAR);
                DatePickerDialog datepicker = new DatePickerDialog( AddGoodTime.this, new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker datepicker, int mYear, int mMonth, int mDay){
                        Button eTime = findViewById(R.id.etimeButton);
                        eTime.setText(mYear + "-" + (mMonth + 1) + "-" + mDay);

                        Calendar datecalendar = Calendar.getInstance();
                        int hour = datecalendar.get(Calendar.HOUR_OF_DAY);
                        int minute = datecalendar.get(Calendar.MINUTE);

                        TimePickerDialog timepicker = new TimePickerDialog(AddGoodTime.this, new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker timePicker, int mhour, int mminute) {
                                Button eTime = findViewById(R.id.etimeButton);
                                eTime.setText( eTime.getText() + " " + mhour + ":" + mminute);
                            }
                        },hour, minute, false);
                        timepicker.show();
                    }

                }, year, month, day);

                datepicker.show();
            }
        });

        Button addPictureButton = findViewById(R.id.addpicture);

        addPictureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                    startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
                }
                else{
                    Toast toast = Toast.makeText(AddGoodTime.this, "Error Adding Image", Toast.LENGTH_SHORT);
                    toast.show();
                    return;
                }

            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            imageBitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
            byte imageInByte[] = stream.toByteArray();
            images.add(imageInByte);
            initRecyclerView();
        }
    }



    private void getUserLocation(){
        if(((GlobalVariables) this.getApplication()).getCanUseLocation()){
            this.userLat = ((GlobalVariables) this.getApplication()).getLatitude();
            this.userLon = ((GlobalVariables) this.getApplication()).getLongitude();
        }
        else{
            android.app.AlertDialog alertDialog = new android.app.AlertDialog.Builder(this).create();
            alertDialog.setTitle("Location Missing");
            alertDialog.setMessage("Please enable location permissions and restart My Times.");
            alertDialog.setButton(android.app.AlertDialog.BUTTON_NEUTRAL, "OK",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            Intent intent = new Intent(AddGoodTime.this, MainActivity.class);
                            startActivity(intent);
                        }
                    });
            alertDialog.show();
        }
    }

    private void initRecyclerView(){
        RecyclerView recyclerView = findViewById(R.id.image_recyclerview);
        ImageRecyclerAdapter adapter = new ImageRecyclerAdapter(this,images);
        recyclerView.setAdapter(adapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);
    }

}
