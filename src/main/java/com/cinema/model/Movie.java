package com.cinema.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@NoArgsConstructor
public class Movie {
    private Long id;
    @NonNull
    private String title;
    @NonNull
    private String description;
}
