package com.singtanglab.ktbworld.dto.ticket;

public sealed interface TicketResponse permits TicketResponse.Success, TicketResponse.Fail {
    record Success(String message, TicketData data) implements TicketResponse {
        public record TicketData(Long ticket_id) {}
    }

    record Fail(String error, String message) implements TicketResponse {}
}
