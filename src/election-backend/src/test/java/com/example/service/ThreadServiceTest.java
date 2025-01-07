package com.example.service;

import com.example.entity.Thread;
import com.example.entity.ThreadCategory;
import com.example.entity.ThreadComment;
import com.example.entity.User;
import com.example.repository.ThreadCategoryRepository;
import com.example.repository.ThreadCommentRepository;
import com.example.repository.ThreadRepository;
import com.example.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ThreadServiceTest {

    @Mock
    private ThreadRepository threadRepository;

    @Mock
    private ThreadCategoryRepository threadCategoryRepository;

    @Mock
    private  ThreadCommentRepository threadCommentRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private ThreadService threadService;

    private Thread thread;

    private ThreadComment threadcomment;

    @BeforeEach
    void setUp() {
        User dummyUser = new User();
        dummyUser.setId(1L);
        dummyUser.setUsername("googoo");
        dummyUser.setEmail("googoo@example.com");
        dummyUser.setPassword("password");

        thread = new Thread();
        thread.setId(1L);
        thread.setUser(dummyUser);
        thread.setTitle("test thread");
        thread.setBody("This is a test thread");
    }

    @Test
    void testCreateThread() {

        List<String> categoryNames = Arrays.asList("cat1", "cat2");
        String title = "test thread";
        String body = "This is a test thread";
        String date = "2024-12-15 00:00";
        String userEmail = "googoo@example.com";

        Set<ThreadCategory> categories = new HashSet<>();
        ThreadCategory category1 = new ThreadCategory("cat1");
        ThreadCategory category2 = new ThreadCategory("cat2");
        categories.add(category1);
        categories.add(category2);

        when(threadCategoryRepository.findByName("cat1")).thenReturn(Optional.of(category1));
        when(threadCategoryRepository.findByName("cat2")).thenReturn(Optional.of(category2));

        User dummyUser = new User();
        dummyUser.setId(1L);
        dummyUser.setEmail(userEmail);
        when(userRepository.findByEmail(userEmail)).thenReturn(Optional.of(dummyUser));

        when(threadRepository.save(any(Thread.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Thread createdThread = threadService.createThread(title, body, date, categoryNames, userEmail);

        assertNotNull(createdThread);
        assertEquals(title, createdThread.getTitle());
        assertEquals(2, createdThread.getCategories().size());
        verify(threadRepository, times(1)).save(any(Thread.class));
        verify(threadCategoryRepository, times(1)).findByName("cat1");
        verify(threadCategoryRepository, times(1)).findByName("cat2");
        verify(userRepository, times(1)).findByEmail(userEmail);
    }


    @Test
    void testGetThreads() {
        Thread anotherThread = new Thread();
        anotherThread.setId(2L);
        anotherThread.setTitle("another thread");

        when(threadRepository.findAll()).thenReturn(Arrays.asList(thread, anotherThread));

        var threads = threadService.getThreads();

        assertEquals(2, threads.size());
        assertTrue(threads.contains(thread));
        assertTrue(threads.contains(anotherThread));
        verify(threadRepository, times(1)).findAll();
    }

    @Test
    void testGetThreadById() {
        when(threadRepository.findById(1L)).thenReturn(Optional.of(thread));

        Thread fetchedThread = threadService.getThreadById(1L);

        assertNotNull(fetchedThread);
        assertEquals("test thread", fetchedThread.getTitle());
        verify(threadRepository, times(1)).findById(1L);
    }

    @Test
    void testGetThreadById_NotFound() {
        when(threadRepository.findById(1L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            threadService.getThreadById(1L);
        });

        assertEquals("Thread not found with id: 1", exception.getMessage());
    }

    @Test
    void testDeleteThread() {
        when(threadRepository.existsById(1L)).thenReturn(true);

        boolean deleted = threadService.deleteThread(1L);

        assertTrue(deleted);
        verify(threadRepository, times(1)).deleteById(1L);
    }

    @Test
    void testDeleteThread_NotFound() {
        when(threadRepository.existsById(1L)).thenReturn(false);

        boolean deleted = threadService.deleteThread(1L);

        assertFalse(deleted);
        verify(threadRepository, times(0)).deleteById(1L);
    }

    @Test
    void testCreateComment() {

        String body = "This is a comment";
        String date = "2024-12-15 00:01";
        String email = "googoo@example.com";

        User dummyUser = new User();
        dummyUser.setId(1L);
        dummyUser.setUsername("googoo");
        dummyUser.setEmail(email);
        dummyUser.setPassword("dummy");
        when(userRepository.findByEmail(email)).thenReturn(Optional.of(dummyUser));

        Thread thread = new Thread();
        thread.setId(1L);
        when(threadRepository.findById(1L)).thenReturn(Optional.of(thread));

        ThreadComment comment = new ThreadComment();
        comment.setBody(body);
        comment.setDate(date);
        comment.setUser(dummyUser);
        comment.setThread(thread);
        when(threadCommentRepository.save(any(ThreadComment.class))).thenReturn(comment);

        ThreadComment createdComment = threadService.createComment(1L, body, date, email);

        assertNotNull(createdComment);
        assertEquals(body, createdComment.getBody());
        assertEquals(date, createdComment.getDate());
        assertEquals(email, createdComment.getUser().getEmail());
        verify(threadRepository, times(1)).findById(1L);
        verify(threadCommentRepository, times(1)).save(any(ThreadComment.class));
        verify(userRepository, times(1)).findByEmail(email);
    }
}
