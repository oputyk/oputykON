package com.example.kamil.otomotonotifier.Data.Daos;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.kamil.otomotonotifier.AdEngine.Models.Searcher;
import com.example.kamil.otomotonotifier.Models.SearcherEntity;

import java.util.List;

@Dao
public interface SearcherEntityDao {
    @Insert(onConflict = 1)
    void addSearcherEntity(SearcherEntity searcherEntity);

    @Query("delete from SearcherEntity")
    void deleteAllSearcherEntities();

    @Query("delete from SearcherEntity where id = :id")
    void deleteSearcherEntity(long id);

    @Query("select * from SearcherEntity")
    List<SearcherEntity> getAllSearcherEntities();

    @Query("select * from SearcherEntity where id= :id")
    SearcherEntity getSearcherEntity(long id);

    @Query("select * from SearcherEntity inner join Client on SearcherEntity.phoneNumber = Client.phoneNumber where Client.phoneNumber = :phoneNumber")
    List<SearcherEntity> getSearchersEntitiesByClientPhoneNumber(String phoneNumber);

    @Update(onConflict = 1)
    void updateSearcherEntity(SearcherEntity searcherEntity);

}
