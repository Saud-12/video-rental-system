package com.crio.video_rental_system.dto;

import com.crio.video_rental_system.entity.User;
import com.crio.video_rental_system.entity.VideoEntity;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RentalDto {
    private Long id;

    private Long userId;

    private Long videoId;

    private LocalDateTime rentalDate;

    private LocalDateTime returnDate;

    private boolean isActive;
}
