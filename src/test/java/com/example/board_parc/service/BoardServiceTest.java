package com.example.board_parc.service;

import com.example.board_parc.domain.Board;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author TaeWK
 */

@SpringBootTest
class BoardServiceTest {


    @Test
    @DisplayName("무슨 테스트입니다.")
    void test01(){


    }

    private Board givenBoard(){
        Board board = new Board();
        board.setUsername("신욱진");
        board.setTitle("코딩 잘하는 ㅂ아법");
        board.setContent("열심히 하면된다/");
        board.
    }
}