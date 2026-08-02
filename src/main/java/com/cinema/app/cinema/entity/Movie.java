package com.cinema.app.cinema.entity;

import jakarta.persistence.*;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lombok.*;

@Entity
@Table(name = "movie")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Movie {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  @Column(nullable = false)
  private String title;

  @ElementCollection(targetClass = Genre.class)
  @CollectionTable(name = "movie_genre", joinColumns = @JoinColumn(name = "movie_id"))
  @Enumerated(EnumType.STRING)
  @Column(name = "genre")
  @Builder.Default
  private List<Genre> genre = new ArrayList<>();

  @Column(length = 2000)
  private String description;

  private Duration duration;

  @OneToMany(mappedBy = "movie", cascade = CascadeType.ALL, orphanRemoval = true)
  @Builder.Default
  private List<Projection> projections = new ArrayList<>();
}
