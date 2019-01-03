package com.tymoorejamal.mytimes;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class AddGoodTime extends AppCompatActivity {

    double userLat;
    double userLon;


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
            @Override
            public void onClick(View view) {
                EditText title = findViewById(R.id.titleText);
                EditText description = findViewById(R.id.descText);
                RatingBar rating = findViewById(R.id.ratingBar);
                String titleText = title.getText().toString().replaceAll("\\s+", "");
                if (titleText == null || titleText.equals("") || titleText.length() == 0) {
                    Toast toast = Toast.makeText(AddGoodTime.this, "Please add a Title", Toast.LENGTH_SHORT);
                    toast.show();
                    return;
                }

                Date date = new Date();
                String strDateFormat = "yyyy-MM-dd hh:mm";
                DateFormat dateFormat = new SimpleDateFormat(strDateFormat);
                String formattedDate = dateFormat.format(date);

                DatabaseHandler databaseHandler = new DatabaseHandler(AddGoodTime.this);
                databaseHandler.insertRow(userLat, userLon, title.getText().toString(),
                        description.getText().toString(), (int) rating.getRating(),
                        formattedDate, formattedDate);
                Log.d("InsertingData", "Row Count: " + Integer.toString(databaseHandler.getRowCount()));
                Log.d("InsertingData", "Last Inserted Row: " + Integer.toString(databaseHandler.getLastInsertedRow()));
                String[] rows = databaseHandler.getRows();
                for (int i = 0; i < rows.length; i++) {
                    Log.d("InsertingData", rows[i]);
                }
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

}
