package com.kg.walkbababackend.model.openai.DB;


import com.kg.walkbababackend.model.openai.DTO.OpenAi.WaypointDTO;
import jakarta.persistence.*;

import java.util.List;

@Entity
public class WaypointInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long waypointID;
    private String waypointName;
    @Column(length = 2000)
    private String waypointDescription;

//    @ElementCollection
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<ImageLink> imageLink;

    public WaypointInfo(WaypointDTO waypoints) {
        this.waypointName = waypoints.name();
        this.waypointDescription = waypoints.description();
        this.imageLink = waypoints.imageLink().stream()
                .map(ImageLink::new)
                .toList();
    }

    public WaypointInfo() {

    }

    public long getWaypointID() {
        return waypointID;
    }

    public void setWaypointID(long waypointID) {
        this.waypointID = waypointID;
    }

    public String getWaypointName() {
        return waypointName;
    }

    public void setWaypointName(String waypointName) {
        this.waypointName = waypointName;
    }

    public String getWaypointDescription() {
        return waypointDescription;
    }

    public void setWaypointDescription(String waypointDescription) {
        this.waypointDescription = waypointDescription;
    }

    public List<ImageLink> getImageLink() {
        return imageLink;
    }

//    public void setImageLink(List<String> imageLink) {
//        this.imageLink = imageLink;
//    }
}
