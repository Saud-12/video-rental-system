package com.crio.video_rental_system.repository;

import com.crio.video_rental_system.entity.RentalEntity;
import com.crio.video_rental_system.entity.User;
import com.crio.video_rental_system.entity.VideoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RentalRepository extends JpaRepository<RentalEntity,Long> {
    List<RentalEntity> findByUserAndReturnDateIsNull(User user);

    List<RentalEntity> findByUser(User user);

    List<RentalEntity> findByVideo(VideoEntity video);

    Optional<RentalEntity> findByVideoAndReturnDateIsNull(VideoEntity video);

}
