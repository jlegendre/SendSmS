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

public class Activity_creer_message extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creer_message);
    }

    @Override
    protected void onResume() {
        super.onResume();

        final FloatingActionButton envoyerVersContact = findViewById(R.id.envoyerVersContact);
        final EditText contenuMessage = findViewById(R.id.message);
        final CheckBox preenregistre = findViewById(R.id.preenregistre);
        envoyerVersContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (preenregistre.isChecked()) {
                    //todo ajouter insertion bdd
                }

                Intent intent = new Intent(Activity_creer_message.this, PhoneContactActivity.class);
                intent.putExtra("message", contenuMessage.getText().toString());
                startActivity(intent);

            }
        });
    }
}
