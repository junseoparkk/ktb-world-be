package com.singtanglab.ktbworld.dto.ticket;

import java.time.LocalDateTime;
import java.util.List;

public record TicketRequest(
        String title,
        String description,
        boolean is_limited,
        int capacity,
        List<Integer> participant_users,
        int creator,
        String category,
        String account,
        // Laundry specific fields
        String laundry_color,
        Boolean is_dry,
        LocalDateTime start_time,
        LocalDateTime end_time,
        // Taxi specific fields
        String destination
) {}
