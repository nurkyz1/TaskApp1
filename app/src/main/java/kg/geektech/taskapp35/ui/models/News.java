package kg.geektech.taskapp35.ui.models;

import com.tbuonomo.viewpagerdotsindicator.SpringDotsIndicator;

import java.io.Serializable;

public class News implements Serializable {
    private  String title;
    private Long createdAt;
    private String email;
    private  String id;
    private  String imageUri;

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


    public void setCreateAd(Long time) {
        this.createdAt = time;
    }

    public News(String title, Long time) {
        this.title = title;
        this.createdAt = time;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
