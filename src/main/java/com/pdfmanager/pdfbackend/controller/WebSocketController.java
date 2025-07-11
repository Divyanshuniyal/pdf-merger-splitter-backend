package com.pdfmanager.pdfbackend.controller;

import com.pdfmanager.pdfbackend.model.PdfTask;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class WebSocketController {

    private final SimpMessagingTemplate messagingTemplate;

    // You can call this method from your service to notify clients
    public void notifyTaskStatus(PdfTask task) {
        messagingTemplate.convertAndSend("/topic/task-status", task);
    }

    // Optional: receive message from client
    @MessageMapping("/notify")
    public void handleMessage(String message) {
        System.out.println("Received from client: " + message);
    }
}
