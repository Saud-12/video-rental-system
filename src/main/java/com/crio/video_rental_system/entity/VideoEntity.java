package com.crio.video_rental_system.entity;

import com.crio.video_rental_system.enums.Genre;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="video")
public class VideoEntity {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @Column(nullable=false)
    private String title;

    @Column(nullable = false)
    private String director;

    @Column(nullable=false)
    private Genre genre;

    @Column(nullable=false)
    private Boolean status=true;

    @OneToMany(mappedBy = "video",cascade=CascadeType.ALL)
    private List<RentalEntity> rentals;
}