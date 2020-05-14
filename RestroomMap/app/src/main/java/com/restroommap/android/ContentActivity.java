package com.restroommap.android;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

public class ContentActivity extends AppCompatActivity {
    @Override


    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content);

        ImageView imglink = (ImageView)this.findViewById(R.id.imageView12);
        imglink.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("http://naver.com"));
                startActivity(intent);
            }
        });

        imglink = (ImageView)this.findViewById(R.id.imageView13);
        imglink.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("https://www.facebook.com"));
                startActivity(intent);
            }
        });

        imglink = (ImageView)this.findViewById(R.id.imageView14);
        imglink.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("https://www.youtube.com"));
                startActivity(intent);
            }
        });

        imglink = (ImageView)this.findViewById(R.id.imageView15);
        imglink.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("https://www.instagram.com"));
                startActivity(intent);
            }
        });

        imglink = (ImageView)this.findViewById(R.id.imageView16);
        imglink.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("http://www.afreecatv.com"));
                startActivity(intent);
            }
        });

        imglink = (ImageView)this.findViewById(R.id.imageView17);
        imglink.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("https://twitter.com"));
                startActivity(intent);
            }
        });

        imglink = (ImageView)this.findViewById(R.id.imageView18);
        imglink.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("http://www.kocon.or.kr/patient/pt1.html"));
                startActivity(intent);
            }
        });
    }
}
