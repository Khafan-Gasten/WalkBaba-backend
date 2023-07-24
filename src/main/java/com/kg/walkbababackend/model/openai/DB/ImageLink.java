package com.kg.walkbababackend.model.openai.DB;

import jakarta.persistence.*;

@Entity
public class ImageLink {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long imageLinkID;

    @Column(length = 1000)
    private String imageLink;

    public ImageLink(String imageLink) {
        this.imageLink = imageLink;
    }

    public ImageLink() {

    }

    public String getImageLink() {
        return imageLink;
    }

    public void setImageLink(String imageLink) {
        this.imageLink = imageLink;
    }
}
