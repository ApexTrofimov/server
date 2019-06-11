package ru.trofimov.server.service;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.internal.util.collections.Sets;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import ru.trofimov.server.domain.ContentEntity;
import ru.trofimov.server.domain.UserEntity;
import ru.trofimov.server.repository.ContentRepository;
import ru.trofimov.server.repository.UserRepository;

import java.util.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceTest {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ContentRepository contentRepository;
    @Autowired
    private UserService userService;

    @Test
    public void findUserByIdTest() {
        //arrange
        Set<ContentEntity> contents = Sets.newSet(
                ContentEntity.builder().id("1").build(),
                ContentEntity.builder().id("2").build()
        );
        userRepository.save(UserEntity.builder()
                .id("123")
                .login("testuser")
                .contents(contents)
                .build());

        //execute
        UserEntity user = userService.findUserById("123");

        //verify
        Assert.assertEquals("123", user.getId());
        List<ContentEntity> retrievedContents = new ArrayList<>(user.getContents());
        Assert.assertEquals(2, retrievedContents.size());

        retrievedContents.sort(Comparator.comparing(ContentEntity::getId));
        Assert.assertEquals("1", retrievedContents.get(0).getId());
        Assert.assertEquals("2", retrievedContents.get(1).getId());
    }

    @Test(expected = EntityNotFoundException.class)
    public void findUserByIdTest_userNotFound() {
        userService.findUserById("not_existed");
    }

    @Test
    public void findUserByLoginTest() {
        //arrange
        Set<ContentEntity> contents = Sets.newSet(
                ContentEntity.builder().id("1").build(),
                ContentEntity.builder().id("2").build()
        );
        userRepository.save(UserEntity.builder()
                .id("123")
                .login("testuser")
                .contents(contents)
                .build());

        //execute
        UserEntity user = userService.findUserByLogin("testuser").get();

        //verify
        Assert.assertEquals("123", user.getId());
        List<ContentEntity> retrievedContents = new ArrayList<>(user.getContents());
        Assert.assertEquals(2, retrievedContents.size());

        retrievedContents.sort(Comparator.comparing(ContentEntity::getId));
        Assert.assertEquals("1", retrievedContents.get(0).getId());
        Assert.assertEquals("2", retrievedContents.get(1).getId());
    }

    @Test
    public void addContentTest() {
        //arrange
        userRepository.save(UserEntity.builder().id("123").build());
        contentRepository.save(ContentEntity.builder().id("321").build());

        //execute
        userService.addContent("123", "321");

        //verify
        Optional<UserEntity> userEntity = userRepository.findUserById("123");
        Assert.assertEquals(1, userEntity.get().getContents().size());
    }

    @Test(expected = EntityNotFoundException.class)
    public void addContentTest_userNotFound() {
        userService.addContent("not_existed", "not_existed");
    }

    @Test
    public void removeContentTest() {
        //arrange
        Set<ContentEntity> contents = Sets.newSet(
                ContentEntity.builder().id("1").build(),
                ContentEntity.builder().id("2").build()
        );
        userRepository.save(UserEntity.builder()
                .id("123")
                .login("testuser")
                .contents(contents)
                .build());

        //execute
        userService.removeContent("123", "2");

        //verify
        UserEntity user = userService.findUserById("123");
        List<ContentEntity> retrievedContents = new ArrayList<>(user.getContents());
        Assert.assertEquals(1, retrievedContents.size());
        Assert.assertEquals("1", retrievedContents.get(0).getId());
    }
}