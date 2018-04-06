package com.sendmessage.sendmessage.bo;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by Administrateur on 03/04/2018.
 */

@Entity(tableName = "message")
public class MessageBO  {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo
    private String contenu;

    private boolean validate;

    public MessageBO(String contenu) {
        this.contenu = contenu;
    }

    @Ignore


    public MessageBO() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContenu() {
        return contenu;
    }

    public void setContenu(String contenu) {
        this.contenu = contenu;
    }

    public boolean isValidate() {
        return validate;
    }

    public void setValidate(boolean validate) {
        this.validate = validate;
    }
}