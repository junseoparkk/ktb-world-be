package com.singtanglab.ktbworld.controller;

import com.singtanglab.ktbworld.dto.ticket.TicketRequest;
import com.singtanglab.ktbworld.dto.ticket.TicketResponse;
import com.singtanglab.ktbworld.service.ticket.TicketService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/tickets")
@RequiredArgsConstructor
public class TicketController {
    private final TicketService ticketService;

    @PostMapping
    public ResponseEntity<TicketResponse> createTicket(@RequestBody TicketRequest request) {
        try {
            TicketResponse response = ticketService.createTicket(request);
            return ResponseEntity.status(201).body(response);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(new TicketResponse("TICKET_CREATED_FAIL", e.getMessage()));
        }
    }
}
