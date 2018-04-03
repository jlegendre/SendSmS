package com.sendmessage.sendmessage;

import android.arch.persistence.room.Room;
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
        getSupportActionBar().setIcon(R.drawable.ic_group_work_black_24dp);

        lstMessage = findViewById(R.id.idListeMessage);

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
                }
                adapter = new MessageAdapter(ListeMessageActivity.this, R.layout.adapter_message, messages);
                lstMessage.setAdapter(adapter);
            }
        }.execute();

        FloatingActionButton delete = findViewById(R.id.delete);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AsyncTask<Void, Void, Void>() {

                    @Override
                    protected Void doInBackground(Void... params) {
                        dao.delete(messageObject);
                        return null;
                    }

                    @Override
                    protected void onPostExecute(Void aVoid) {
                        adapter.clear();
                        adapter.notifyDataSetChanged();
                    }
                }.execute();
            }
        });

        FloatingActionButton envoyer_vers_contact = findViewById(R.id.envoyer_vers_contact);
        envoyer_vers_contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ListeMessageActivity.this, PhoneContactActivity.class);
                intent.putExtra("message", messageObject.getContenu().toString());
                startActivity(intent);
            }
        });

        lstMessage.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                MessageBO messageBO = (MessageBO) parent.getItemAtPosition(position);

                ImageView validate = parent.getChildAt(position).findViewById(R.id.validate_message);

                for (int i=0; i<parent.getChildCount(); i++) {
                    ImageView validate2 = parent.getChildAt(i).findViewById(R.id.validate_message);
                    validate2.setVisibility(View.INVISIBLE);
                }

                messageObject = messageBO;
                validate.setVisibility(View.VISIBLE);
            }
        });
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case android.R.id.home:
                Toast.makeText(this, "HOME", Toast.LENGTH_SHORT).show();
                onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }
}
