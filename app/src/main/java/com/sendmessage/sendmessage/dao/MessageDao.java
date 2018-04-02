package com.sendmessage.sendmessage.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.sendmessage.sendmessage.bo.MessageBO;

import java.util.ArrayList;
import java.util.List;



public class MessageDao {
    //Représente la connexion.
    private SQLiteDatabase db;
    private GestionBddHelper helper;
    private int instancier = 0;

    public MessageDao(Context context)
    {
        helper = new GestionBddHelper(context);
        db = helper.getWritableDatabase();
    }

    public int update(MessageBO item)
    {
        ContentValues values = new ContentValues();



        values.put(MessageContract.COL_CONTENU, item.getContenu());

        //Where
        String whereClause = MessageContract.COL_ID + " = ? ";

        //Where args
        String[] whereArgs = new String[] {
                String.valueOf(item.getId())
        };

        return db.update(MessageContract.TABLE_NAME,values, whereClause, whereArgs);
    }


    public long insert(MessageBO item)
    {
        ContentValues values = new ContentValues();



        values.put(MessageContract.COL_CONTENU, item.getContenu());


        return db.insert(MessageContract.TABLE_NAME,null,values);
    }

    public void initialiserBDD(){    }

    public MessageBO selectById(String id){
        MessageBO truc =null;
        //Tableau des colonnes
        String[] tableColumns = new String[] {
                MessageContract.COL_ID,
                MessageContract.COL_CONTENU,
        };

        //Where
        String whereClause = MessageContract.COL_ID + " = ? ";

        //Where args
        String[] whereArgs = new String[] {
                id
        };

        Cursor c = db.query(MessageContract.TABLE_NAME,
                tableColumns,
                whereClause,
                whereArgs,
                null,
                null,
                null);

        if(c.moveToFirst()) {
            truc = this.getTruc(c);
        }
        c.close();


        return truc;
    }

    public List<MessageBO> selectAll(){
        List<MessageBO> lst = new ArrayList<>();

        String[] tableColumns = new String[] {
                MessageContract.COL_ID,
                MessageContract.COL_CONTENU,
        };

        Cursor c = db.query(MessageContract.TABLE_NAME,
                tableColumns,
                null,
                null,
                null,
                null,
                null);


        while (c.moveToNext()) {
            MessageBO truc = this.getTruc(c);
            lst.add(truc);
        };

        c.close();

        return lst;
    }

    /**
     *
     * @param c Cursor
     * @return Truc
     */
    private MessageBO getTruc(Cursor c){

        //espace dans les constantes à enlever pour trouver les index des colonnes
        MessageBO truc = new MessageBO(c.getInt(c.getColumnIndex(MessageContract.COL_ID.trim())),
                c.getString(c.getColumnIndex(MessageContract.COL_CONTENU.trim())));
        //int id, String nom, String description, String prix, float envie
        /*
         Truc truc = new Truc(c.getInt(TrucContract.NUM_COL_ID),
                c.getString(TrucContract.NUM_COL_LIBELLE),
                c.getInt(TrucContract.NUM_COL_VALEUR)
        );
         */

        return truc;
    }


    public void close(){
        if(db != null) {
            //on ferme l'accès à la BDD
            db.close();
        }
    }

}

