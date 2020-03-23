package com.rimas.explorenepal.Interface;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.rimas.explorenepal.model.BookmarkList;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface BookmarkListDao {

    @Insert
    public void addTobookmarkdata(BookmarkList bookmarkList);

    @Query("SELECT * FROM bookmark")
    public List<BookmarkList> getBookmarkListData();

    @Query("SELECT EXISTS (SELECT 1 FROM bookmark WHERE id=:id)")
    public int isWish(int id);

    @Delete
    public  void delete(BookmarkList bookmarkList);



}