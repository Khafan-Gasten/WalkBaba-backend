package com.kg.walkbababackend.model.openai.DB;

import com.kg.walkbababackend.model.openai.DTO.MapsApi.ExportLinkDTO;
import jakarta.persistence.*;

@Entity
public class ExportLink {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="export_link_id")
    private long exportLinkId;
    @Column(length = 1000)
    private String exportLink;
    @Column(length = 1000)
    private String startExportLink;
    @Column(length = 1000)
    private String endExportLink;

    public ExportLink(ExportLinkDTO exportLinkDTO) {
        this.exportLink = exportLinkDTO.exportMapLink();
        this.startExportLink = exportLinkDTO.exportStartMapLink();
        this.endExportLink = exportLinkDTO.exportEndMapLink();
    }

    public ExportLink() {

    }

    public String getExportLink() {
        return exportLink;
    }

    public void setExportLink(String exportLink) {
        this.exportLink = exportLink;
    }

    public String getStartExportLink() {
        return startExportLink;
    }

    public void setStartExportLink(String startExportLink) {
        this.startExportLink = startExportLink;
    }

    public String getEndExportLink() {
        return endExportLink;
    }

    public void setEndExportLink(String endExportLink) {
        this.endExportLink = endExportLink;
    }
}
