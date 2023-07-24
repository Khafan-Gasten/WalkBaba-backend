package com.kg.walkbababackend.model.openai.DTO.MapsApi;

import com.kg.walkbababackend.model.openai.DB.ExportLink;

public record ExportLinkDTO(String exportMapLink, String exportStartMapLink, String exportEndMapLink) {
    public ExportLinkDTO(ExportLink exportLinks) {
        this(exportLinks.getExportLink(), exportLinks.getStartExportLink(), exportLinks.getEndExportLink());
    }
}
