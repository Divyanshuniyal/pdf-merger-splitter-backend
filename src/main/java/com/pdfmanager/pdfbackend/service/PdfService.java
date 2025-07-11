package com.pdfmanager.pdfbackend.service;

import com.pdfmanager.pdfbackend.dto.MergeRequest;
import com.pdfmanager.pdfbackend.dto.PdfResponse;
import com.pdfmanager.pdfbackend.dto.SplitRequest;
import com.pdfmanager.pdfbackend.model.PdfTask;
import com.pdfmanager.pdfbackend.repository.PdfTaskRepository;
import lombok.RequiredArgsConstructor;
import org.apache.pdfbox.multipdf.PDFMergerUtility;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import jakarta.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PdfService {

    private final PdfTaskRepository pdfTaskRepository;

    @Value("${file.storage.path}")
    private String storagePath;

    @PostConstruct
    public void init() throws IOException {
        Files.createDirectories(Paths.get(storagePath));
    }

    public PdfResponse mergePDFs(MergeRequest request) throws IOException {
        PdfTask task = createTask("MERGE");

        PDFMergerUtility merger = new PDFMergerUtility();
        String fileName = "merged_" + UUID.randomUUID() + ".pdf";
        String filePath = storagePath + fileName;

        merger.setDestinationFileName(filePath);
        for (MultipartFile file : request.getFiles()) {
            merger.addSource(file.getInputStream());
        }

        try {
            merger.mergeDocuments(null);
            return finalizeTask(task, fileName);
        } catch (Exception ex) {
            return failTask(task, ex);
        }
    }

    public PdfResponse splitPDF(SplitRequest request) throws IOException {
        PdfTask task = createTask("SPLIT");

        try (PDDocument originalDoc = PDDocument.load(request.getFile().getInputStream())) {
            PDDocument part1 = new PDDocument();
            int totalPages = originalDoc.getNumberOfPages();
            int splitAfter = request.getSplitAfterPage();

            if (splitAfter <= 0 || splitAfter >= totalPages) {
                throw new IllegalArgumentException("Invalid split page number.");
            }

            for (int i = 0; i < totalPages; i++) {
                if (i <= splitAfter) {
                    part1.addPage(originalDoc.getPage(i));
                }
            }

            String fileName = "split_" + UUID.randomUUID() + "_part1.pdf";
            String filePath = storagePath + fileName;
            part1.save(filePath);
            part1.close();

            return finalizeTask(task, fileName);
        } catch (Exception ex) {
            return failTask(task, ex);
        }
    }

    private PdfTask createTask(String type) {
        return pdfTaskRepository.save(PdfTask.builder()
                .type(type)
                .timestamp(LocalDateTime.now())
                .status("IN_PROGRESS")
                .build());
    }

    private PdfResponse finalizeTask(PdfTask task, String fileName) {
        String url = "/api/pdf/download/" + fileName;
        task.setStatus("SUCCESS");
        task.setResultUrl(url);
        pdfTaskRepository.save(task);
        return new PdfResponse("Operation successful", url);
    }

    private PdfResponse failTask(PdfTask task, Exception ex) {
        task.setStatus("FAILED");
        task.setErrorMessage(ex.getMessage());
        pdfTaskRepository.save(task);
        return new PdfResponse("Operation failed: " + ex.getMessage(), null);
    }
}
