package com.sendmessage.sendmessage;

import android.arch.persistence.room.Room;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.sendmessage.sendmessage.bo.MessageBO;
import com.sendmessage.sendmessage.dao.AppDatabase;
import com.sendmessage.sendmessage.dao.MessageDao;

public class CreateMessageActivity extends AppCompatActivity {

    private AppDatabase db;
    private MessageDao dao;
    private MessageBO unMessage = new MessageBO();
    private EditText contenuMessage;
    private Switch preenregistre;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creer_message);

        //parametre base de données
        db = Room.databaseBuilder(
                getApplicationContext(),
                AppDatabase.class,
                "database.db").build();

        //ajout utilisateur
        dao = db.messageDao();


        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setIcon(R.drawable.ic_group_work_black_24dp);

        contenuMessage = findViewById(R.id.message);
        preenregistre = findViewById(R.id.preenregistre);

        final FloatingActionButton envoyerVersContact = findViewById(R.id.envoyerVersContact);

        envoyerVersContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {

                unMessage.setContenu(contenuMessage.getText().toString());

                new AsyncTask<Void, Void, Void>() {

                    @Override
                    protected Void doInBackground(Void... params) {
                        if (preenregistre.isChecked()) {
                            dao.insert(unMessage);
                        }
                        return null;
                    }

                    @Override
                    protected void onPostExecute(Void aVoid) {
                        if(isEmpty(contenuMessage)){
                            Toast.makeText(v.getContext(), "Veuillez saisir un message.", Toast.LENGTH_SHORT).show();
                        }else{
                            Intent intent = new Intent(CreateMessageActivity.this, PhoneContactActivity.class);
                            intent.putExtra("message", contenuMessage.getText().toString());
                            startActivity(intent);
                        }

                    }
                }.execute();
            }
        });



    }

    private boolean isEmpty(EditText etText) {
        if (etText.getText().toString().trim().length() > 0)
            return false;

        return true;
    }



    @Override
    protected void onResume() {
        super.onResume();


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
