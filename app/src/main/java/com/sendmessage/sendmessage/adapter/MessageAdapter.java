package com.sendmessage.sendmessage.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.sendmessage.sendmessage.R;
import com.sendmessage.sendmessage.bo.MessageBO;

import java.util.List;

/**
 * Created by Administrateur on 30/03/2018.
 */

public class MessageAdapter extends ArrayAdapter<MessageBO> {

    private List<MessageBO> maliste;
    private Context c;
    private int res;
    ImageView imageViewValidate;
    TextView txtContenu;


    public MessageAdapter(@NonNull Context context, int resource, @NonNull List<MessageBO> objects) {
        super(context, resource, objects);
        c = context;
        maliste = objects;
        res = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {


        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(res, parent, false);
        }

        MessageBO message = maliste.get(position);

        txtContenu = (TextView) convertView.findViewById(R.id.contenu_message);
        imageViewValidate = (ImageView) convertView.findViewById(R.id.validate_message);

        Log.i("taille", String.valueOf(message.getContenu().length()));
        if(message.getContenu().length() > 55){
            txtContenu.setText(message.getContenu().substring(0, 50).concat(" ..."));
        }else{
            txtContenu.setText(message.getContenu());
        }

        imageViewValidate.setVisibility(message.isValidate() ? View.VISIBLE : View.GONE);



        return convertView;
    }
}
