package com.pdfmanager.pdfbackend.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "pdf_tasks")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PdfTask {

    @Id
    private String id;

    private String type; // "MERGE" or "SPLIT"

    private LocalDateTime timestamp;

    private String status; // "SUCCESS", "FAILED", "IN_PROGRESS"

    private String resultUrl; // Download link if successful

    private String errorMessage; // If failed
}
