package com.singtanglab.ktbworld.service.ticket;

import com.singtanglab.ktbworld.dto.ticket.TicketRequest;
import com.singtanglab.ktbworld.dto.ticket.TicketResponse;

public interface TicketService {
    TicketResponse createTicket(TicketRequest request);
}
