package com.example.kamil.otomotonotifier;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;
import java.util.List;

@Dao
public interface SearcherDao {
    @Insert(onConflict = 1)
    void addSearcher(Searcher searcher);

    @Query("delete from searcher")
    void deleteAllSearchers();

    @Query("delete from searcher where id = :id")
    void deleteSearcher(long id);

    @Query("select * from searcher")
    List<Searcher> getAllSearchers();

    @Query("select * from searcher where id= :id")
    Searcher getSearcher(long id);

    @Update(onConflict = 1)
    void update(Searcher searcher);
}
