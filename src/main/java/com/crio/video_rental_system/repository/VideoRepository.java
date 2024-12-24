package com.crio.video_rental_system.repository;

import com.crio.video_rental_system.entity.VideoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VideoRepository extends JpaRepository<VideoEntity,Long> {
}
