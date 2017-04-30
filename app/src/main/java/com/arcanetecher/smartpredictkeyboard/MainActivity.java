package com.arcanetecher.smartpredictkeyboard;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.MenuInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button dashboardBtn = (Button)findViewById(R.id.dashboardbtn);
        Button enableBtn = (Button)findViewById(R.id.enableBtn);
        Button switchBtn = (Button)findViewById(R.id.switchBtn);

        switchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InputMethodManager imeManager = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imeManager != null) {
                    imeManager.showInputMethodPicker();
                } else {
                    Log.e("Error", "Cannot switch ime");
                }
            }
        });

        enableBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Settings.ACTION_INPUT_METHOD_SETTINGS);
                startActivity(intent);
            }
        });

        dashboardBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String ime = Settings.Secure.getString(
                        getContentResolver(),
                        Settings.Secure.DEFAULT_INPUT_METHOD
                );
                if (ime.equals("com.arcanetecher.smartpredictkeyboard/.SoftKeyboard")) {
                    Intent intent = new Intent(MainActivity.this, Menu.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(MainActivity.this, "Please switch to ខ្មែរយើង keyboard",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, (android.view.Menu) menu);
        return true;
    }
}
