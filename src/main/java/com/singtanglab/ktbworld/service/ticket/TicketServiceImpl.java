package com.singtanglab.ktbworld.service.ticket;

import com.singtanglab.ktbworld.dto.ticket.TicketRequest;
import com.singtanglab.ktbworld.dto.ticket.TicketResponse;
import com.singtanglab.ktbworld.entity.Category;
import com.singtanglab.ktbworld.entity.Ticket;
import com.singtanglab.ktbworld.entity.User;
import com.singtanglab.ktbworld.entity.UserTicket;
import com.singtanglab.ktbworld.repository.ticket.TicketRepository;
import com.singtanglab.ktbworld.repository.user.UserRepository;
import com.singtanglab.ktbworld.repository.userTicket.UserTicketRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class TicketServiceImpl implements TicketService {
    private final TicketRepository ticketRepository;
    private final UserTicketRepository userTicketRepository;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public TicketResponse createTicket(TicketRequest request) {
        try {
            Ticket ticket;
            if (request.category().equalsIgnoreCase("세탁")) {
                int machineId = assignMachineId(request.start_time(), request.end_time());
                if (machineId == -1) {
                    throw new RuntimeException("All washing machines are booked at that time");
                }
                ticket = Ticket.builder()
                        .category(Category.LAUNDRY)
                        .status("대기중")
                        .title(request.title())
                        .description(request.description())
                        .isLimited(request.is_limited())
                        .capacity(request.capacity())
                        .laundryColor(request.laundry_color())
                        .isDry(request.is_dry())
                        .machineId(machineId)
                        .account(request.account())
                        .startTime(request.start_time())
                        .endTime(request.end_time())
                        .build();
            } else if (request.category().equalsIgnoreCase("택시")) {
                ticket = Ticket.builder()
                        .category(Category.TAXI)
                        .status("모집중")
                        .title(request.title())
                        .description(request.description())
                        .isLimited(request.is_limited())
                        .capacity(request.capacity())
                        .destination(request.destination())
                        .account(request.account())
                        .startTime(request.start_time())
                        .build();
            } else {
                ticket = new Ticket(
                        Category.valueOf("GONGGU"),
                        "모집중",
                        request.title(),
                        request.description(),
                        request.is_limited(),
                        request.capacity(),
                        request.account()
                );
            }

            Ticket savedTicket = ticketRepository.save(ticket);

            for (Integer userId : request.participant_users()) {
                User user = userRepository.findById(userId)
                        .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));
                UserTicket userTicket = UserTicket.builder()
                        .user(user)
                        .ticket(savedTicket)
                        .build();
                userTicketRepository.save(userTicket);
            }

            return new TicketResponse("TICKET_CREATED_SUCCESS", new TicketResponse.TicketData(savedTicket.getId()));
        } catch (Exception e) {
            log.error("Exception during ticket creation: {}", e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }

    private int assignMachineId(LocalDateTime startTime, LocalDateTime endTime) {
        List<Integer> bookedMachineIds = ticketRepository.findBookedMachineIds(startTime, endTime);
        if (!bookedMachineIds.contains(1)) {
            return 1;
        } else if (!bookedMachineIds.contains(2)) {
            return 2;
        } else {
            return -1;  // 모든 세탁기가 예약된 경우
        }
    }
}
