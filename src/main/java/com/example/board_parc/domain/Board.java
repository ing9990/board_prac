package com.example.board_parc.domain;

import com.sun.istack.NotNull;
import lombok.*;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * @author TaeWK
 */
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Board {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(nullable = false)
    private String username;

    @NotNull
    @Column(nullable = false)
    private String title;

    @NotNull
    @Column(nullable = false)
    private String content = "";

    @Column(name="board_like")
    private int like = 0;

    @Column(name="board_unlike")
    private int unlike = 0;

    @Column(name="board_password")
    private String password;

    private LocalDateTime created_at = LocalDateTime.now();

    private LocalDateTime updated_at = LocalDateTime.now();
}
