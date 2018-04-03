package com.sendmessage.sendmessage.dao;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.sendmessage.sendmessage.bo.MessageBO;

/**
 * Created by Administrateur on 03/04/2018.
 */

@Database(entities = {MessageBO.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract MessageDao messageDao();
}