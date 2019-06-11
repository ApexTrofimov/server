package ru.trofimov.server.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table(name = "users")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity {

    @Id
    @Column(name = "id", updatable = false)
    private String id;

    @Column(name = "login")
    private String login;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "users_contents",
            joinColumns = @JoinColumn(name = "userId", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "contentId", referencedColumnName = "id"),
            uniqueConstraints = @UniqueConstraint(columnNames = {"userId", "contentId"}))
    private Set<ContentEntity> contents = new HashSet<>();
}
