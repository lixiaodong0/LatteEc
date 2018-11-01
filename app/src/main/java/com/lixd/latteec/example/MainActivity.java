package com.lixd.latteec.example;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.lixd.latte.core.Latte;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Toast.makeText(Latte.getApplicationContext(), "传入Context成功了", Toast.LENGTH_SHORT).show();

    }
}
