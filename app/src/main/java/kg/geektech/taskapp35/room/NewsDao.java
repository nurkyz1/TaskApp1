package kg.geektech.taskapp35.room;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import kg.geektech.taskapp35.ui.models.News;
@Dao
public interface NewsDao {
    @Query("SELECT *FROM news ORDER BY createdAt DESC")
    List <News> getAll();

    @Insert
    void insert(News news);
}
