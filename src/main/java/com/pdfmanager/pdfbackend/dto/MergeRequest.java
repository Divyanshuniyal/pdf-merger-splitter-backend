package com.pdfmanager.pdfbackend.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
public class MergeRequest {
    private List<MultipartFile> files;
}
