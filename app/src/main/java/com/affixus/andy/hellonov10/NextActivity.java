package com.affixus.andy.hellonov10;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;

public class NextActivity extends AppCompatActivity implements View.OnClickListener {
    private SharedPreferences prefs;
    private MySqliteHelper mySqliteHelper;

    private Button button7;
    private Button button;
    private Button button2;
    private Button button3;
    private Button button4;
    private Button button5;
    private Button button6;
    private Button button8;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_next);

        initialze();

        button7.setOnClickListener(this);
        button.setOnClickListener(this);
        button2.setOnClickListener(this);
        button3.setOnClickListener(this);
        button4.setOnClickListener(this);
        button5.setOnClickListener(this);
        button6.setOnClickListener(this);
        button8.setOnClickListener(this);
    }

    private void initialze() {
        prefs = getSharedPreferences("MY_PREFS", Context.MODE_PRIVATE);
        mySqliteHelper = new MySqliteHelper(this);

        button7 = (Button) findViewById(R.id.button7);
        button = (Button) findViewById(R.id.button);
        button2 = (Button) findViewById(R.id.button2);
        button3 = (Button) findViewById(R.id.button3);
        button4 = (Button) findViewById(R.id.button4);
        button5 = (Button) findViewById(R.id.button5);
        button6 = (Button) findViewById(R.id.button6);
        button8 = (Button) findViewById(R.id.button8);
    }

    @Override
    public void onClick(View v) {
        if (R.id.button7 == v.getId()) {
            handlePrevious();
        } else if (R.id.button == v.getId()) {
            handleShared();
        } else if (R.id.button2 == v.getId()) {
            loadInternalStorage();
        } else if(R.id.button3 == v.getId()) {
            loadInternalCache();
        } else if(R.id.button4 == v.getId()) {
            loadExternalCache();
        } else if(R.id.button5 == v.getId()){
            loadExPublic();
        } else if(R.id.button6 == v.getId()) {
            loadExPri();
        } else if(R.id.button8 == v.getId()) {
            mySqliteHelper.selectAll();
        }
    }

    private void loadExPri() {
        try {
            File filesDir = getExternalFilesDir(Environment.DIRECTORY_MOVIES);
            File readFile = new File(filesDir + File.separator + "myfolder", "ext_pri.txt");

            String data = readFile(readFile);
            Toast.makeText(this, data, Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Exception", Toast.LENGTH_SHORT).show();
        }
    }

    private void loadExPublic() {
        try {
            File filesDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MOVIES);
            File readFile = new File(filesDir + File.separator + "myfolder", "ext_pub.txt");

            String data = readFile(readFile);
            Toast.makeText(this, data, Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Exception", Toast.LENGTH_SHORT).show();
        }
    }

    private void loadExternalCache() {
        try {
            File filesDir = getExternalCacheDir();
            File readFile = new File(filesDir + File.separator + "myfolder", "MY_FILE2.txt");

            String data = readFile(readFile);
            Toast.makeText(this, data, Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Exception", Toast.LENGTH_SHORT).show();
        }
    }

    private void loadInternalCache() {
        try {
            File filesDir = getCacheDir();
            File readFile = new File(filesDir + File.separator + "myfolder", "MY_FILE2.txt");

            String data = readFile(readFile);
            Toast.makeText(this, data, Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Exception", Toast.LENGTH_SHORT).show();
        }
    }

    private void loadInternalStorage() {

        try {
            File filesDir = getFilesDir();
            File readFile = new File(filesDir + File.separator + "myfolder", "MY_FILE2.txt");

            String data = readFile(readFile);
            Toast.makeText(this, data, Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Exception", Toast.LENGTH_SHORT).show();
        }

    }

    private String readFile(File file) throws Exception {
        BufferedReader br = null;
        StringBuffer sb = new StringBuffer("");
        try {
            br = new BufferedReader(new FileReader(file));

            String str = br.readLine();
            while (str != null) {
                sb.append(str).append("\n");
                str = br.readLine();
            }
        } finally {
            br.close();
        }

        return sb.toString();
    }

    private void handleShared() {
        Map<String, ?> all = prefs.getAll();
        StringBuffer sb = new StringBuffer("");
        for (Map.Entry<String, ?> entry : all.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();
            sb.append(key).append(" = ").append(value).append("\n");
        }

        Toast.makeText(this, sb.toString(), Toast.LENGTH_SHORT).show();
    }

    private void handlePrevious() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_next, menu);
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
