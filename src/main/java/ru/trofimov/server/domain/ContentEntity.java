package ru.trofimov.server.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Builder
@Entity
@Table(name = "contents")
@NoArgsConstructor
@AllArgsConstructor
public class ContentEntity {

    @Id
    @Column(name = "id", updatable = false)
    private String id;

    @Column(name = "title")
    private String title;
}
