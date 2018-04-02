package com.sendmessage.sendmessage.dao;

/**
 * Created by lbouvet on 26/01/2018.
 */

public abstract class MessageContract {
    public static final String TABLE_NAME = " MESSAGE ";
    public static final String COL_ID = " ID ";
    public static final String COL_CONTENU = " CONTENU ";

    public static final String SQL_CREATE_TABLE =
            " CREATE TABLE IF NOT EXISTS "
                    + TABLE_NAME + " ("
                    + COL_ID
                    + " INTEGER PRIMARY KEY AUTOINCREMENT , "
                    + COL_CONTENU
                    + " TEXT )";

    public static final String SQL_DROP_TABLE =
            " DROP TABLE IF EXISTS "
                    + TABLE_NAME;
}
