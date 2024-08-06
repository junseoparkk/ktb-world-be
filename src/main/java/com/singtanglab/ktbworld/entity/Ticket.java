package com.singtanglab.ktbworld.entity;

import com.singtanglab.ktbworld.BaseTimeEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "tickets")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Ticket extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ticket_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    private Category category;

    private String status;
    private String title;
    private String description;

    @Column(name = "is_limited")
    private boolean isLimited;

    private int capacity;

    @Column(name = "laundry_color")
    private String laundryColor;

    @Column(name = "is_dry")
    private boolean isDry;

    private String destination;

    @Column(name = "machine_id")
    private int machineId;

    private String account;

    @Column(name = "start_time")
    private LocalDateTime startTime;

    @Column(name = "end_time")
    private LocalDateTime endTime;

    @OneToMany(mappedBy = "ticket", cascade = CascadeType.ALL)
    private List<UserTicket> userTickets = new LinkedList<>();

    // 공통 생성자
    public Ticket(Category category, String status, String title, String description, boolean isLimited,
                  int capacity, String account) {
        this.category = category;
        this.status = status;
        this.title = title;
        this.description = description;
        this.isLimited = isLimited;
        this.capacity = capacity;
        this.account = account;
    }

    // 세탁 생성자
    @Builder
    public Ticket(Category category, String status, String title, String description, boolean isLimited,
                  int capacity, String laundryColor, boolean isDry, int machineId, String account,
                  LocalDateTime startTime,
                  LocalDateTime endTime) {
        this.category = category;
        this.status = status;
        this.title = title;
        this.description = description;
        this.isLimited = isLimited;
        this.capacity = capacity;
        this.laundryColor = laundryColor;
        this.isDry = isDry;
        this.machineId = machineId;
        this.account = account;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    // 택시 생성자
    @Builder
    public Ticket(Category category, String status, String title, String description, boolean isLimited,
                  int capacity, String destination, String account, LocalDateTime startTime) {
        this.category = category;
        this.status = status;
        this.title = title;
        this.description = description;
        this.isLimited = isLimited;
        this.capacity = capacity;
        this.destination = destination;
        this.account = account;
        this.startTime = startTime;
    }
}
