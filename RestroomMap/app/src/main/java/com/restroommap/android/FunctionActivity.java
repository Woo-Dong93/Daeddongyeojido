package com.restroommap.android;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import static com.restroommap.android.R.id.abutton;
import static com.restroommap.android.R.id.button3;
import static com.restroommap.android.R.id.button4;

public class FunctionActivity extends AppCompatActivity {
    private Button acbutton; //
    private Button bcbutton;
    private Button ccbutton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_function);
        buttonInit();

        acbutton.setOnClickListener(new View.OnClickListener() { // 컨텐츠 버튼 클릭
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(FunctionActivity.this, FunctionaActivity.class);
                startActivity(intent);
            }
        });
        bcbutton.setOnClickListener(new View.OnClickListener() { // 컨텐츠 버튼 클릭
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(FunctionActivity.this, FunctionActivity2.class);
                startActivity(intent);
            }
        });
        ccbutton.setOnClickListener(new View.OnClickListener() { // 컨텐츠 버튼 클릭
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(FunctionActivity.this, FunctionActivity3.class);
                startActivity(intent);
            }
        });
    }

    private void buttonInit() {

        acbutton = (Button) findViewById(abutton);
        bcbutton = (Button) findViewById(button3);
        ccbutton = (Button) findViewById(button4);
    }





}
