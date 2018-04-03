package com.sendmessage.sendmessage.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.sendmessage.sendmessage.R;
import com.sendmessage.sendmessage.bo.Contact;
import com.sendmessage.sendmessage.bo.MessageBO;

import java.util.List;

/**
 * Created by Administrateur on 03/04/2018.
 */

public class MessageAdapter extends ArrayAdapter<MessageBO> {
    private List<MessageBO> maliste;
    private Context c;
    private int res;

    public MessageAdapter(@NonNull Context context, int resource, @NonNull List<MessageBO> objects) {
        super(context, resource, objects);
        c = context;
        maliste = objects;
        res = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        TextView txtMessage;


        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(res, parent, false);
        }

        MessageBO message = maliste.get(position);

        //txtMessage = (TextView) convertView.findViewById(R.id.);

        //txtMessage.setText(message.getContenu());
        return convertView;
    }
}
