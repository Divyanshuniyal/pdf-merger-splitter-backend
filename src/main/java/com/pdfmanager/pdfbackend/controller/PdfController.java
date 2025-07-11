package com.pdfmanager.pdfbackend.controller;

import com.pdfmanager.pdfbackend.dto.MergeRequest;
import com.pdfmanager.pdfbackend.dto.PdfResponse;
import com.pdfmanager.pdfbackend.dto.SplitRequest;
import com.pdfmanager.pdfbackend.service.PdfService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@RestController
@RequestMapping("/api/pdf")
@RequiredArgsConstructor
public class PdfController {

    private final PdfService pdfService;

    @Value("${file.storage.path}")
    private String storagePath;

    @PostMapping("/merge")
    public ResponseEntity<PdfResponse> mergePDFs(@RequestParam("files") List<MultipartFile> files) {
        try {
            MergeRequest request = new MergeRequest();
            request.setFiles(files);
            PdfResponse response = pdfService.mergePDFs(request);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new PdfResponse("Merge failed: " + e.getMessage(), null));
        }
    }

    @PostMapping("/split")
    public ResponseEntity<PdfResponse> splitPDF(
            @RequestParam("file") MultipartFile file,
            @RequestParam("splitAfterPage") int splitAfterPage
    ) {
        try {
            SplitRequest request = new SplitRequest();
            request.setFile(file);
            request.setSplitAfterPage(splitAfterPage);
            PdfResponse response = pdfService.splitPDF(request);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new PdfResponse("Split failed: " + e.getMessage(), null));
        }
    }

    @GetMapping("/download/{fileName}")
    public ResponseEntity<?> downloadFile(@PathVariable String fileName) {
        try {
            Path path = Paths.get(storagePath + fileName);
            byte[] data = Files.readAllBytes(path);
            return ResponseEntity.ok()
                    .header("Content-Disposition", "attachment; filename=" + fileName)
                    .body(data);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}
