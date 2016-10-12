package com.example.contactbookdemo.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.contactbookdemo.R;
import com.example.contactbookdemo.Utility.FetchContactDetailUtil;
import com.example.contactbookdemo.model.ContactModel;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by android on 12/10/16.
 */

public class ContactsRecyclerAdapter extends RecyclerView.Adapter<ContactsRecyclerAdapter.MyViewHolder> {


    private Context mContext;
    private ArrayList<ContactModel> mContactDetailList;

    public ContactsRecyclerAdapter(Context context, ArrayList<ContactModel> contactDetailModelList){
        mContext = context;
        mContactDetailList =  contactDetailModelList;
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView contactName, contactNumber, contactEmail, contactLastCall;
        public ImageView contactImage;

        public MyViewHolder(View view) {
            super(view);
            contactName = (TextView) view.findViewById(R.id.tv_contact_name);
            contactNumber = (TextView) view.findViewById(R.id.tv_contact_number);
            contactEmail = (TextView) view.findViewById(R.id.tv_contact_email);
            contactImage = (ImageView)view.findViewById(R.id.iv_contact_image);
            contactLastCall = (TextView)view.findViewById(R.id.tv_contact_last_call_time);
        }
    }


    @Override
    public ContactsRecyclerAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.inflate_contacts_list, null);
        MyViewHolder myView = new MyViewHolder(layoutView);
        return myView;
    }

    @Override
    public void onBindViewHolder(ContactsRecyclerAdapter.MyViewHolder holder, int position) {

        holder.contactName.setText(mContactDetailList.get(position).getName());
        //set Phone number
        if(mContactDetailList.get(position).getPhoneNumber()!=null) {
            if (mContactDetailList.get(position).getPhoneNumber().size() > 0) {

                holder.contactNumber.setText(mContactDetailList.get(position).getPhoneNumber().get(0));
            }else {
                holder.contactNumber.setText(mContext.getResources().getString(R.string.no_phone_number));
            }
        }else {
            holder.contactNumber.setText(mContext.getResources().getString(R.string.no_phone_number));
        }

        //set email address
        if(mContactDetailList.get(position).getEmailAddress()!=null) {
            if (mContactDetailList.get(position).getEmailAddress().size() > 0) {

                holder.contactEmail.setText(mContactDetailList.get(position).getEmailAddress().get(0));
            }else{
                holder.contactEmail.setText(mContext.getResources().getString(R.string.no_email_address));
            }
        }else{
            holder.contactEmail.setText(mContext.getResources().getString(R.string.no_email_address));
        }

        //set last call
        if(mContactDetailList.get(position).getLastCallDetailList()!=null) {
            if (mContactDetailList.get(position).getLastCallDetailList().size() > 0) {
                holder.contactLastCall.setText(mContactDetailList.get(position).getLastCallDetailList().get(0));
            }else{
                holder.contactLastCall.setText("");
            }
        }else{
            holder.contactLastCall.setText("");
        }

        //set photo
        String pic = mContactDetailList.get(position).getPhoto();
        if ( pic!= null) {
            System.out.println(Uri.parse(mContactDetailList.get(position).getPhoto()));
            try {
                Bitmap bitmap = MediaStore.Images.Media
                        .getBitmap(mContext.getContentResolver(),
                                Uri.parse(pic));

                holder.contactImage.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else{
            holder.contactImage.setImageResource(R.mipmap.background);
        }

    }

    @Override
    public int getItemCount() {
        return mContactDetailList.size();
    }
}
