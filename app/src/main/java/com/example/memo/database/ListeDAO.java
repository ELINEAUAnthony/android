package com.example.memo.database;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public abstract class ListeDAO {
    @Query("SELECT * FROM Memos")
    public abstract List<ListeDTO> getListeMemos();
    @Query("SELECT COUNT(*) FROM Memos WHERE intitule = :intitule")
    public abstract long countCoursesParIntitule(String intitule);
    @Insert
    public abstract void insert(ListeDTO... Memos);
    @Update
    public abstract void update(ListeDTO... Memos);
    @Delete
    public abstract void delete(ListeDTO... Memos);
}