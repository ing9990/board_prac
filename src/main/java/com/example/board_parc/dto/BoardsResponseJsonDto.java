package com.example.board_parc.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author TaeWK
 */

@Getter
@Setter
public class BoardsResponseJsonDto {
    private String title;
    private String username;
}
