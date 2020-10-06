package com.example.permissionassignment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class ContactsActivity extends AppCompatActivity {
    private static final int REQUEST_READ_PERMISSION = 1;
    private static final String TAG = ContactsActivity.class.getSimpleName();
//    private static final int PERMISSIONS_REQUEST_READ_CONTACTS = 1;

    static final String[] projection = {ContactsContract.Contacts.DISPLAY_NAME, ContactsContract.Contacts.TIMES_CONTACTED};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        findViewById(R.id.gotodangerous).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ContactsActivity.this, MainActivity3.class);
                startActivity(intent);
            }
        });

        findViewById(R.id.getcontacts).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkPermissionAndGetListCall();
            }
        });
    }
    private void loadContacts() {

        Log.i(TAG, "Entered loadContacts()");

        StringBuilder text = new StringBuilder();


        Cursor cursor = getContentResolver().query(ContactsContract.Contacts.CONTENT_URI, projection, null, null, null);

        if (cursor != null && cursor.getCount() != 0) {
            while (cursor.moveToNext()) {
                text.append(cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME)));
                text.append(" (").append(cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.TIMES_CONTACTED))).append(")");
                text.append("\n\n");
            }
        }

        TextView box = findViewById(R.id.text);
        box.setText(text.toString());

        Log.i(TAG, "Contacts loaded");
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_READ_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                checkPermissionAndGetListCall();
            } else {
                Toast.makeText(this, "Can't get the contacts list!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void checkPermissionAndGetListCall() {

        if (ContextCompat.checkSelfPermission(ContactsActivity.this, Manifest.permission.READ_CONTACTS) == PackageManager.PERMISSION_GRANTED) {
                loadContacts();
        } else {
            // إذا رجعت فولس بتكون اول مرة واذا رجعت ترو بتكون اني مرة أو أكثر فبدي أعمل توضيح للمستخدم عشان يرضى يوافق هاد بالنسبة ل شود شو ريكويست بارميشين
            if (ActivityCompat.shouldShowRequestPermissionRationale(ContactsActivity.this, Manifest.permission.READ_CONTACTS)) {
                new AlertDialog.Builder(this).setMessage("Read Contacts permission needed to be able to bring contacts here").setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ActivityCompat.requestPermissions(ContactsActivity.this, new String[]{Manifest.permission.READ_CONTACTS}, REQUEST_READ_PERMISSION);

                    }
                }).setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).create().show();
            } else {
                ActivityCompat.requestPermissions(ContactsActivity.this, new String[]{Manifest.permission.READ_CONTACTS}, REQUEST_READ_PERMISSION);

            }
        }
    }


}