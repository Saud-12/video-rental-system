package com.crio.video_rental_system.dto;

import com.crio.video_rental_system.enums.Genre;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VideoDto {
    private Long id;
    private String title;
    private String director;
    private Genre genre;
    private Boolean status;
}
