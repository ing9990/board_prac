package com.example.board_parc.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * @author TaeWK
 */

@Getter
@Setter
public class BoardListViewDto {

    private Long id;

    private String username;

    private String title;

    private String content;

    private int like;

    private int unlike;

    private LocalDateTime created_at;

    private LocalDateTime updated_at;
}
