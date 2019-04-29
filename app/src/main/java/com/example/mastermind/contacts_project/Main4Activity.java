package com.example.mastermind.contacts_project;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

public class Main4Activity extends AppCompatActivity {

    EditText nameED,emailED,phoneED;
    ImageView imageView;
    String imgUri;
    int id;
    ImageButton imageButton;
    Button save1,cancel1;
    MyDatabase database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);
        imageButton=findViewById(R.id.addphoto);
        nameED=findViewById(R.id.name);
        phoneED=findViewById(R.id.phonenumber);
        emailED=findViewById(R.id.email);
        imageView=findViewById(R.id.profilePic);
        save1=findViewById(R.id.save);
        cancel1=findViewById(R.id.cancel);

        Intent intent=getIntent();
        nameED.setText(intent.getStringExtra("name"));
        phoneED.setText(intent.getStringExtra("phone"));
        emailED.setText(intent.getStringExtra("email"));
        imgUri=intent.getStringExtra("imgid");
        id=intent.getIntExtra("id",-1);

            Glide.with(Main4Activity.this).load(Uri.parse(imgUri)).into(imageView);


        cancel1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=getIntent();
                nameED.setText(intent.getStringExtra("name"));
                phoneED.setText(intent.getStringExtra("phone"));
                emailED.setText(intent.getStringExtra("email"));
                imgUri=intent.getStringExtra("imgid");
                id=intent.getIntExtra("id",-1);
                Glide.with(Main4Activity.this).load(Uri.parse(imgUri)).into(imageView);

            }
        });
        save1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //update the database
                database=new MyDatabase(Main4Activity.this);
               boolean result= database.updateContacts(""+id,nameED.getText().toString(),phoneED.getText().toString()
                ,emailED.getText().toString(),imgUri);
                if(result)
                {
                    Toast.makeText(getApplicationContext(),"Contact Updated",Toast.LENGTH_SHORT).show();
                    Intent i=new Intent(Main4Activity.this,MainActivity.class);
                    startActivity(i);
                }
                else
                    {
                        Toast.makeText(getApplicationContext(),"Error",Toast.LENGTH_SHORT).show();

                    }

            }
        });


    }
}
