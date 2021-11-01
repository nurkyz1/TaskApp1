package kg.geektech.taskapp35.ui.models;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.util.List;

@Entity
public class News implements Serializable {

    @PrimaryKey
    @NonNull
    private  String id;
    private  String title;
    private Long createdAt;
    private String email;
    private  String imageUrl;
    private int view_count;
   //  private List<String> userLikes;
    public int getView_count() {
        return view_count;
    }

    public void setView_count(int view_count) {
        this.view_count = view_count;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public News() {
    }

    public News(String title) {
        this.title = title;
    }

    public Long getCreatedAt() {
        return createdAt;
    }


    public void setCreatedAt(Long createdAt) {
        this.createdAt = createdAt;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public News(String title, Long time) {
        this.title = title;
        this.createdAt = time;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
