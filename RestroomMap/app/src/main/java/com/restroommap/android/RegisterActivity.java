package com.restroommap.android;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

public class RegisterActivity extends AppCompatActivity {
    private Button divisionBtn;
    private Button existBtn;
    private Button childBtn;
    private Button registerBtn;
    private Button commonBtn;
    private Button notExistBtn;
    private Button disableBtn;
    private Button cancelBtn;
    private String location;

    private Button[] buttons = new Button[6];
    private boolean[] isSelectItems = new boolean[6]; // 0: 분리 1:공용 2:있음 3:없음 4:아동 5:장애

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        location = getIntent().getStringExtra("location");

        btnInit();

        divisionCommonSelect();
        existNotExistSelect();
        childDisableSelect();
        close();
        requestRegister();
    }

    private void btnInit() {
        divisionBtn = (Button) findViewById(R.id.divisionBtn);
        existBtn = (Button) findViewById(R.id.existBtn);
        childBtn = (Button) findViewById(R.id.childBtn);
        registerBtn = (Button) findViewById(R.id.registerBtn);
        commonBtn = (Button) findViewById(R.id.commonBtn);
        notExistBtn = (Button) findViewById(R.id.notExistBtn);
        disableBtn = (Button) findViewById(R.id.disableBtn);
        cancelBtn = (Button) findViewById(R.id.cancelBtn);

        buttons[0] = divisionBtn;
        buttons[1] = commonBtn;
        buttons[2] = existBtn;
        buttons[3] = notExistBtn;
        buttons[4] = childBtn;
        buttons[5] = disableBtn;
    }

    private void divisionCommonSelect() {
        divisionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //ui는 모두 변경하셔야 합니다
                divisionBtn.setBackgroundColor(Color.BLACK);
                divisionBtn.setTextColor(Color.WHITE);

                commonBtn.setBackgroundColor(Color.GRAY);
                commonBtn.setTextColor(Color.BLACK);

                //선택된
                isSelectItems[0] = true;
                isSelectItems[1] = false;
            }
        });

        commonBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                commonBtn.setBackgroundColor(Color.BLACK);
                commonBtn.setTextColor(Color.WHITE);

                divisionBtn.setBackgroundColor(Color.GRAY);
                divisionBtn.setTextColor(Color.BLACK);

                isSelectItems[1] = true;
                isSelectItems[0] = false;
            }
        });
    }

    private void existNotExistSelect() {
        existBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //ui는 모두 변경하셔야 합니다
                existBtn.setBackgroundColor(Color.BLACK);
                existBtn.setTextColor(Color.WHITE);

                notExistBtn.setBackgroundColor(Color.GRAY);
                notExistBtn.setTextColor(Color.BLACK);

                //선택된
                isSelectItems[2] = true;
                isSelectItems[3] = false;
            }
        });

        notExistBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                notExistBtn.setBackgroundColor(Color.BLACK);
                notExistBtn.setTextColor(Color.WHITE);

                existBtn.setBackgroundColor(Color.GRAY);
                existBtn.setTextColor(Color.BLACK);

                isSelectItems[3] = true;
                isSelectItems[2] = false;
            }
        });
    }

    private void childDisableSelect() {
        childBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //ui는 모두 변경하셔야 합니다
                childBtn.setBackgroundColor(Color.BLACK);
                childBtn.setTextColor(Color.WHITE);

                disableBtn.setBackgroundColor(Color.GRAY);
                disableBtn.setTextColor(Color.BLACK);

                //선택된
                isSelectItems[4] = true;
                isSelectItems[5] = false;
            }
        });

        disableBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                disableBtn.setBackgroundColor(Color.BLACK);
                disableBtn.setTextColor(Color.WHITE);

                childBtn.setBackgroundColor(Color.GRAY);
                childBtn.setTextColor(Color.BLACK);

                isSelectItems[5] = true;
                isSelectItems[4] = false;
            }
        });
    }

    private void requestRegister() {
        final ArrayList<String> sendMsg = new ArrayList<>();
        sendMsg.add(location); // 위치 전송

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i=0;i<6;i++) {
                    if (isSelectItems[i]) {
                        sendMsg.add(buttons[i].getText().toString()); //선택한 것들 전송
                    }
                }
                new Client(sendMsg);
                finish();

            }
        });
    }
    private void close(){
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
