package com.sendmessage.sendmessage;

import android.Manifest;
import android.content.ContentResolver;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Build;
import android.provider.ContactsContract;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.sendmessage.sendmessage.adapter.ContactAdapter;
import com.sendmessage.sendmessage.bo.Contact;

import java.util.ArrayList;
import java.util.List;

public class PhoneContactActivity extends AppCompatActivity {

        // The ListView
    private ListView lstNames;
    private List<Contact> contactList = new ArrayList<Contact>();

    // Request code for READ_CONTACTS. It can be any number > 0.
    private static final int PERMISSIONS_REQUEST_READ_CONTACTS = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_contact);


    }

    @Override
    protected void onResume() {
        super.onResume();

        // Find the list view
        lstNames = findViewById(R.id.idListePhoneContact);
        final FloatingActionButton envoyersms = findViewById(R.id.envoyer_sms);
        showContacts();

        lstNames.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Contact contact = (Contact) parent.getItemAtPosition(position);

                ImageView validate = view.findViewById(R.id.validate);

                if (contactList.contains(contact)) {
                    contactList.remove(contact);
                    validate.setVisibility(View.INVISIBLE);
                } else {
                    contactList.add(contact);
                    validate.setVisibility(View.VISIBLE);
                }
            }
        });

        envoyersms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (Contact contact : contactList) {
                    try{
                        sendSMS(contact.getNumero() , getIntent().getStringExtra("message"));
                    }catch (Exception e){
                        e.getCause();
                    }
                }
            }
        });
    }

    /**
     * Show the contacts in the ListView.
     */
    private void showContacts() {
        // Check the SDK version and whether the permission is already granted or not.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.READ_CONTACTS}, PERMISSIONS_REQUEST_READ_CONTACTS);
            //After this point you wait for callback in onRequestPermissionsResult(int, String[], int[]) overriden method
        } else {
            // Android version is lesser than 6.0 or the permission is already granted.
            List<Contact> contacts = getContactNames();
            ContactAdapter adapter = new ContactAdapter(this, R.layout.adapter_contact, contacts);
            lstNames.setAdapter(adapter);
        }
    }

    private void sendSMS(String phoneNumber, String message) {
        SmsManager sms = SmsManager.getDefault();
        sms.sendTextMessage(phoneNumber, null, message, null, null);


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                           int[] grantResults) {
        if (requestCode == PERMISSIONS_REQUEST_READ_CONTACTS) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission is granted
                showContacts();
            } else {
                Toast.makeText(this, "Until you grant the permission, we canot display the names", Toast.LENGTH_SHORT).show();
            }
        }
    }

    /**
     * Read the name of all the contacts.
     *
     * @return a list of names.
     */
    private List<Contact> getContactNames() {
        List<Contact> contacts = new ArrayList<>();

        // Get the ContentResolver
        ContentResolver cr = getContentResolver();
        // Get the Cursor of all the contacts
        Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI, null,
                null, null, null);
        if (cur.getCount() > 0) {
            while (cur.moveToNext()) {
                Contact pContact = new Contact();
                String id = cur.getString(cur.getColumnIndex(ContactsContract.Contacts._ID));
                String name = cur.getString(cur.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                Log.i("Names", name);
                pContact.setPrenomContact(name);
                if (Integer.parseInt(cur.getString(cur.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))) > 0)
                {
                    // Query phone here. Covered next
                    Cursor phones = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,null,ContactsContract.CommonDataKinds.Phone.CONTACT_ID +" = "+ id,null, null);
                    while (phones.moveToNext()) {
                        String phoneNumber = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                        Log.i("Number", phoneNumber);
                     pContact.setNumero(phoneNumber);
                    }
                    phones.close();
                }
                contacts.add(pContact);
            }

        }

        return contacts;
    }





}
