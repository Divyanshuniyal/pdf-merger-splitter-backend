package com.pdfmanager.pdfbackend.repository;

import com.pdfmanager.pdfbackend.model.PdfTask;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PdfTaskRepository extends MongoRepository<PdfTask, String> {
}
