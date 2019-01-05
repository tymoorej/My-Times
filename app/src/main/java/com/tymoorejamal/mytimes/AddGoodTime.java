package com.tymoorejamal.mytimes;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TimePicker;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;


public class AddGoodTime extends AppCompatActivity {

    double userLat;
    double userLon;
    Uri imageUri;

    static final int REQUEST_IMAGE_CAPTURE = 1;
    static final int REQUEST_LOAD_IMG = 2;
    ArrayList<byte[]> images = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("testwidget","In onCreate" );
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_good_time);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        checkPermissions();
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
                Log.d("InsertingData", "Row Count: " + Integer.toString(databaseHandler.TimesGetRowCount()));
                Log.d("InsertingData", "Last Inserted Row: " + Integer.toString(databaseHandler.TimesGetLastInsertedRow()));
                ArrayList<GoodTime> rows = databaseHandler.TimesGetRows();
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

            private void insertTime(DatabaseHandler databaseHandler, EditText title, EditText description, RatingBar rating, String stime, String etime){
                databaseHandler.TimesInsertRow(userLat, userLon, title.getText().toString(),
                        description.getText().toString(), (int) rating.getRating(),
                        stime, etime);
            }

            private void insertImages(DatabaseHandler databaseHandler, int tid){
                for (int i=0; i<images.size(); i++){
                    databaseHandler.ImagesInsertRow(tid,AddGoodTime.byteToString(images.get(i)));
                }
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
                insertTime(databaseHandler, title, description, rating, stime.getText().toString(), etime.getText().toString());
                insertImages(databaseHandler, databaseHandler.TimesGetLastInsertedRow());
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

                ContentValues values = new ContentValues();
                values.put(MediaStore.Images.Media.TITLE, "New Picture");
                values.put(MediaStore.Images.Media.DESCRIPTION, "From your Camera");
                imageUri = getContentResolver().insert(
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);

            }
        });

        Button choosePictureButton = findViewById(R.id.chosepicture);

        choosePictureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                photoPickerIntent.setType("image/*");
                startActivityForResult(photoPickerIntent, REQUEST_LOAD_IMG);
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            try {
                Bitmap imageBitmap = MediaStore.Images.Media.getBitmap(
                        getContentResolver(), imageUri);

                ByteArrayOutputStream stream = new ByteArrayOutputStream();

                Matrix matrix = new Matrix();
                matrix.postRotate(90);
                Bitmap scaledBitmap = Bitmap.createScaledBitmap(imageBitmap, imageBitmap.getWidth(), imageBitmap.getHeight(), true);
                Bitmap rotatedBitmap = Bitmap.createBitmap(scaledBitmap, 0, 0, scaledBitmap.getWidth(), scaledBitmap.getHeight(), matrix, true);


                rotatedBitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                byte imageInByte[] = stream.toByteArray();
                images.add(imageInByte);
                initRecyclerView();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        else if (requestCode == REQUEST_LOAD_IMG && resultCode == RESULT_OK){
            Uri imageUri = data.getData();
            InputStream imageStream = null;
            try {
                imageStream = getContentResolver().openInputStream(imageUri);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                Toast toast = Toast.makeText(AddGoodTime.this, "Something went wrong!", Toast.LENGTH_SHORT);
                toast.show();
                return;
            }
            Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);

            Matrix matrix = new Matrix();
            matrix.postRotate(90);
            Bitmap scaledBitmap = Bitmap.createScaledBitmap(selectedImage, selectedImage.getWidth(), selectedImage.getHeight(), true);
            Bitmap rotatedBitmap = Bitmap.createBitmap(scaledBitmap, 0, 0, scaledBitmap.getWidth(), scaledBitmap.getHeight(), matrix, true);

            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            rotatedBitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);

            byte imageInByte[] = stream.toByteArray();

            images.add(imageInByte);
            initRecyclerView();
        }
    }



    private void checkPermissions(){
        if(((GlobalVariables) this.getApplication()).getCanUseLocation() && ((GlobalVariables) this.getApplication()).getCanUseExternalStorage()) {
            return;
        }
        else{
            android.app.AlertDialog alertDialog = new android.app.AlertDialog.Builder(this).create();
            alertDialog.setTitle("Permissions Missing");
            alertDialog.setMessage("Please enable all permissions and restart My Times.");
            alertDialog.setButton(android.app.AlertDialog.BUTTON_NEUTRAL, "OK",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            Intent intent = new Intent(AddGoodTime.this, MainActivity.class);
                            startActivity(intent);
                        }
                    });
            alertDialog.show();
            alertDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialogInterface) {
                    Intent intent = new Intent(AddGoodTime.this, MainActivity.class);
                    startActivity(intent);
                }
            });
        }
    }

    private void getUserLocation(){
        this.userLat = ((GlobalVariables) this.getApplication()).getLatitude();
        this.userLon = ((GlobalVariables) this.getApplication()).getLongitude();
    }

    private void initRecyclerView(){
        RecyclerView recyclerView = findViewById(R.id.image_recyclerview);
        ImageRecyclerAdapter adapter = new ImageRecyclerAdapter(this,images);
        recyclerView.setAdapter(adapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);
    }

    private static String byteToString(byte[] b){
        return Base64.encodeToString(b, Base64.NO_WRAP);
    }

}
