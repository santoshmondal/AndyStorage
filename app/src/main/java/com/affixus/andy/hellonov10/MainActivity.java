package com.affixus.andy.hellonov10;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private SharedPreferences prefs;
    private MySqliteHelper mySqliteHelper;

    private EditText editText1;
    private EditText editText2;

    private Button buttonNext;
    private Button buttonShared;
    private Button buttonInternal;
    private Button buttonInternalCache;
    private Button buttonExternalCache;
    private Button buttonExternalPublic;
    private Button buttonExternalPrivate;
    private Button buttonSqlite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initialize();

        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("FIRST_KEY", "HELLO FIRST VALUE");
        editor.commit();

        buttonShared.setOnClickListener(this);
        buttonNext.setOnClickListener(this);
        buttonInternal.setOnClickListener(this);
        buttonInternalCache.setOnClickListener(this);
        buttonExternalCache.setOnClickListener(this);
        buttonExternalPublic.setOnClickListener(this);
        buttonExternalPrivate.setOnClickListener(this);
        buttonSqlite.setOnClickListener(this);
    }

    private void initialize(){
        prefs = getSharedPreferences("MY_PREFS", Context.MODE_PRIVATE);
        mySqliteHelper = new MySqliteHelper(this);
        mySqliteHelper.getWritableDatabase();

        editText1 = (EditText) findViewById(R.id.editText1);
        editText2 = (EditText) findViewById(R.id.editText2);

        buttonNext = (Button) findViewById(R.id.buttonNext);
        buttonShared = (Button) findViewById(R.id.buttonShared);
        buttonInternal = (Button) findViewById(R.id.buttonInternal);
        buttonInternalCache = (Button) findViewById(R.id.buttonInternalCache);
        buttonExternalCache = (Button) findViewById(R.id.buttonExternalCache);
        buttonExternalPublic = (Button) findViewById(R.id.buttonExternalPublic);
        buttonExternalPrivate = (Button) findViewById(R.id.buttonExternalPrivate);
        buttonSqlite = (Button) findViewById(R.id.buttonSqlite);
    }

    @Override
    public void onClick(View v) {
        if(R.id.buttonNext == v.getId()) {
            handleButtonNext();
        } else if(R.id.buttonShared == v.getId()) {
            handleShared();
        } else if(R.id.buttonInternal == v.getId()) {
            handleInternalStorage();
        } else if(R.id.buttonInternalCache == v.getId()) {
            handleInternalCache();
        } else if(R.id.buttonExternalCache == v.getId()) {
            handleExternalCache();
        } else if(R.id.buttonExternalPublic == v.getId()) {
            handlePublicWrite();
        } else if(R.id.buttonExternalPrivate == v.getId()) {
            handlePrivateWrite();
        } else if(R.id.buttonSqlite == v.getId()) {
            writeToSqlite();
        }
    }

    private void writeToSqlite() {
        String key = editText1.getText().toString();
        String value = editText2.getText().toString();

        String state = Environment.getExternalStorageState();
        if(key != null && !key.trim().isEmpty() && value != null && !value.trim().isEmpty()) {
            mySqliteHelper.insert(key);
            Toast.makeText(this, "SQLlite Success", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Please enter values.", Toast.LENGTH_SHORT).show();
        }
    }

    private void handlePrivateWrite() {
        String key = editText1.getText().toString();
        String value = editText2.getText().toString();

        String state = Environment.getExternalStorageState();
        if(key != null && !key.trim().isEmpty() && value != null && !value.trim().isEmpty()) {
            if(Environment.MEDIA_MOUNTED.equals(state)) {
                Toast.makeText(this, "MOUNTED", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "NOT MOUNTED", Toast.LENGTH_SHORT).show();
                return;
            }

            File filesDir = getExternalFilesDir(Environment.DIRECTORY_MOVIES);
            try {
                File writeFile = new File(filesDir.getCanonicalPath()  + File.separator + "myfolder", "ext_pri.txt");
                writeFile.getParentFile().mkdirs();

                writeFile(key + " = " + value, writeFile);

                Toast.makeText(this, filesDir.getCanonicalPath(), Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                e.printStackTrace();
            }

            Toast.makeText(this, "External PRIVATE STORAGE UPDATED.", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Please enter values.", Toast.LENGTH_SHORT).show();
        }
    }

    private void handlePublicWrite() {
        String key = editText1.getText().toString();
        String value = editText2.getText().toString();

        String state = Environment.getExternalStorageState();
        if(Environment.MEDIA_MOUNTED.equals(state)) {
            Toast.makeText(this, "MOUNTED", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "NOT MOUNTED", Toast.LENGTH_SHORT).show();
            return;
        }

        File filesDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MOVIES);
        if(key != null && !key.trim().isEmpty() && value != null && !value.trim().isEmpty()) {
            try {
                File writeFile = new File(filesDir.getCanonicalPath()  + File.separator + "myfolder", "ext_pub.txt");
                writeFile.getParentFile().mkdirs();

                writeFile(key + " = " + value, writeFile);

                Toast.makeText(this, filesDir.getCanonicalPath(), Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                e.printStackTrace();
            }

            Toast.makeText(this, "External PUBLIC STORAGE UPDATED.", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Please enter values.", Toast.LENGTH_SHORT).show();
        }
    }

    private void handleExternalCache() {
        String key = editText1.getText().toString();
        String value = editText2.getText().toString();

        File filesDir = getExternalCacheDir();
        if(key != null && !key.trim().isEmpty() && value != null && !value.trim().isEmpty()) {
            try {
                File writeFile = new File(filesDir.getCanonicalPath()  + File.separator + "myfolder", "MY_FILE2.txt");
                writeFile.getParentFile().mkdirs();

                writeFile(key + " = " + value, writeFile);

                Toast.makeText(this, filesDir.getCanonicalPath(), Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                e.printStackTrace();
            }

            Toast.makeText(this, "External CACHE STORAGE UPDATED.", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Please enter values.", Toast.LENGTH_SHORT).show();
        }
    }

    private void handleInternalCache() {
        String key = editText1.getText().toString();
        String value = editText2.getText().toString();

        File filesDir = getCacheDir();
        if(key != null && !key.trim().isEmpty() && value != null && !value.trim().isEmpty()) {
            try {
                File writeFile = new File(filesDir.getCanonicalPath()  + File.separator + "myfolder", "MY_FILE2.txt");
                writeFile.getParentFile().mkdirs();

                writeFile(key + " = " + value, writeFile);

                Toast.makeText(this, filesDir.getCanonicalPath(), Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                e.printStackTrace();
            }

            Toast.makeText(this, "CACHE STORAGE UPDATED.", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Please enter values.", Toast.LENGTH_SHORT).show();
        }
    }

    private void handleInternalStorage() {
        String key = editText1.getText().toString();
        String value = editText2.getText().toString();

        File filesDir = getFilesDir();
        if(key != null && !key.trim().isEmpty() && value != null && !value.trim().isEmpty()) {
            try {
                //fos = openFileOutput("MY_FILE.txt", Context.MODE_APPEND);
                File writeFile = new File(filesDir.getCanonicalPath()  + File.separator + "myfolder", "MY_FILE2.txt");
                writeFile.getParentFile().mkdirs();

                writeFile(key + " = " + value, writeFile);

                Toast.makeText(this, filesDir.getCanonicalPath(), Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                e.printStackTrace();
            }

            Toast.makeText(this, "INTERANAL STORAGE UPDATED.", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Please enter values.", Toast.LENGTH_SHORT).show();
        }

    }

    private void writeFile(String data, File file) throws Exception{
        StringWriter sw = new StringWriter();
        FileWriter fileWriterw = null;
        try {
            fileWriterw = new FileWriter(file, true);
            sw.write(data);
            sw.write("\n");
            fileWriterw.write(sw.toString());
        } finally{
            fileWriterw.close();
        }
    }

    private void handleShared() {
        String key = editText1.getText().toString();
        String value = editText2.getText().toString();

        if(key != null && !key.trim().isEmpty() && value != null && !value.trim().isEmpty()) {
            SharedPreferences.Editor editor = prefs.edit();
            editor.putString(key, value);
            editor.commit();

            Toast.makeText(this, "PREFS UPDATED.", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Please enter values.", Toast.LENGTH_SHORT).show();
        }
    }

    private void handleButtonNext() {
        Intent intent = new Intent(this, NextActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}
