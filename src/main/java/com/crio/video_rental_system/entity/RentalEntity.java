package com.crio.video_rental_system.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name="rental")
public class RentalEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="user_id",nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name="video_id",nullable = false)
    private VideoEntity video;

    @Column(name="rental_date",nullable = false)
    private LocalDateTime rentalDate;

    @Column(name="return_date")
    private LocalDateTime returnDate;

    @Column(name="is_active",nullable=false)
    private boolean isActive;

}
