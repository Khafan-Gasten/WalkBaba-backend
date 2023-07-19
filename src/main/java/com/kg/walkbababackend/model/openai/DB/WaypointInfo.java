package com.kg.walkbababackend.model.openai.DB;


import com.kg.walkbababackend.model.openai.DTO.OpenAIRouteDTO;
import com.kg.walkbababackend.model.openai.DTO.WaypointDTO;
import jakarta.persistence.*;

@Entity
public class WaypointInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long waypointID;
    private String waypointName;
    private String waypointDescription;
    private String imageLink;

    public WaypointInfo(WaypointDTO waypoints) {
        this.waypointName = waypoints.name();
        this.waypointDescription = waypoints.description();
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

    public String getImageLink() {
        return imageLink;
    }

    public void setImageLink(String imageLink) {
        this.imageLink = imageLink;
    }
}
