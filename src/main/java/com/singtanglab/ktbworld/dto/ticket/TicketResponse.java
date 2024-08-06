package com.singtanglab.ktbworld.dto.ticket;

public record TicketResponse(String message, TicketData data) {
    public record TicketData(Long ticket_id) {}

    public TicketResponse(String message, String errorMessage) {
        this(message, new TicketData(null));
    }
}
