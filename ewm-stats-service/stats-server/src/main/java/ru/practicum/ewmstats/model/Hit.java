package ru.practicum.ewmstats.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "stat_storage")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Hit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne
    @JoinColumn(name = "app_id")
    private App app;
    private String uri;
    private String ip;
    private LocalDateTime timestamp;
}
