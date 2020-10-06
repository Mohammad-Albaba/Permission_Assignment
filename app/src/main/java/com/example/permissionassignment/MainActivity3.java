package com.example.permissionassignment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainActivity3 extends AppCompatActivity {
private final String ACTION_VIEW_IMPORTANT_DATA ="com.example.action.VIEW_IMPORTANT_DATA";
private final String PERMISSION_VIEW_IMPORTANT_DATA = "com.example.permission.VIEW_IMPORTANT_DATA";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

    }

    public void viewImportantData(View view) {
        if (ContextCompat.checkSelfPermission(this,PERMISSION_VIEW_IMPORTANT_DATA)== PackageManager.PERMISSION_GRANTED){
            Intent intent = new Intent(ACTION_VIEW_IMPORTANT_DATA);
            startActivity(intent);
        }else {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this , PERMISSION_VIEW_IMPORTANT_DATA)){
                new AlertDialog.Builder(this)
                        .setMessage(getString(R.string.permission_message))
                        .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                ActivityCompat.requestPermissions(MainActivity3.this, new String[]{PERMISSION_VIEW_IMPORTANT_DATA}, 0);

                            }
                        }).create().show();
            }else {
                ActivityCompat.requestPermissions(this, new String[]{PERMISSION_VIEW_IMPORTANT_DATA}, 0);
            }}

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 0){
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Intent intent = new Intent(ACTION_VIEW_IMPORTANT_DATA);
                startActivity(intent);
            }else {
                Toast.makeText(this, "Permission Denied!", Toast.LENGTH_SHORT).show();
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}