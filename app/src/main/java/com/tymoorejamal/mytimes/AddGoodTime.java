package com.tymoorejamal.mytimes;

import android.app.DatePickerDialog;
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
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class AddGoodTime extends AppCompatActivity {

    double userLat;
    double userLon;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
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

            private String getFormattedDate(){
                Date date = new Date();
                String strDateFormat = "yyyy-MM-dd hh:mm";
                DateFormat dateFormat = new SimpleDateFormat(strDateFormat);
                String formattedDate = dateFormat.format(date);
                return formattedDate;
            }

            private void insertDataToDB(DatabaseHandler databaseHandler,EditText title,EditText description, RatingBar rating, String formattedDate){
                databaseHandler.insertRow(userLat, userLon, title.getText().toString(),
                        description.getText().toString(), (int) rating.getRating(),
                        formattedDate, formattedDate);
            }

            @Override
            public void onClick(View view) {
                EditText title = findViewById(R.id.titleText);
                EditText description = findViewById(R.id.descText);
                RatingBar rating = findViewById(R.id.ratingBar);
                String titleText = title.getText().toString().replaceAll("\\s+", "");
                if(checkValidTitle(titleText) == false){
                    return;
                }

                String formattedDate = getFormattedDate();

                DatabaseHandler databaseHandler = new DatabaseHandler(AddGoodTime.this);
                insertDataToDB(databaseHandler, title, description, rating, formattedDate);
                logInsertion(databaseHandler);
                goBack();
                displaySuccessMessage();
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
