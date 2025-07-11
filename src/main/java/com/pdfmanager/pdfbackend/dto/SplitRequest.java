package com.pdfmanager.pdfbackend.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class SplitRequest {
    private MultipartFile file;
    private int splitAfterPage;  // Split after this page number
}
