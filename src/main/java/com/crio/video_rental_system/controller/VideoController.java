package com.crio.video_rental_system.controller;

import com.crio.video_rental_system.dto.VideoDto;
import com.crio.video_rental_system.service.VideoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/videos")
@RequiredArgsConstructor
public class VideoController {
    private final VideoService videoService;

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping()
    public ResponseEntity<VideoDto> createNewVideo(@RequestBody VideoDto videoDto){
        return new ResponseEntity<>(videoService.createNewVideo(videoDto), HttpStatus.CREATED);
    }

    @GetMapping(path="/{videoId}")
    public ResponseEntity<VideoDto> getVideoById(@PathVariable Long videoId){
        return ResponseEntity.ok(videoService.getVideoById(videoId));
    }

    @GetMapping()
    public ResponseEntity<List<VideoDto>> getAllVideos(){
        return ResponseEntity.ok(videoService.getAllVideo());
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping(path="/{videoId}")
    public ResponseEntity<VideoDto> updateVideoById(@PathVariable Long videoId,@RequestBody VideoDto videoDto){
        return ResponseEntity.ok(videoService.updateVideoById(videoId,videoDto));
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping(path="/{videoId}")
    public ResponseEntity<Boolean> deleteVideoById(@PathVariable Long videoId){
        return ResponseEntity.ok(videoService.deleteVideoById(videoId));
    }
}