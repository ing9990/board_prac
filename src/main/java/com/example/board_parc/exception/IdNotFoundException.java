package com.example.board_parc.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;

/**
 * @author TaeWK
 */

@Slf4j
public class IdNotFoundException extends RuntimeException {

    private static final int ID_NOT_FOUND = 400;

    IdNotFoundException(){
        log.info("ID를 찾을 수 없습니다.");
    }
}
