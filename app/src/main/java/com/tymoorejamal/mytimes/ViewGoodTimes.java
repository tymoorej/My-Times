package com.tymoorejamal.mytimes;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ViewGoodTimes extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_good_times);
        displayLocation();

        Button back = findViewById(R.id.b_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ViewGoodTimes.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    private void displayLocation(){
        if(((GlobalVariables) this.getApplication()).getCanUseLocation()){
            double latitude = ((GlobalVariables) this.getApplication()).getLatitude();
            double longitude = ((GlobalVariables) this.getApplication()).getLongitude();

            TextView latText = findViewById(R.id.lat);
            latText.setText(Double.toString(latitude));

            TextView lonText = findViewById(R.id.lon);
            lonText.setText(Double.toString(longitude));
        }
        else{
            AlertDialog alertDialog = new AlertDialog.Builder(this).create();
            alertDialog.setTitle("Location Missing");
            alertDialog.setMessage("Please enable your location and restart My Times.");
            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            Intent intent = new Intent(ViewGoodTimes.this, MainActivity.class);
                            startActivity(intent);
                        }
                    });
            alertDialog.show();
        }
    }


}
