package com.example.contactbookdemo.Utility;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.provider.CallLog;
import android.provider.ContactsContract;
import android.support.v4.app.ActivityCompat;
import android.text.format.DateUtils;

import com.example.contactbookdemo.model.ContactModel;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by android on 12/10/16.
 */

public class FetchContactDetailUtil {

    //Get contact List

    public ArrayList<ContactModel> getContacts(Context context) {

        String phoneNumber = null;
        String email = null;

        Uri CONTENT_URI = ContactsContract.Contacts.CONTENT_URI;
        String _ID = ContactsContract.Contacts._ID;
        String DISPLAY_NAME = ContactsContract.Contacts.DISPLAY_NAME;
        String HAS_PHONE_NUMBER = ContactsContract.Contacts.HAS_PHONE_NUMBER;

        Uri PhoneCONTENT_URI = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
        String Phone_CONTACT_ID = ContactsContract.CommonDataKinds.Phone.CONTACT_ID;
        String NUMBER = ContactsContract.CommonDataKinds.Phone.NUMBER;

        Uri EmailCONTENT_URI = ContactsContract.CommonDataKinds.Email.CONTENT_URI;
        String EmailCONTACT_ID = ContactsContract.CommonDataKinds.Email.CONTACT_ID;
        String DATA = ContactsContract.CommonDataKinds.Email.DATA;

        String PHOTO_URI = ContactsContract.CommonDataKinds.Phone.PHOTO_URI;

        ContentResolver contentResolver = context.getContentResolver();

        Cursor cursor = contentResolver.query(CONTENT_URI, null, null, null, DISPLAY_NAME+" ASC");
        ArrayList<ContactModel> contactDetailList = new ArrayList<>();

        // Loop for every contact in the phone
        if (cursor.getCount() > 0) {

            while (cursor.moveToNext()) {

                ContactModel model = new ContactModel();

                String contact_id = cursor.getString(cursor.getColumnIndex(_ID));
                String name = cursor.getString(cursor.getColumnIndex(DISPLAY_NAME));

                String photo = cursor.getString(cursor.getColumnIndex(PHOTO_URI));

                int hasPhoneNumber = Integer.parseInt(cursor.getString(cursor.getColumnIndex(HAS_PHONE_NUMBER)));

                if (hasPhoneNumber > 0) {
                    model.setContactId(contact_id);
                    model.setName(name);
                    model.setPhoto(photo);

                    // Query and loop for every phone number of the contact
                    ArrayList<String> phoneNumberList = new ArrayList<>();
                    ArrayList<String> lastCallDetailList = new ArrayList<>();

                    Cursor phoneCursor = contentResolver.query(PhoneCONTENT_URI, null, Phone_CONTACT_ID + " = ?", new String[]{contact_id}, null);

                    while (phoneCursor.moveToNext()) {

                        phoneNumber = phoneCursor.getString(phoneCursor.getColumnIndex(NUMBER));
                        phoneNumberList.add(phoneNumber);


                            String lastCallDetail = getCallDetails(context, phoneNumber);
                            lastCallDetailList.add(lastCallDetail);

                    }
                    model.setPhoneNumber(phoneNumberList);
                    model.setLastCallDetailList(lastCallDetailList);

                    phoneCursor.close();

                    // Query and loop for every email of the contact
                    ArrayList<String> emailList = new ArrayList<>();
                    Cursor emailCursor = contentResolver.query(EmailCONTENT_URI, null, EmailCONTACT_ID + " = ?", new String[]{contact_id}, null);

                    while (emailCursor.moveToNext()) {

                        email = emailCursor.getString(emailCursor.getColumnIndex(DATA));
                        emailList.add(email);
                    }

                    model.setEmailAddress(emailList);

                    emailCursor.close();
                    contactDetailList.add(model);
                }

            }

        }

        return contactDetailList;
    }


    // Get Call log Details

    public String getCallDetails(Context context, String numberToSearch) {

        String lastCallDetails ="";
        ContentResolver contentResolver = context.getContentResolver();
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_CALL_LOG) == PackageManager.PERMISSION_GRANTED) {
            String strNumber[] = { numberToSearch };
            Cursor cursor = contentResolver.query(CallLog.Calls.CONTENT_URI,
                    null, CallLog.Calls.NUMBER+" =? " , strNumber, CallLog.Calls.DATE + " DESC");
            if (cursor.getCount() > 0) {
                int number = cursor.getColumnIndex(CallLog.Calls.NUMBER);
                int type = cursor.getColumnIndex(CallLog.Calls.TYPE);
                int date = cursor.getColumnIndex(CallLog.Calls.DATE);
                int duration = cursor.getColumnIndex(CallLog.Calls.DURATION);
                cursor.moveToFirst();

                //while (cursor.moveToNext()) {
                String phNumber = cursor.getString(number);
                String callType = cursor.getString(type);
                String callDate = cursor.getString(date);
                Date callDayTime = new Date(Long.valueOf(callDate));
                String callDuration = cursor.getString(duration);
                String dir = null;
                int dircode = Integer.parseInt(callType);
                switch (dircode) {
                    case CallLog.Calls.OUTGOING_TYPE:
                        dir = "OUTGOING";
                        break;
                    case CallLog.Calls.INCOMING_TYPE:
                        dir = "INCOMING";
                        break;

                    case CallLog.Calls.MISSED_TYPE:
                        dir = "MISSED";
                        break;
                }
                String durationV = "";
                try {
                    int callDurationValue = Integer.parseInt(callDuration);
                    durationV = getDurationString(callDurationValue);
                } catch (Exception e) {
                }

                lastCallDetails = dir + "\n" + DateUtils.getRelativeTimeSpanString(context, Long.valueOf(callDate), true).toString() + "\n" + durationV + " (duration)";
            }


                cursor.close();

            //}
        }

        return lastCallDetails;
    }


    private String getDurationString(int seconds) {

        int hours = seconds / 3600;
        int minutes = (seconds % 3600) / 60;
        seconds = seconds % 60;

        return twoDigitString(hours) + " : " + twoDigitString(minutes) + " : " + twoDigitString(seconds);
    }

    private String twoDigitString(int number) {

        if (number == 0) {
            return "00";
        }

        if (number / 10 == 0) {
            return "0" + number;
        }

        return String.valueOf(number);
    }

}
