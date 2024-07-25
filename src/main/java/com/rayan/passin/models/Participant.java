package com.rayan.passin.models;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Entity
@Table(name = "tb_participants")
public class Participant implements Serializable {

          @Id
          @GeneratedValue(strategy = GenerationType.UUID)
          private UUID id;

          @Column(name = "name", nullable = false)
          private String name;

          @Column(name = "email", nullable = false, unique = true)
          private String email;

          @ManyToOne
          @JoinColumn(name = "event_id", nullable = false)
          private Event event;

          @CreationTimestamp
          @Column(name = "created_at", nullable = false)
          private LocalDateTime createdAt;

}
