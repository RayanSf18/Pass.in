package com.rayan.passin.models;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Entity
@Table(name = "tb_events")
public class Event implements Serializable {

          @Id
          @GeneratedValue(strategy = GenerationType.UUID)
          private UUID id;

          @Column(name = "title", nullable = false, unique = true)
          private String title;

          @Column(name = "details", nullable = false)
          private String details;

          @Column(name = "slug", nullable = false)
          private String slug;

          @Column(name = "maximum_attendees", nullable = false)
          private Integer maximumAttendees;

}
