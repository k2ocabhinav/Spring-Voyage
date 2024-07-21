package com.springvoyage.prod.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PostDTO {
    Long id;

    private String title;
    private String description;

    private LocalDateTime createdAt;


}
