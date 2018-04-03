package com.sendmessage.sendmessage;

import android.arch.persistence.room.Room;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.sendmessage.sendmessage.adapter.ContactAdapter;
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
    private List<String> listStrMessages;
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

        listStrMessages = new ArrayList<String>();

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
                if (messages.size() > 0) {
                    for (MessageBO message : messages) {
                        listStrMessages.add(message.getContenu());
                    }
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                if (messages.size() < 1) {
                    Toast.makeText(ListeMessageActivity.this, "Vous n'avez pas encore de messages pré-enregistré", Toast.LENGTH_SHORT).show();
                }
                ArrayAdapter adapter = new ArrayAdapter(ListeMessageActivity.this, android.R.layout.simple_list_item_1, listStrMessages);
                lstMessage.setAdapter(adapter);
            }
        }.execute();
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
