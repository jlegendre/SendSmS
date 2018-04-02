package com.sendmessage.sendmessage;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by lbouvet on 26/01/2018.
 */

public class GestionBddHelper extends SQLiteOpenHelper {

    private final static String DATABASE_NAME = "SendMessage.db";
    private final static int DATABASE_VERSION = 1;

    public GestionBddHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(MessageContract.SQL_CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(MessageContract.SQL_DROP_TABLE);
        //Faire les modification version par version
        onCreate(db);
    }
}
