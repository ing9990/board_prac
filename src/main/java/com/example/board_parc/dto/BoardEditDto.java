package com.example.board_parc.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * @author TaeWK
 */

@Getter
@Setter
public class BoardEditDto {

    private Long id;

    private String title;

    private String content;

    private String password;
}
