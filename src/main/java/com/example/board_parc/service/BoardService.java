package com.example.board_parc.service;

import com.example.board_parc.domain.Board;
import com.example.board_parc.dto.BoardDeleteDto;
import com.example.board_parc.dto.BoardEditDto;
import com.example.board_parc.dto.BoardListViewDto;
import com.example.board_parc.dto.BoardPostDto;
import com.example.board_parc.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * @author TaeWK
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class BoardService {

    private final BoardRepository boardRepository;

    private void update_date(Long id) {
        boardRepository.findById(id)
                .orElseThrow(RuntimeException::new)
                .setUpdated_at(LocalDateTime.now());
        log.info("업데이트 날짜를 ", LocalDateTime.now()  +"로 설정합니다.");
    }


    public List<BoardListViewDto> findAllBoards() {
        ArrayList<BoardListViewDto> list = new ArrayList<>();
        boardRepository.findAll().forEach(
                (x) -> {
                    BoardListViewDto boardListViewDto = new BoardListViewDto();
                    boardListViewDto.setId(x.getId());
                    boardListViewDto.setTitle(x.getTitle());
                    boardListViewDto.setContent(x.getContent());
                    boardListViewDto.setLike(x.getLike());
                    boardListViewDto.setUnlike(x.getUnlike());
                    boardListViewDto.setUsername(x.getUsername());

                    list.add(boardListViewDto);
                }
        );

        log.info("오름차 순으로 정렬한 " + boardRepository.findAll().size() + "개의 게시글을 찾았습니다.");
        Collections.sort(list, Comparator.comparing(BoardListViewDto::getTitle));
        return list;
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
        log.info(board.getTitle() + " 게시글에 좋아요가 " + board.getLike() + "개 입니다.");
        update_date(id);
        return boardRepository.save(board);
    }

    public Board addBoardUnlike(Long id) {
        Board board = boardRepository.findById(id).orElseThrow(RuntimeException::new);
        board.setUnlike(board.getUnlike() + 1);
        log.info(board.getTitle() + " 게시글에 싫어요가 " + board.getUnlike() + "개 입니다.");
        update_date(id);
        return boardRepository.save(board);
    }

    public void deleteBoard(Long id, BoardDeleteDto boardDeleteDto) {
        boolean flag = boardRepository.findById(id)
                .orElseThrow(RuntimeException::new)
                .getPassword().equals(boardDeleteDto.getPassword());

        if (flag == true) {
            log.info(id + "번째 게시글이 삭제되었습니다.");
            boardRepository.deleteById(id);
        } else {
            log.info("패스워드가 틀렸습니다.");
        }
    }

    public List<Board> findBoardsByTitle(String title) {
        ArrayList<Board> list = new ArrayList<>();
        boardRepository.findAll().forEach(
                (items) -> {
                    String items_title = items.getTitle();

                    if (items_title.contains(title))
                        list.add(items);
                }
        );
        log.info("제목이 [" + title + "]인 게시글" + list.size() + "개를 조회했습니다.");
        return list;
    }

    public List<Board> findBoardsByLike(int n) {
        List<Board> list = boardRepository.findBoardsByLikeGreaterThan(n);
        log.info("좋아요 " + n +"개 이상인 게시글을 "+ list.size() + "개 발견했습니다.");
        return list;
    }

    public List<Board> findBoardsByUnlike(int n) {
        List<Board> list = boardRepository.findBoardsByUnlikeLessThan(n);
        log.info("싫어요가 " + n +"개 미만인 게시글 " + list.size() +"개를 발견했습니다.");
        return list;
    }

    public List<Board> findBoardsByLikeUnlike(int likes, int unlikes) {
        List<Board> list = boardRepository.findBoardsByUnlikeLessThanAndLikeGreaterThan(unlikes, likes);
        log.info("좋아요가 " + likes +"개 보다 많고 " + unlikes +"개 보다 적은 게시글" + list.size() +"개 발견했습니다.");
        return list;
    }

    public List<Board> findBoardsByUsername(String username) {
        List<Board> list =  boardRepository.findBoardsByUsername(username);
        log.info(username+"님이 작성한 게시글 " +list.size() +"개 발견했습니다.");
        return list;
    }


    public Board editBoard(BoardEditDto boardEditDto) {

        Board board = boardRepository.findById(boardEditDto.getId())
                .orElseThrow(RuntimeException::new);

        if (boardEditDto.getPassword().equals(board.getPassword())) {

            board.setTitle(boardEditDto.getTitle());
            board.setContent(boardEditDto.getContent());

            log.info("게시글 수정 완료했습니다.");
        } else {
            log.info("패스워드가 일치하지 않습니다.");
        }
        update_date(boardEditDto.getId());
        return boardRepository.save(board);
    }


    public List<BoardListViewDto> findAllBoardsDESC() {
        ArrayList<BoardListViewDto> list = new ArrayList<>();

        boardRepository.findAll().forEach(
                (x) -> {
                    BoardListViewDto boardListViewDto = new BoardListViewDto();
                    boardListViewDto.setId(x.getId());
                    boardListViewDto.setTitle(x.getTitle());
                    boardListViewDto.setContent(x.getContent());
                    boardListViewDto.setLike(x.getLike());
                    boardListViewDto.setUnlike(x.getUnlike());
                    boardListViewDto.setUsername(x.getUsername());

                    list.add(boardListViewDto);
                }
        );

        log.info("오름차 순으로 정렬한 " + boardRepository.findAll().size() + "개의 게시글을 찾았습니다.");
        Collections.sort(list, Comparator.comparing(BoardListViewDto::getTitle).reversed());
        return list;
    }
}
