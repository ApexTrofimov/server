package ru.trofimov.server.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.trofimov.server.domain.ContentEntity;
import ru.trofimov.server.domain.UserEntity;
import ru.trofimov.server.repository.ContentRepository;
import ru.trofimov.server.repository.UserRepository;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final ContentRepository contentRepository;

    public UserEntity findUserById(String userId) {
        return userRepository.findUserById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User", userId));
    }

    public Optional<UserEntity> findUserByLogin(String userId) {
        return userRepository.findUserByLogin(userId);
    }

    public void addContent(String userId, String contentId) {
        ContentEntity contentEntity = contentRepository.findProductById(contentId)
                .orElseThrow(() -> new EntityNotFoundException("Content", contentId));

        UserEntity userEntity = userRepository.findUserById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User", userId));

        userEntity.getContents().add(contentEntity);
        userRepository.save(userEntity);
    }

    public void removeContent(String userId, String id) {
        UserEntity userEntity = userRepository.findUserById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User", userId));

        Set<ContentEntity> contents = userEntity.getContents().stream()
                .filter(contentEntity -> !id.equals(contentEntity.getId()))
                .collect(Collectors.toSet());

        userEntity.setContents(contents);
        userRepository.save(userEntity);
    }
}
