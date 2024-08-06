package com.singtanglab.ktbworld.repository.userTicket;

import com.singtanglab.ktbworld.entity.UserTicket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserTicketRepository extends JpaRepository<UserTicket, Long> {
}
