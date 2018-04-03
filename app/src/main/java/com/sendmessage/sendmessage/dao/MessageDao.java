package com.sendmessage.sendmessage.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.sendmessage.sendmessage.bo.MessageBO;

import java.util.List;

/**
 * Created by Administrateur on 03/04/2018.
 */

@Dao
public interface MessageDao {

    @Insert
    void insert(MessageBO utilisateur);

    @Insert
    void insertAll(MessageBO... utilisateurs);

    @Delete
    void delete(MessageBO utilisateur);

    @Update
    void update(MessageBO utilisateur);

    @Query("SELECT * FROM message WHERE id = (:id) LIMIT 1")
    MessageBO getById(int id);

    @Query("SELECT * FROM message")
    List<MessageBO> getAll();
}