package ru.trofimov.server.service;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.internal.util.collections.Sets;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import ru.trofimov.server.domain.ContentEntity;
import ru.trofimov.server.repository.ContentRepository;

import java.util.List;
import java.util.Set;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ContentServiceTest {
    @Autowired
    private ContentRepository contentRepository;
    @Autowired
    private ContentService contentService;

    @Test
    public void getContents() {
        //arrange
        Set<ContentEntity> userContents = Sets.newSet(
                ContentEntity.builder().id("1").title("title1").build(),
                ContentEntity.builder().id("2").title("title2").build()
        );
        contentRepository.saveAll(userContents);

        //execute
        List<ContentEntity> contents = contentService.getContents();

        //verify
        Assert.assertEquals(2, contents.size());
        Assert.assertEquals("1", contents.get(0).getId());
        Assert.assertEquals("title1", contents.get(0).getTitle());
        Assert.assertEquals("2", contents.get(1).getId());
        Assert.assertEquals("title2", contents.get(1).getTitle());
    }
}