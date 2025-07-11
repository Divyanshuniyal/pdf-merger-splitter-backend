package com.pdfmanager.pdfbackend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PdfResponse {
    private String message;
    private String downloadUrl;
}
