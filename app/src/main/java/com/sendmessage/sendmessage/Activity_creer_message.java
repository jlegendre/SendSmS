package com.sendmessage.sendmessage;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.sendmessage.sendmessage.bo.MessageBO;
import com.sendmessage.sendmessage.dao.MessageDao;

public class Activity_creer_message extends AppCompatActivity {

    private MessageDao dao;
    private MessageBO unMessage = new MessageBO();
    private EditText contenuMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creer_message);
        dao = new MessageDao(this);
    }

    @Override
    protected void onResume() {
        super.onResume();

        final FloatingActionButton envoyerVersContact = findViewById(R.id.envoyerVersContact);
        contenuMessage = findViewById(R.id.message);
        final CheckBox preenregistre = findViewById(R.id.preenregistre);
        envoyerVersContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (preenregistre.isChecked()) {
                    unMessage.setContenu(contenuMessage.getText().toString());

                    dao.insert(unMessage);
                }

                Intent intent = new Intent(Activity_creer_message.this, PhoneContactActivity.class);
                intent.putExtra("message", contenuMessage.getText().toString());
                startActivity(intent);

            }
        });


    }
}
