package com.example.mastermind.contacts_project;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.io.IOException;
import java.util.ArrayList;

public class MyCustomAdapter extends RecyclerView.Adapter<MyCustomAdapter.MyviewHolder>{

    private OnClickListemerm listemerm;

    ArrayList<String> nameA;
    ArrayList<String> phoneA;
    ArrayList<String> emailA;
    ArrayList<String> imageA;
    Context mcontext;

    public interface OnClickListemerm{
        void onItemClickm(int position);
    }


    public void setOnItemClickm(OnClickListemerm listemer)
    {
        listemerm=listemer;
    }

    public MyCustomAdapter(Context context,ArrayList<String> name,ArrayList<String> phone,ArrayList<String> email,ArrayList<String> image)
    {
        mcontext=context;
        nameA=name;
        phoneA=phone;
        emailA=email;
        imageA=image;


    }


    @NonNull
    @Override
    public MyviewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View  view = LayoutInflater.from(mcontext).inflate(R.layout.mycustomlistview, viewGroup, false);
        MyviewHolder holder=new MyviewHolder(view,listemerm);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyviewHolder holder, int position) {

        holder.name.setText(nameA.get(position));
        holder.phone.setText(phoneA.get(position));
        String imgtemp = imageA.get(position);

        Glide.with(mcontext).load(imgtemp).placeholder(R.drawable.ic_android_black_24dp).into(holder.img);



    }

    @Override
    public int getItemCount() {
        return nameA.size();
    }
    public static class MyviewHolder extends RecyclerView.ViewHolder
    {
        public ImageView img;
        TextView name,phone,noContacts;


        public MyviewHolder(@NonNull View convertView, final OnClickListemerm listner) {
            super(convertView);
             img=convertView.findViewById(R.id.img);
             name=convertView.findViewById(R.id.name);
             phone=convertView.findViewById(R.id.phone);
             convertView.setOnClickListener(new View.OnClickListener() {
                 @Override
                 public void onClick(View v) {
                     if(listner!=null)
                     {
                         int position=getAdapterPosition();
                         if(position!=RecyclerView.NO_POSITION)
                         {
                             listner.onItemClickm(position);
                         }
                     }

                 }
             });


        }
    }

}
