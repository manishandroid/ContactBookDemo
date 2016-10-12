package com.example.contactbookdemo.fragment;


import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.contactbookdemo.R;
import com.example.contactbookdemo.Utility.FetchContactDetailUtil;
import com.example.contactbookdemo.adapter.ContactsRecyclerAdapter;
import com.example.contactbookdemo.adapter.GridSpacingItemDecoration;
import com.example.contactbookdemo.model.ContactModel;

import java.util.ArrayList;


/**
 * Created by android on 12/10/16.
 */

public class ContactBookFragment extends Fragment {

    private Context mContext;
    private GridLayoutManager lLayout;
    private FetchContactDetailUtil mContactDetailUtils;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mContext = getActivity();

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_contact_detail,
                container, false);

        lLayout = new GridLayoutManager(mContext, 2);

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mContactDetailUtils = new FetchContactDetailUtil();

        RecyclerView rView = (RecyclerView)view.findViewById(R.id.myrecyclerview);
        rView.setHasFixedSize(true);
        rView.setLayoutManager(lLayout);
        rView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));
        ArrayList<ContactModel> list = mContactDetailUtils.getContacts(mContext);

        if(list.size()>0){
            ContactsRecyclerAdapter rcAdapter = new ContactsRecyclerAdapter(mContext,list);
            rView.setAdapter(rcAdapter);
        }else{
             Snackbar.make(view.findViewById(android.R.id.content), getResources().getString(R.string.no_contact), Snackbar.LENGTH_LONG).show();

        }
    }

    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }
}
