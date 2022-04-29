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
@Table()
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(nullable = false, length = 10)
    private String username;

    @NotNull
    @Column(nullable = false, length = 100)
    private String title;

    @NotNull
    @Column(nullable = false, length = 300)
    private String content = "";

    @Column(name = "board_like")
    private int like = 0;

    @Column(name = "board_unlike")
    private int unlike = 0;

    @Column(name = "board_password", length = 20)
    private String password;

    @Column(name = "board_created_at")
    private LocalDateTime created_at = LocalDateTime.now();

    @Column(name = "board_updated_at")
    private LocalDateTime updated_at = LocalDateTime.now();
}
