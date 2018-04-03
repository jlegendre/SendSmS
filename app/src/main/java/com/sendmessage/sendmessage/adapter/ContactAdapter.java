package com.sendmessage.sendmessage.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.sendmessage.sendmessage.R;
import com.sendmessage.sendmessage.bo.Contact;

import java.util.List;

/**
 * Created by Administrateur on 30/03/2018.
 */

public class ContactAdapter extends ArrayAdapter<Contact> {

    private List<Contact> maliste;
    private Context c;
    private int res;

    public ContactAdapter(@NonNull Context context, int resource, @NonNull List<Contact> objects) {
        super(context, resource, objects);
        c = context;
        maliste = objects;
        res = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        TextView txtContactName;
        TextView txtNumero;

        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(res, parent, false);
        }

        Contact contact = maliste.get(position);

        txtContactName = (TextView) convertView.findViewById(R.id.idContactName);
        txtNumero = (TextView) convertView.findViewById(R.id.idNumero);

        txtContactName.setText(contact.getPrenomContact());
        txtNumero.setText(contact.getNumero());


        return convertView;
    }
}
