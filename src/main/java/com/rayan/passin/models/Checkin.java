package com.rayan.passin.models;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "tb_check_ins")
public class Checkin implements Serializable {

          @Id
          @GeneratedValue(strategy = GenerationType.UUID)
          private UUID id;

          @CreationTimestamp
          @Column(name = "created_at")
          private LocalDateTime createdAt;

          @OneToOne
          @JoinColumn(name = "participant_id")
          private Participant participant;

}