package com.example.mastermind.contacts_project;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;

public class Main2Activity extends AppCompatActivity {
    Button save,cancel;
    EditText name,mobile,email;
    ImageView imageView;
    ImageButton imgb;
    Uri ImgUri;
    String nameS,phoneS,emailS,imguriS;
    MyDatabase database;
    boolean imageInserted=false;
    boolean saveEnabled=false;
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent=new Intent(this,MainActivity.class);
        startActivity(intent);


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==1 && resultCode==Activity.RESULT_OK)
        {   imageInserted=true;
            ImgUri=data.getData();
            imguriS=ImgUri.toString();
            if(imageInserted)
            {
               Glide.with(Main2Activity.this).load(ImgUri).into(imageView);
            }

        }


    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        initializeId();
        imgb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallary();
            }
        });
            save.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //data will be saved to Database
                    nameS = name.getText().toString().trim();
                    phoneS = mobile.getText().toString().trim();
                    emailS = email.getText().toString().trim();
                    if (imageInserted == false) {
                        Uri img = Uri.parse("android.resource://" + getPackageName() + "/" + R.drawable.ic_android_black_24dp);
                        imguriS = img.toString();
                    }
                    boolean result = database.addContacts(nameS, phoneS, emailS, imguriS);
                    if (result) {
                        Toast.makeText(getApplicationContext(), "Contact Added", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(Main2Activity.this, MainActivity.class);
                        startActivity(intent);
                    } else
                        Toast.makeText(getApplicationContext(), "Contact  Already exists!!" +
                                "", Toast.LENGTH_LONG).show();

                }
            });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                email.setText("");
                mobile.setText("");
                name.setText("");

                imageView.setImageResource(R.drawable.ic_android_black_24dp);


            }
        });



    }
    public  void openGallary()
    {
        Intent galaryIntent=new Intent();
        galaryIntent.setType("image/*");
        galaryIntent.setAction(Intent.ACTION_OPEN_DOCUMENT);
        startActivityForResult(Intent.createChooser(galaryIntent,"Select Picture"),1);
    }
    public  void initializeId()
    {
        database=new MyDatabase(this);
        save=findViewById(R.id.save);
        cancel=findViewById(R.id.cancel);
        name=findViewById(R.id.name);
        imgb=findViewById(R.id.addphoto);
        email=findViewById(R.id.email);
        mobile=findViewById(R.id.phonenumber);
        imageView=findViewById(R.id.profilePic);


}


}
