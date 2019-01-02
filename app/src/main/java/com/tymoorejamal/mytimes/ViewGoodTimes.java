package com.tymoorejamal.mytimes;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class ViewGoodTimes extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_good_times);
        checkLocationPermission();
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        Button back = findViewById(R.id.b_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ViewGoodTimes.this, MainActivity.class);
                startActivity(intent);
            }
        });

        initRecyclerView();
    }

    private void checkLocationPermission(){
        if(!((GlobalVariables) this.getApplication()).getCanUseLocation()){
            AlertDialog alertDialog = new AlertDialog.Builder(this).create();
            alertDialog.setTitle("Location Missing");
            alertDialog.setMessage("Please enable location permissions and restart My Times.");
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

    private void initRecyclerView(){
        RecyclerView recyclerView = findViewById(R.id.view_recylerview);

        DatabaseHandler databaseHandler = new DatabaseHandler(ViewGoodTimes.this);
        ArrayList<GoodTime> goodTimes = databaseHandler.getRows();

        ViewGoodTimeRecyclerViewAdapter adapter = new ViewGoodTimeRecyclerViewAdapter(this,goodTimes);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }


}
