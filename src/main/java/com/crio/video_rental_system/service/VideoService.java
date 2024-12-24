package com.crio.video_rental_system.service;

import com.crio.video_rental_system.dto.VideoDto;
import com.crio.video_rental_system.entity.VideoEntity;
import com.crio.video_rental_system.exception.ResourceNotFoundException;
import com.crio.video_rental_system.repository.VideoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class VideoService {

    private final VideoRepository videoRepository;
    private final ModelMapper modelMapper;

    public VideoDto createNewVideo(VideoDto videoDto){
        VideoEntity videoEntity=modelMapper.map(videoDto, VideoEntity.class);
        return modelMapper.map(videoRepository.save(videoEntity),VideoDto.class);
    }

    public VideoDto getVideoById(Long id){
        VideoEntity videoEntity=videoRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Video with ID: "+id+" does not exists!"));
        return modelMapper.map(videoEntity, VideoDto.class);
    }

    public List<VideoDto> getAllVideo(){
        return videoRepository.findAll()
                .stream()
                .map(entity->modelMapper.map(entity,VideoDto.class))
                .collect(Collectors.toList());
    }

    public VideoDto updateVideoById(Long id,VideoDto videoDto){
        VideoEntity videoEntity=videoRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Video with ID: "+id+" does not exists!"));
        modelMapper.map(videoDto,videoEntity);
        return modelMapper.map(videoRepository.save(videoEntity),VideoDto.class);
    }

    public Boolean deleteVideoById(Long id){
        if(!videoRepository.existsById(id)){
            throw new ResourceNotFoundException("Video with ID: "+id+" does not exists!");
        }
        videoRepository.deleteById(id);
        return true;
    }
}
