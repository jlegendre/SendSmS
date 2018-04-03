package com.sendmessage.sendmessage;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.sendmessage.sendmessage.adapter.ContactAdapter;
import com.sendmessage.sendmessage.bo.Contact;
import com.sendmessage.sendmessage.bo.MessageBO;
import com.sendmessage.sendmessage.dao.MessageDao;

import java.util.ArrayList;
import java.util.List;

public class ListeMessageActivity extends AppCompatActivity {

    private MessageDao dao;
    private ListView lstMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liste_message);
        dao = new MessageDao(this);
        lstMessage = findViewById(R.id.idListeMessage);

        List<MessageBO> messages = dao.selectAll();
        List<String> listStrMessages = new ArrayList<String>();
        // On envoie nos contenus de messages dans une
        // liste de string pour pouvoir les envoyés à l'ArrayAdapter
        if(messages.size() >=1){
            for(MessageBO message: messages){
                listStrMessages.add(message.getContenu());
            }
        }else{
            Toast.makeText(this, "Vous n'avez pas encore de messages pré-enregistré", Toast.LENGTH_SHORT).show();
        }

        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1 , listStrMessages);
        lstMessage.setAdapter(adapter);

    }
}
