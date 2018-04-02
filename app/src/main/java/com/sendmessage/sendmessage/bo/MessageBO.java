package com.sendmessage.sendmessage.bo;

public class MessageBO {

    private int id;
    private String contenu;

    public MessageBO() {
    }

    public MessageBO(int id, String contenu) {
        this.id = id;
        this.contenu = contenu;
    }

    public MessageBO(String contenu) {
        this.contenu = contenu;
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

}
