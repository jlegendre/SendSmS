package com.sendmessage.sendmessage;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by Administrateur on 30/03/2018.
 */

public class Contact implements Serializable{



    private int id;
    private String numero;
    private String nomContact;
    private String prenomContact;

    public Contact(int id, String numero, String nomContact, String prenomContact) {
        this.id = id;
        this.numero = numero;
        this.nomContact = nomContact;
        this.prenomContact = prenomContact;
    }

    public Contact(String numero, String nomContact, String prenomContact) {
        this.numero = numero;
        this.nomContact = nomContact;
        this.prenomContact = prenomContact;
    }

    public Contact() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getNomContact() {
        return nomContact;
    }

    public void setNomContact(String nomContact) {
        this.nomContact = nomContact;
    }

    public String getPrenomContact() {
        return prenomContact;
    }

    public void setPrenomContact(String prenomContact) {
        this.prenomContact = prenomContact;
    }


}
