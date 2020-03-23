package com.rimas.explorenepal.Database;


import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.rimas.explorenepal.Interface.BookmarkListDao;
import com.rimas.explorenepal.model.BookmarkList;

@Database(entities={BookmarkList.class},version = 1, exportSchema = false)
public abstract class BookmarkDatabase extends RoomDatabase {

    public abstract BookmarkListDao bookmarkListDao();


}
