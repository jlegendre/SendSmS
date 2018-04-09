package com.sendmessage.sendmessage;

import android.app.AlertDialog;
import android.arch.persistence.room.Room;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.sendmessage.sendmessage.adapter.ContactAdapter;
import com.sendmessage.sendmessage.adapter.MessageAdapter;
import com.sendmessage.sendmessage.bo.Contact;
import com.sendmessage.sendmessage.bo.MessageBO;
import com.sendmessage.sendmessage.dao.AppDatabase;
import com.sendmessage.sendmessage.dao.MessageDao;

import java.util.ArrayList;
import java.util.List;

public class ListeMessageActivity extends AppCompatActivity {

    private AppDatabase db;
    private MessageDao dao;
    private ListView lstMessage;
    private MessageBO messageObject = null;
    private MessageAdapter adapter;
    List<MessageBO> messages;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liste_message);


        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayUseLogoEnabled(true);

        lstMessage = findViewById(R.id.idListeMessage);

        MobileAds.initialize(this,
                "ca-app-pub-9572476281021887~4194978965");

        final AdView adView = (AdView) findViewById(R.id.bannerMessage);
        adView.loadAd(new AdRequest.Builder().build());
        adView.setAdListener(new AdListener() {
            @Override
            public void onAdFailedToLoad(int i) {
                super.onAdFailedToLoad(i);
                System.err.println("Ad failed: " + i);
            }
        });


        //parametre base de données
        db = Room.databaseBuilder(
                getApplicationContext(),
                AppDatabase.class,
                "database.db").build();

        //ajout utilisateur
        dao = db.messageDao();

        new AsyncTask<Void, Void, Void>() {

            @Override
            protected Void doInBackground(Void... params) {
                messages = dao.getAll();
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                if (messages.size() < 1) {
                    Toast.makeText(ListeMessageActivity.this, "Vous n'avez pas encore de messages pré-enregistré", Toast.LENGTH_SHORT).show();
                } else {
                    adapter = new MessageAdapter(ListeMessageActivity.this, R.layout.adapter_message, messages);
                    lstMessage.setAdapter(adapter);
                }
            }
        }.execute();

        FloatingActionButton delete = findViewById(R.id.delete);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {

                AlertDialog diaBox = AskOption();
                if(diaBox != null){
                    diaBox.show();
                }

            }
        });

        FloatingActionButton envoyer_vers_contact = findViewById(R.id.envoyer_vers_contact);
        envoyer_vers_contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(messageObject != null){
                    Intent intent = new Intent(ListeMessageActivity.this, PhoneContactActivity.class);
                    intent.putExtra("message", messageObject.getContenu().toString());
                    startActivity(intent);
                }else{
                    Toast.makeText(v.getContext(), "Vous devez sélectionnez un message.", Toast.LENGTH_SHORT).show();
                }

            }
        });

        lstMessage.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                MessageBO messageBO = (MessageBO) parent.getItemAtPosition(position);

                messageBO.setValidate(! messageBO.isValidate());

                if (messageObject != null && !messageBO.equals(messageObject)) {
                    messageObject.setValidate(! messageObject.isValidate());
                }
                if (messageBO.equals(messageObject)) {
                    messageObject = null;
                } else {
                    messageObject = messageBO;
                }

                adapter.notifyDataSetChanged();
            }
        });
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case android.R.id.home:
                onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }


    private AlertDialog AskOption() {
        if (messageObject != null) {
            final AlertDialog myQuittingDialogBox = new AlertDialog.Builder(this)
                    //set message, title, and icon
                    .setTitle("Delete")
                    .setMessage("Do you want to Delete")
                    .setIcon(R.drawable.ic_delete_forever_black_24dp)

                    .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            new AsyncTask<Void, Void, Void>() {

                                @Override
                                protected Void doInBackground(Void... params) {
                                    dao.delete(messageObject);
                                    messages = dao.getAll();
                                    return null;
                                }

                                @Override
                                protected void onPostExecute(Void aVoid) {
                                    Toast.makeText(ListeMessageActivity.this, "Suppression réussite", Toast.LENGTH_SHORT).show();
                                    adapter.clear();
                                    adapter = new MessageAdapter(ListeMessageActivity.this, R.layout.adapter_message, messages);
                                    lstMessage.setAdapter(adapter);
                                }
                            }.execute();
                        }
                    })
                    .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    }).create();


            return myQuittingDialogBox;
        } else {
            Toast.makeText(this, "Vous devez d'abord sélectionner un élement pour le supprimer.", Toast.LENGTH_SHORT).show();
            return null;
        }


    }

}
