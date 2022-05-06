package com.example.board_parc.service;

import com.example.board_parc.domain.Board;
import com.example.board_parc.dto.*;
import com.example.board_parc.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.jni.Local;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Array;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.function.Supplier;

/**
 * @author TaeWK
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class BoardService {

    private final BoardRepository boardRepository;

    private String dateTimeTokenizer(LocalDateTime time1, LocalDateTime time2) {

        String[] now = time1.toString().split("-");

        System.out.println(now[0]);
        System.out.println(now[1]);

        now[2] = now[2].replace("T", "일");
        now[2] = now[2].replaceFirst(":", "시 ");
        now[2] = now[2].replace(":", "분 ");
        now[2] = now[2].replace(".", "초 ");
        now[2] = now[2].substring(0, now[2].length() - 9);

        log.info(now[0] + "년 " + now[1] + "일 " + now[2] + "입니다.");

        return "";
    }

    private void update_date(Long id) {
        boardRepository.findById(id).orElseThrow(RuntimeException::new).setUpdated_at(LocalDateTime.now());
        log.info("업데이트 날짜를 ", LocalDateTime.now() + "로 설정합니다.");
    }

    @Transactional(readOnly = true)
    public List<BoardListViewDto> findAllBoards() {
        ArrayList<BoardListViewDto> list = new ArrayList<>();
        boardRepository.findAll().forEach((x) -> {
            BoardListViewDto boardListViewDto = new BoardListViewDto();
            boardListViewDto.setId(x.getId());
            boardListViewDto.setTitle(x.getTitle());
            boardListViewDto.setContent(x.getContent());
            boardListViewDto.setLike(x.getLike());
            boardListViewDto.setUnlike(x.getUnlike());
            boardListViewDto.setUsername(x.getUsername());

            list.add(boardListViewDto);
        });

        log.info(boardRepository.findAll().size() + "개의 게시글을 찾았습니다.");
        return list;
    }

    @Transactional(readOnly = true)
    public List<Board> findBoardsOrderByUpdate_date() {

        List<Board> boardList = new ArrayList<>();

        boardList = boardRepository.findAll();
        Collections.sort(boardList, Comparator.comparing(Board::getUpdated_at));

        log.info("게시글을 최근 업데이트 순서로 정렬했습니다.");
        return boardList != null ? boardList : new ArrayList<>();
    }

    @Transactional(readOnly = true)
    public List<Board> findBoardsOrderByLike() {
        List<Board> boardList = new ArrayList<>();

        boardList = boardRepository.findAll();

        Collections.sort(boardList, Comparator.comparing(Board::getLike).reversed());

        log.info("게시글을 좋아요 순서대로 조회했습니다.");
        return boardList;
    }


    @Transactional(readOnly = true)
    public Board findBoardById(Long id) {
        Board board = boardRepository.findById(id).orElseThrow(RuntimeException::new);

        log.info(id + "번째 게시글을 찾았습니다.");
        return board != null ? board : new Board();
    }


    @Transactional(readOnly = true)
    public List<Board> findBoardsByLike(int n) {
        List<Board> list = boardRepository.findBoardsByLikeGreaterThan(n);
        log.info("좋아요 " + n + "개 이상인 게시글을 " + list.size() + "개 발견했습니다.");
        return list;
    }


    @Transactional(readOnly = true)
    public List<Board> findBoardsByUnlike(int n) {
        List<Board> list = boardRepository.findBoardsByUnlikeLessThan(n);
        log.info("싫어요가 " + n + "개 미만인 게시글 " + list.size() + "개를 발견했습니다.");
        return list;
    }


    @Transactional(readOnly = true)
    public List<Board> findBoardsByLikeUnlike(int likes, int unlikes) {
        List<Board> list = boardRepository.findBoardsByUnlikeLessThanAndLikeGreaterThan(unlikes, likes);
        log.info("좋아요가 " + likes + "개 보다 많고 " + unlikes + "개 보다 적은 게시글" + list.size() + "개 발견했습니다.");
        return list;
    }

    @Transactional(readOnly = true)
    public List<Board> findBoardsByUsername(String username) {
        List<Board> list = boardRepository.findBoardsByUsername(username);
        log.info(username + "님이 작성한 게시글 " + list.size() + "개 발견했습니다.");
        return list;
    }

    @Transactional(readOnly = true)
    public List<Board> findBoardsByTitle(String title) {
        ArrayList<Board> list = new ArrayList<>();
        boardRepository.findAll().forEach((items) -> {
            String items_title = items.getTitle();

            if (items_title.contains(title)) list.add(items);
        });
        log.info("제목이 [" + title + "]인 게시글" + list.size() + "개를 조회했습니다.");
        return list;
    }


    @Transactional(readOnly = true)
    public List<BoardListViewDto> findAllBoardsDESC() {
        ArrayList<BoardListViewDto> list = new ArrayList<>();

        boardRepository.findAll().forEach((x) -> {
            BoardListViewDto boardListViewDto = new BoardListViewDto();
            boardListViewDto.setId(x.getId());
            boardListViewDto.setTitle(x.getTitle());
            boardListViewDto.setContent(x.getContent());
            boardListViewDto.setLike(x.getLike());
            boardListViewDto.setUnlike(x.getUnlike());
            boardListViewDto.setUsername(x.getUsername());

            list.add(boardListViewDto);
        });

        log.info(boardRepository.findAll().size() + "개의 게시글을 찾았습니다.");
        Collections.sort(list, Comparator.comparing(BoardListViewDto::getTitle).reversed());
        return list;
    }


    @Transactional
    public Board addBoardLike(Long id) {
        Board board = boardRepository.findById(id).orElseThrow(RuntimeException::new);
        board.setLike(board.getLike() + 1);
        log.info(board.getTitle() + " 게시글의 좋아요가 " + board.getLike() + "개로 변경되었습니다.");
        update_date(id);
        return boardRepository.save(board);
    }


    @Transactional
    public Board addBoardUnlike(Long id) {
        Board board = boardRepository.findById(id).orElseThrow(RuntimeException::new);

        if (board.getUnlike() >= 5) {
            log.info("싫어요의 갯수가 최대에 도달했습니다.");
        }

        board.setUnlike(board.getUnlike() + 1);
        log.info(board.getTitle() + " 게시글의 싫어요가 " + board.getUnlike() + "개로 변경되었습니다.");
        update_date(id);
        return boardRepository.save(board);
    }

    @Transactional
    public Board addBoard(BoardPostDto boardPostDto) {
        Board board = new Board();

        board.setTitle(boardPostDto.getTitle());
        board.setContent(boardPostDto.getContent());
        board.setUsername(boardPostDto.getUsername());
        board.setPassword(boardPostDto.getPassword());

        board.setCreated_at(LocalDateTime.now());
        board.setUpdated_at(LocalDateTime.now());

        log.info(boardPostDto.getTitle() + "이 등록되었습니다.");

        return boardRepository.save(board);
    }


    @Transactional
    public void deleteBoard(Long id, BoardDeleteDto boardDeleteDto) {
        boolean flag = boardRepository.findById(id).orElseThrow(RuntimeException::new).getPassword().equals(boardDeleteDto.getPassword());

        if (flag == true) {
            log.info(id + "번째 게시글이 삭제되었습니다.");
            boardRepository.deleteById(id);
        } else {
            log.info("패스워드가 틀렸습니다.");
        }
    }


    @Transactional
    public Board editBoard(BoardEditDto boardEditDto) {

        Board board = boardRepository.findById(boardEditDto.getId()).orElseThrow(RuntimeException::new);

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

    public ResponseEntity<?> findBoardByIdTest(Long test_id) {

        // 게시글 IS NOT NULL : 게시글 찾아오기
        // 게시글 IS NULL : 0번 게시글 주고 Response status 404 NOT FOUND 고정할당.

        Board board = boardRepository.findById(test_id).orElse(boardRepository.findAll().stream().findFirst().get());

        board.setCreated_at(LocalDateTime.now());
        board.setUpdated_at(LocalDateTime.now());

        dateTimeTokenizer(LocalDateTime.now(), LocalDateTime.now());

        log.info(board != null ? test_id + "번 게시글을 Response Entity로 반환하였습니다." : test_id + "번 게시글을 찾지 못하여 가장 마지막 게시글을 반환했습니다.");

        return board != null ? ResponseEntity.ok().body(board) : ResponseEntity.notFound().build();
    }

    public ResponseEntity<?> findBoardByNameTest(String test_name) {
        List<Board> boardList = boardRepository.findBoardsByUsername(test_name);

        System.out.println(
                boardList.size() != 0 ? boardList.size() + "개의 게시글을 조회했습니다."
                        : "게시글이 없습니다."
        );

        if(boardList.size() != 0 ){
            List<BoardsResponseJsonDto> response = new ArrayList<>();
            for(Board board : boardList){
                BoardsResponseJsonDto boards = new BoardsResponseJsonDto();
                boards.setTitle(board.getTitle());
                boards.setUsername(board.getUsername());
                response.add(boards);
            }
        }




        return boardList.size() != 0 ? ResponseEntity.ok().body(boardList) : ResponseEntity.notFound().build();

    }
}
