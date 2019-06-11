package ru.trofimov.server.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.trofimov.server.domain.ContentEntity;
import ru.trofimov.server.repository.ContentRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ContentService {
    private final ContentRepository contentRepository;

    public List<ContentEntity> getContents() {
        return (List<ContentEntity>) contentRepository.findAll();
    }
}
