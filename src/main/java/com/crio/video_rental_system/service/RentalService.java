package com.crio.video_rental_system.service;

import com.crio.video_rental_system.dto.RentalDto;
import com.crio.video_rental_system.entity.RentalEntity;
import com.crio.video_rental_system.entity.User;
import com.crio.video_rental_system.entity.VideoEntity;
import com.crio.video_rental_system.exception.MaxRentalLimitException;
import com.crio.video_rental_system.exception.ResourceNotFoundException;
import com.crio.video_rental_system.exception.UnauthorizedAccessException;
import com.crio.video_rental_system.repository.RentalRepository;
import com.crio.video_rental_system.repository.UserRepository;
import com.crio.video_rental_system.repository.VideoRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RentalService {

    private final RentalRepository rentalRepository;
    private final UserRepository userRepository;
    private final VideoRepository videoRepository;
    private final ModelMapper modelMapper;

    @Transactional
    public RentalDto rentVideo(Long userId, Long videoId){
        User user=userRepository.findById(userId).orElseThrow(()->new ResourceNotFoundException("User with ID: "+userId+" does not exist!"));
        VideoEntity video=videoRepository.findById(videoId).orElseThrow(()->new ResourceNotFoundException("video with ID: "+videoId+" does not exists!"));

        if (user.getRentals().size() >= 2) {
            throw new MaxRentalLimitException("User has already rented two videos");
        }

        RentalEntity rental=RentalEntity.builder()
                .user(user)
                .video(video)
                .rentalDate(LocalDateTime.now())
                .isActive(true)
                .build();
        rental=rentalRepository.save(rental);
        user.getRentals().add(rental);
        video.getRentals().add(rental);
        userRepository.save(user);
        videoRepository.save(video);

        return RentalDto.builder()
                .id(rental.getId())
                .userId(userId)
                .videoId(videoId)
                .rentalDate(rental.getRentalDate())
                .isActive(rental.isActive())
                .build();
    }

    @Transactional
    public void returnVideo(Long userId,Long rentalId){
        User user=userRepository.findById(userId).orElseThrow(()->new ResourceNotFoundException("User with ID: "+userId+" does not exist!"));
        RentalEntity rental=rentalRepository.findById(rentalId).orElseThrow(()->new ResourceNotFoundException("Rental with ID: "+rentalId+" does not exist!"));

        if (!rental.getUser().getId().equals(userId)) {
            throw new UnauthorizedAccessException("User is not authorized to return this rental");
        }

        user.getRentals().remove(rental);
        rental.setActive(false);
        rental.setReturnDate(LocalDateTime.now());

        VideoEntity video=rental.getVideo();
        video.setStatus(true);

        rentalRepository.save(rental);
        userRepository.save(user);
        videoRepository.save(video);
    }


    public List<RentalDto> getUserRentals(Long userId){
        User user=userRepository.findById(userId).orElseThrow(()->new ResourceNotFoundException("User with ID: "+userId+" does not exist!"));
        return user.getRentals()
                .stream()
                .map(entity->{
                    RentalDto rentalDto=modelMapper.map(entity,RentalDto.class);
                    rentalDto.setUserId(entity.getUser().getId());
                    rentalDto.setVideoId(entity.getVideo().getId());
                    return rentalDto;
                })
                .collect(Collectors.toUnmodifiableList());
    }

    public List<RentalDto> getActiveRentals(Long userId){
       return getUserRentals(userId);
    }

    public List<RentalDto> getRentalsByVideo(Long videoId){
        VideoEntity video=videoRepository.findById(videoId).orElseThrow(()->new ResourceNotFoundException("video with ID: "+videoId+" does not exists!"));
        List<RentalEntity> rental=rentalRepository.findByVideo(video);
        return rental
                .stream()
                .map(entity->{
                    RentalDto rentalDto=modelMapper.map(entity,RentalDto.class);
                    rentalDto.setUserId(entity.getUser().getId());
                    rentalDto.setVideoId(entity.getVideo().getId());
                    return rentalDto;
                })
                .collect(Collectors.toUnmodifiableList());
    }

    public List<RentalDto> getAllRentals(){
        return rentalRepository.findAll()
                .stream()
                .map(entity->{
                    RentalDto rentalDto=modelMapper.map(entity,RentalDto.class);
                    rentalDto.setUserId(entity.getUser().getId());
                    rentalDto.setVideoId(entity.getVideo().getId());
                    return rentalDto;
                })
                .collect(Collectors.toUnmodifiableList());
    }

    public void deleteRental(Long rentalId){
        if(!rentalRepository.existsById(rentalId)){
            throw new ResourceNotFoundException("Rental with ID: "+rentalId+" does not exist!");
        }
        rentalRepository.deleteById(rentalId);
    }
}
