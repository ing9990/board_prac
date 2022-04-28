package com.example.board_parc.repository;

import com.example.board_parc.domain.Board;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author TaeWK
 */

@Repository
public interface BoardRepository extends JpaRepository<Board, Long> {
    List<Board> findBoardsByLikeGreaterThan(int n);

    List<Board> findBoardsByUnlikeLessThan(int n);

    List<Board> findBoardsByUnlikeLessThanAndLikeGreaterThan(int n, int m);

    List<Board> findBoardsByUsername(String username);
}
