package com.singtanglab.ktbworld.repository.ticket;

import com.singtanglab.ktbworld.entity.Category;
import com.singtanglab.ktbworld.entity.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {
    @Query("SELECT t FROM Ticket t WHERE t.category = :category AND t.endTime > :now ORDER BY t.createdAt DESC")
    List<Ticket> findAllLaundryTickets(@Param("category") Category category, @Param("now") LocalDateTime now);

    @Query("SELECT t FROM Ticket t WHERE t.category = :category ORDER BY t.createdAt DESC")
    List<Ticket> findAllTicketsByCategory(@Param("category") Category category);

    @Query("SELECT t.machineId FROM Ticket t WHERE t.category = 'LAUNDRY' AND " +
            "((t.startTime <= :endTime AND t.endTime >= :startTime) OR " +
            "(t.startTime <= :endTime AND t.endTime >= :startTime))")
    List<Integer> findBookedMachineIds(@Param("startTime") LocalDateTime startTime, @Param("endTime") LocalDateTime endTime);

    @Query("SELECT t FROM Ticket t WHERE t.category = 'LAUNDRY' AND t.endTime > :now ORDER BY t.createdAt DESC")
    List<Ticket> findActiveLaundryTickets(@Param("now") LocalDateTime now);

    @Query("SELECT t FROM Ticket t WHERE (t.category <> 'LAUNDRY' OR t.endTime > :now) ORDER BY t.createdAt DESC")
    List<Ticket> findAllTickets(@Param("now") LocalDateTime now);
}
