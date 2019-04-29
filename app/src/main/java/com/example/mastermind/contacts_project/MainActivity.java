package com.example.mastermind.contacts_project;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    boolean permission=false;
    ArrayList<Integer>arrayListId;
    ArrayList<String>arrayListName;
    Toolbar toolbar;
    ActionBar actionBar;
    NavigationView navigationView;
    DrawerLayout drawerLayout;
    FloatingActionButton fab;
    MyDatabase database;
    SearchView searchView;
    ArrayList<String> arrayListPhone;
    ArrayList<String> arrayListEmail;
    ArrayList<String>arrayListImg;
    MyCustomAdapter customAdapter;
    Cursor cursor;
    RecyclerView recyclerView;
    ArrayList<Integer>arrayListIdD;
    ArrayList<String>arrayListNameD;
    ArrayList<String>arrayListPhoneD;
    ArrayList<String>arrayListEmailD;
    ArrayList<String> arrayListImgD;

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode==1)
        {
            if(grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED)
            {
                Toast.makeText(getApplicationContext(),"Welcome",Toast.LENGTH_SHORT).show();
            }
            else
                {

                    Toast.makeText(getApplicationContext(),"Permission Denied",Toast.LENGTH_SHORT).show();
                }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if(ContextCompat.checkSelfPermission(this,Manifest.permission.WRITE_EXTERNAL_STORAGE)==
            PackageManager.PERMISSION_GRANTED)
        {//If Permission Granted
            permission=true;

            }
        else{
            requestforPermission();
        }
        Main3Activity.nameF=new ArrayList<>();

    }

    @Override
    protected void onPostResume() {
        super.onPostResume();

        initizeall();
        settingNavigationDrawer();
        addContact();
        gettingContacts();
        favlist();
        settingListView();
        workingOfSearchView();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void addContact()
    {

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,Main2Activity.class);
                startActivity(intent);
            }
        });
    }

    private void settingNavigationDrawer()
    {
        toolbar.setTitle("Contacts");
        setSupportActionBar(toolbar);
        actionBar=getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_menu_black_24dp);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                switch (menuItem.getItemId())
                {
                    case R.id.favourate:
         MyCustomAdapter customAdapter1=new MyCustomAdapter(MainActivity.this,Main3Activity.nameF,Main3Activity.phoneF,Main3Activity.emailF,Main3Activity.imageF);
                      recyclerView.setAdapter(customAdapter1);
                        customAdapter1.setOnItemClickm(new MyCustomAdapter.OnClickListemerm() {
                            @Override
                            public void onItemClickm(int position)
                            {
                                // Launching Details activity

                                Intent intent=new Intent(MainActivity.this,Main3Activity.class);
                                intent.putExtra("name",Main3Activity.nameF.get(position));
                                intent.putExtra("phone",Main3Activity.phoneF.get(position));
                                intent.putExtra("email",Main3Activity.emailF.get(position));
                                intent.putExtra("ImageUri",Main3Activity.imageF.get(position));
                                startActivity(intent);

                            }
                        });
                        if(Main3Activity.nameF.size()>0)
                        {
                            TextView tv=findViewById(R.id.tv);
                            tv.setText("");
                        }
                        else {
                            TextView tv=findViewById(R.id.tv);
                            tv.setText("No Favouare Contacts :)");

                        }
                        customAdapter.notifyDataSetChanged();






                        break;

                    case R.id.share:
                        Intent sendIntent = new Intent();
                        sendIntent.setAction(Intent.ACTION_SEND);
                        sendIntent.putExtra(Intent.EXTRA_TEXT,
                                "Hey check out my app at: https://play.google.com/store/apps/details?id=com.google.android.apps.mastermind.contacts_project");
                        sendIntent.setType("text/plain");
                        startActivity(sendIntent);
                        break;






                }

                drawerLayout.closeDrawers();







                return true;
            }
        });


    }

    private void initizeall()
    {
        toolbar=findViewById(R.id.toolbar);
        fab=findViewById(R.id.fab);
        navigationView=findViewById(R.id.nv);
        drawerLayout=findViewById(R.id.dl);
        recyclerView=findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        recyclerView.setHasFixedSize(true);
        searchView=findViewById(R.id.searchView);
        arrayListName=new ArrayList<>();
        arrayListEmail=new ArrayList<>();
        arrayListPhone=new ArrayList<>();
        arrayListImg=new ArrayList<>();
        arrayListId=new ArrayList<>();
        database=new MyDatabase(this);
        Main3Activity.nameF=new ArrayList<>();
        Main3Activity.phoneF=new ArrayList<>();
        Main3Activity.emailF=new ArrayList<>();
        Main3Activity.imageF=new ArrayList<>();

    }
    public void  settingListView()
    {
        customAdapter=new MyCustomAdapter(this,arrayListName,arrayListPhone,arrayListEmail,arrayListImg);
        recyclerView.setAdapter(customAdapter);
        customAdapter.setOnItemClickm(new MyCustomAdapter.OnClickListemerm() {
            @Override
            public void onItemClickm(int position)
            {
                // Launching Details activity

                Intent intent=new Intent(MainActivity.this,Main3Activity.class);
                intent.putExtra("name",arrayListName.get(position));
                intent.putExtra("phone",arrayListPhone.get(position));
                intent.putExtra("email",arrayListEmail.get(position));
                intent.putExtra("ImageUri",arrayListImg.get(position));
                intent.putExtra("id",arrayListId.get(position));
                startActivity(intent);

            }
        });
        if(arrayListName.size()>0)
        {
            TextView tv=findViewById(R.id.tv);
            tv.setText("");
        }
        customAdapter.notifyDataSetChanged();

    }
    private void requestforPermission()
    {
        ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1);
    }


    public void gettingContacts()
    {
        cursor=database.getContacts();

        while (cursor.moveToNext())
        {   arrayListId.add(cursor.getInt(0));
            arrayListName.add(cursor.getString(1));
            arrayListPhone.add(cursor.getString(2));
            arrayListEmail.add(cursor.getString(3));
            arrayListImg.add(cursor.getString(4));
        }


    }


    public void workingOfSearchView()
    {
        searchView.setIconified(false);
        searchView.setQueryHint("Enter name to find");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {



                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                Cursor cursor=database.getsearchData(newText);
                 arrayListIdD=new ArrayList<>();
                 arrayListNameD=new ArrayList<>();
                arrayListPhoneD=new ArrayList<>();
                arrayListEmailD=new ArrayList<>();
                arrayListImgD=new ArrayList<>();


                while(cursor.moveToNext())
                {
                    arrayListIdD.add(cursor.getInt(0));
                    arrayListNameD.add(cursor.getString(1));
                    arrayListPhoneD.add(cursor.getString(2));
                    arrayListEmailD.add(cursor.getString(3));
                    arrayListImgD.add(cursor.getString(4));
                }
                MyCustomAdapter adapter=new MyCustomAdapter(MainActivity.this,arrayListNameD,arrayListPhoneD,arrayListEmailD,arrayListImgD);

                recyclerView.setAdapter(adapter);
                adapter.setOnItemClickm(new MyCustomAdapter.OnClickListemerm() {
                    @Override
                    public void onItemClickm(int position)
                    {
                        // Launching Details activity
                        Intent intent=new Intent(MainActivity.this,Main3Activity.class);
                        intent.putExtra("name",arrayListNameD.get(position));
                        intent.putExtra("phone",arrayListPhoneD.get(position));
                        intent.putExtra("email",arrayListEmailD.get(position));
                        intent.putExtra("ImageUri",arrayListImgD.get(position));
                        intent.putExtra("id",arrayListIdD.get(position));
                        startActivity(intent);



                    }
                });



                if(arrayListNameD.size()==0)
                {
                    TextView textView=findViewById(R.id.tv);
                    textView.setText("No Contacts Found");
                }
                adapter.notifyDataSetChanged();




                return false;
            }
        });
    }




public void  favlist()
{
    MyDatabase database=new MyDatabase(MainActivity.this);
    Cursor cursor=database.gettingFav();
    if(!(cursor.moveToNext()))
    {
        Toast.makeText(getApplicationContext(),"No Favourates Yet ",Toast.LENGTH_SHORT).show();
        return;
    }
    else
    {while (cursor.moveToNext())
    {

        Main3Activity.nameF.add(cursor.getString(1));
        Main3Activity.phoneF.add(cursor.getString(2));
        Main3Activity.emailF.add(cursor.getString(3));
        Main3Activity.imageF.add(cursor.getString(4));
    }


    }


}

public void restart()
{
    Intent intent=new Intent(MainActivity.this,MainActivity.class);
    startActivity(intent);
    finish();
}

}
