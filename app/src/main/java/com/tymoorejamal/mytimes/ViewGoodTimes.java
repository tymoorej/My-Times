package com.tymoorejamal.mytimes;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

public class ViewGoodTimes extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_good_times);
        checkPermissions();
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

    private void checkPermissions(){
        if(!((GlobalVariables) this.getApplication()).getCanUseLocation() || !((GlobalVariables) this.getApplication()).getCanUseExternalStorage()){
            AlertDialog alertDialog = new AlertDialog.Builder(this).create();
            alertDialog.setTitle("Permissions Missing");
            alertDialog.setMessage("Please enable all permissions and restart My Times.");
            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            Intent intent = new Intent(ViewGoodTimes.this, MainActivity.class);
                            startActivity(intent);
                        }
                    });

            alertDialog.show();
            alertDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialogInterface) {
                    Intent intent = new Intent(ViewGoodTimes.this, MainActivity.class);
                    startActivity(intent);
                }
            });
        }
    }

    private void initRecyclerView(){
        RecyclerView recyclerView = findViewById(R.id.view_recylerview);

        DatabaseHandler databaseHandler = new DatabaseHandler(ViewGoodTimes.this);
        ArrayList<GoodTime> goodTimes = databaseHandler.TimesGetRows();

        for (GoodTime g : goodTimes){
            Log.d("testGeneral", g.toString());
            databaseHandler.ImagesGetImages(g.getTid());
        }

        ViewGoodTimeRecyclerViewAdapter adapter = new ViewGoodTimeRecyclerViewAdapter(this,goodTimes);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

}
