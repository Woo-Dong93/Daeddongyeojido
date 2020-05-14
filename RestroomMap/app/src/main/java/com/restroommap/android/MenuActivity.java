package com.restroommap.android;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class MenuActivity extends AppCompatActivity {
    private Button findToiletButton; // 화장실찾기 버튼
    private Button functionButton; // 기능 버튼
    private Button contentButton; //컨텐츠 버튼
    public String flag = "";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        flag = getIntent().getStringExtra("ALL"); // introActivity에서의 전달값

        buttonInit();

        findToiletButton.setOnClickListener(new View.OnClickListener() { // 화장실 찾기 버튼 클릭
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuActivity.this, MainActivity.class);
                intent.putExtra("ALL", flag);
                startActivity(intent);
            }
        });

        functionButton.setOnClickListener(new View.OnClickListener() { // 다양한 기능 버튼 클릭
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuActivity.this, FunctionActivity.class);
                startActivity(intent);
            }
        });

        contentButton.setOnClickListener(new View.OnClickListener() { // 컨텐츠 버튼 클릭
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuActivity.this, ContentActivity.class);
                startActivity(intent);
            }
        });
    }

    private void buttonInit() {
        findToiletButton = (Button) findViewById(R.id.findToiletBtn);
        functionButton = (Button) findViewById(R.id.functionBtn);
        contentButton = (Button) findViewById(R.id.contentBtn);
    }
}
