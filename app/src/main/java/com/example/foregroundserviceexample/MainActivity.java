package com.example.foregroundserviceexample;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    public static final String KEY_INPUT_EXTRA = "inputExtra";
    private Button btnStartService,btnStopService;
    private EditText etInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etInput = findViewById(R.id.et_input);
        btnStartService = findViewById(R.id.btn_start_service);
        btnStopService = findViewById(R.id.btn_stop_service);

        btnStartService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent serviceIntent = new Intent(MainActivity.this,ExampleService.class);
                serviceIntent.putExtra(KEY_INPUT_EXTRA, etInput.getText().toString());
                startService(serviceIntent);
                /*Call startForegroundService(serviceIntent) if you want to start the service while your app in
                * background. It will give you 5s time-frame to call startForeground(id,notification), if you don't
                * call startForeground system will kill the service after 5s. startForegroundService(serviceIntent) method
                * is introduced on API level 26 so you should use condition to check version or you can use ContextCompat.startForegroundService(context,serviceIntent)
                * which does the same version check*/
                //ContextCompat.startForegroundService(MainActivity.this,serviceIntent);
            }
        });

        btnStopService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopService(new Intent(MainActivity.this,ExampleService.class));
            }
        });
    }
}
