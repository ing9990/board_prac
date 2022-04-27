package com.example.board_parc.controller;

import com.example.board_parc.domain.Board;
import com.example.board_parc.dto.BoardDeleteDto;
import com.example.board_parc.dto.BoardPostDto;
import com.example.board_parc.service.BoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author TaeWK
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class BoardController {

    private final BoardService boardService;

    @GetMapping("")
    public List<Board> findAllBoards(){
        return boardService.findAllBoards();
    }

    @GetMapping("/{id}")
    public Board findBoardById(@PathVariable Long id){
        return boardService.findBoardById(id);
    }

    @GetMapping("/search/{title}")
    public List<Board> findBoardsByTitle(@PathVariable String title){
        return boardService.findBoardsByTitle(title);
    }

    @GetMapping("/like/{n}")
    public List<Board> findBoardsByLike(@PathVariable Long n){
        return boardService.findBoardsByLike(n);
    }

    @GetMapping("/unlike/{n}")
    public List<Board> findBoardsByUnlike(@PathVariable Long n){
        return boardService.findBoardsByUnlike(n);
    }

    @GetMapping("/search/{likes}/{unlikes}")
    public List<Board> findBoardsByLikeUnlike(@PathVariable int likes,int unlikes){
        return boardService.findBoardsByLikeUnlike(likes,unlikes);
    }

    @PostMapping("/write")
    public Board addBoard(@RequestBody BoardPostDto boardPostDto){
        return boardService.addBoard(boardPostDto);
    }

    @PutMapping("/{id}/up")
    public Board addLikeBoard(@PathVariable Long id){
        return boardService.addBoardLike(id);
    }

    @PutMapping("/{id}/down")
    public Board addUnLikeBoard(@PathVariable Long id){
        return boardService.addBoardUnlike(id);
    }

    @DeleteMapping("/{id}")
    public void deleteBoard(@PathVariable Long id, @RequestBody BoardDeleteDto boardDeleteDto){
        boardService.deleteBoard(id,boardDeleteDto);
    }



}
















