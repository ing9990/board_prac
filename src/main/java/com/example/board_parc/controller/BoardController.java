package com.example.board_parc.controller;

import com.example.board_parc.domain.Board;
import com.example.board_parc.dto.*;
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
    public List<BoardListViewDto> findAllBoards(){
        return boardService.findAllBoards();
    }

    @GetMapping("/{id}")
    public Board findBoardById(@PathVariable Long id){
        return boardService.findBoardById(id);
    }

    @GetMapping("/search/title/{title}")
    public List<Board> findBoardsByTitle(@PathVariable String title){
        return boardService.findBoardsByTitle(title);
    }

    @GetMapping("/search/username/{username}")
    public List<Board> findBoardsByUsername(@PathVariable String username){
        return boardService.findBoardsByUsername(username);
    }

    @GetMapping("/like/{n}")
    public List<Board> findBoardsByLike(@PathVariable int n){
        return boardService.findBoardsByLike(n);
    }

    @GetMapping("/unlike/{n}")
    public List<Board> findBoardsByUnlike(@PathVariable int n){
        return boardService.findBoardsByUnlike(n);
    }

    @GetMapping("/search/{likes}/{unlikes}")
    public List<Board> findBoardsByLikeUnlike(@PathVariable int likes, @PathVariable int unlikes){
        return boardService.findBoardsByLikeUnlike(likes,unlikes);
    }

    //-----------------------------------------------------------------------------------------------------------


    @PostMapping("/write")
    public Board addBoard(@RequestBody BoardPostDto boardPostDto){
        return boardService.addBoard(boardPostDto);
    }



    //-----------------------------------------------------------------------------------------------------------



    @PutMapping("/{id}/up")
    public Board addLikeBoard(@PathVariable Long id){
        return boardService.addBoardLike(id);
    }

    @PutMapping("/{id}/down")
    public Board addUnLikeBoard(@PathVariable Long id){
        return boardService.addBoardUnlike(id);
    }

    @PutMapping("")
    public Board editBoard(@RequestBody BoardEditDto boardEditDto){
        return boardService.editBoard(boardEditDto);
    }


    //-----------------------------------------------------------------------------------------------------------

    @DeleteMapping("/{id}")
    public void deleteBoard(@PathVariable Long id, @RequestBody BoardDeleteDto boardDeleteDto){
        boardService.deleteBoard(id,boardDeleteDto);
    }



}
















