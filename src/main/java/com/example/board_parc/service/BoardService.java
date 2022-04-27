package com.example.board_parc.service;

import com.example.board_parc.domain.Board;
import com.example.board_parc.dto.BoardDeleteDto;
import com.example.board_parc.dto.BoardPostDto;
import com.example.board_parc.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @author TaeWK
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class BoardService {

    private final BoardRepository boardRepository;


    public List<Board> findAllBoards() {
        int cnt = boardRepository.findAll().size();
        log.info(cnt + "개의 게시글을 찾았습니다.");
        return boardRepository.findAll();
    }

    public Board findBoardById(Long id) {
        Board board = boardRepository.findById(id)
                .orElseThrow(RuntimeException::new);

        log.info(id + "번째 게시글을 찾았습니다.");
        return board != null ? board : new Board();
    }


    public Board addBoard(BoardPostDto boardPostDto) {
        Board board = new Board();

        board.setTitle(boardPostDto.getTitle());
        board.setContent(boardPostDto.getContent());
        board.setUsername(boardPostDto.getUsername());
        board.setPassword(boardPostDto.getPassword());

        log.info(boardPostDto.getTitle() + "이 등록되었습니다.");
        return boardRepository.save(board);
    }

    public Board addBoardLike(Long id) {
        Board board = boardRepository.findById(id).orElseThrow(RuntimeException::new);
        board.setLike(board.getLike() + 1);
        log.info(board.getTitle() + " 게시글에 좋아요가 " + board.getLike() +"개 입니다.");
        update_date(id);
        return boardRepository.save(board);
    }

    public Board addBoardUnlike(Long id) {
        Board board = boardRepository.findById(id).orElseThrow(RuntimeException::new);
        board.setUnlike(board.getUnlike() + 1);
        log.info(board.getTitle() + " 게시글에 싫어요가 " + board.getUnlike()+"개 입니다.");
        update_date(id);
        return boardRepository.save(board);
    }

    public void deleteBoard(Long id, BoardDeleteDto boardDeleteDto) {
        boolean flag = boardRepository.findById(id)
                        .orElseThrow(RuntimeException::new)
                                .getPassword().equals(boardDeleteDto.getPassword());

        if(flag == true){
            log.info(id + "번째 게시글이 삭제되었습니다.");
            boardRepository.deleteById(id);
        }else{
            log.info("패스워드가 틀렸습니다.");
        }
    }

    public List<Board> findBoardsByTitle(String title) {

        ArrayList<Board> list = new ArrayList<>();

        boardRepository.findAll().forEach(
                (items) -> {
                    String items_title = items.getTitle();

                    if(items_title.contains(title)){
                        list.add(items);
                    }
                }
        );

        return list;
    }

    public List<Board> findBoardsByLike(Long n) {
        return boardRepository.findBoardsByLikeGreaterThan(n);
    }

    public List<Board> findBoardsByUnlike(Long n){
        return boardRepository.findBoardsByUnlikeLessThan(n);
    }

    public List<Board> findBoardsByLikeUnlike(int likes, int unlikes) {
        return boardRepository.findBoardsByUnlikeLessThanAndLikeGreaterThan(unlikes,likes);
    }

    private void update_date(Long id){
        boardRepository.findById(id)
                .orElseThrow(RuntimeException::new)
                .setUpdated_at(LocalDateTime.now());
    }
}
