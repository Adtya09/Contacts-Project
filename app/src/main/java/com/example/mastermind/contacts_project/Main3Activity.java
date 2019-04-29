package com.example.mastermind.contacts_project;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.io.IOException;
import java.util.ArrayList;

public class Main3Activity extends AppCompatActivity {
    ImageView Profile,call,emailImageViewpic;
    TextView phoneNumber,Email,name,edit,favour;
    String nameS,emailS,phoneS,imgUriS;
    int id;
    TextView share,delete1;
    MyDatabase database;
    public static ArrayList<String>nameF;
    public static ArrayList<String>phoneF;
    public static ArrayList<String>emailF;
    public static ArrayList<String>imageF;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        favour=findViewById(R.id.addtoFavourate);
        phoneNumber=findViewById(R.id.phoneNumber);
        Profile=findViewById(R.id.fullimg);
        name=findViewById(R.id.name1);
        Email=findViewById(R.id.emailing);
        call=findViewById(R.id.call);
        edit=findViewById(R.id.edit);
        share=findViewById(R.id.shareContact);
        delete1=findViewById(R.id.deleteContact);
        nameF=new ArrayList<>();
        phoneF=new ArrayList<>();
        emailF=new ArrayList<>();
        imageF=new ArrayList<>();
        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Intent.ACTION_SEND);
                intent.putExtra(Intent.EXTRA_TEXT,nameS+"\n\n"+phoneS);
                intent.setType("text/plain");
                startActivity(intent);
            }
        });

        delete1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder=new AlertDialog.Builder(Main3Activity.this);
                builder.setTitle("Warning!!");
                builder.setMessage("Do You Really Want to delete ?");
                builder.setCancelable(false);
                builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        database = new MyDatabase(Main3Activity.this);
                        boolean result = database.deleteContact(""+id);
                        if (result) {
                            Toast.makeText(getApplicationContext(),"Contact Deleted",Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(Main3Activity.this, MainActivity.class);
                            startActivity(intent);
                                }
                        else {
                            Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
                        }

                    }
                });

                builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.dismiss();
                    }
                });
                builder.create().show();



            }
        });



        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openEditActivity();
            }
        });
        emailImageViewpic=findViewById(R.id.email1);
        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
          makephoneCall();

            }
        });
        emailImageViewpic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendEmail();
            }
        });
        Intent intent=getIntent();
        nameS=intent.getStringExtra("name");
        emailS=intent.getStringExtra("email");
        phoneS=intent.getStringExtra("phone");
        imgUriS=intent.getStringExtra("ImageUri");
        id=intent.getIntExtra("id",-1);
        name.setText(nameS);
        Email.setText(emailS);
        phoneNumber.setText(phoneS);
        Glide.with(Main3Activity.this).load(Uri.parse(imgUriS)).into(Profile);



        favour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                MyDatabase database=new MyDatabase(Main3Activity.this);
                boolean res=database.addtoFav(nameS,phoneS,emailS,imgUriS);
                if(res==true) {
                    Toast.makeText(getApplicationContext(), "Added to Favourates", Toast.LENGTH_SHORT).show();
                    }
                    else {
                    Toast.makeText(getApplicationContext(), "Error in Adding to Favourates", Toast.LENGTH_SHORT).show();

                }

            }
        });

    }






    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode==1)
        {
            if(grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED)
            {
                makephoneCall();
            }
            else{
                Toast.makeText(getApplicationContext(),"Permission Denied",Toast.LENGTH_SHORT).show();
            }
        }
    }

    void makephoneCall()
{
    if(ContextCompat.checkSelfPermission(Main3Activity.this,Manifest.permission.CALL_PHONE)
            !=PackageManager.PERMISSION_GRANTED)
    {
        ActivityCompat.requestPermissions(Main3Activity.this,new String[]{Manifest.permission.CALL_PHONE},1);
    }
    else{
        String dial="tel:"+phoneS;
        startActivity(new Intent(Intent.ACTION_CALL,Uri.parse(dial)));

    }


}
void sendEmail()
{

    Intent intent=new Intent(Intent.ACTION_SEND);
    String email[]={emailS};
    intent.putExtra(Intent.EXTRA_EMAIL,email);
    intent.setType("message/rfc822");
    startActivity(Intent.createChooser(intent,"Select Email-Client"));

}
void openEditActivity()
{
    Intent intent=new Intent(Main3Activity.this,Main4Activity.class);
    intent.putExtra("id",id);
    intent.putExtra("name",nameS);
    intent.putExtra("phone",phoneS);
    intent.putExtra("email",emailS);
    intent.putExtra("imgid",imgUriS);
    startActivity(intent);


}


}



