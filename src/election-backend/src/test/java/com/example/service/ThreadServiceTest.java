package com.example.service;

import com.example.entity.Thread;
import com.example.entity.ThreadComment;
import com.example.entity.User;
import com.example.repository.ThreadRepository;
import com.example.repository.ThreadCommentRepository;
import com.example.util.JwtUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ThreadServiceTest {

    @Mock
    private ThreadRepository threadRepository;

    @Mock
    private ThreadCommentRepository threadCommentRepository;

    @InjectMocks
    private ThreadService threadService;

    private Thread thread;

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
        thread.setCategory("cat1");
    }

    @Test
    void testCreateThread() {
        when(threadRepository.save(thread)).thenReturn(thread);

        Thread createdThread = threadService.createThread(thread);

        assertNotNull(createdThread);
        assertEquals("test thread", createdThread.getTitle());
        verify(threadRepository, times(1)).save(thread);
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
        ThreadComment comment = new ThreadComment();
        comment.setBody("This is a comment");

        when(threadRepository.findById(1L)).thenReturn(Optional.of(thread));
        when(threadCommentRepository.save(comment)).thenReturn(comment);

        ThreadComment createdComment = threadService.createComment(1L, comment);

        assertNotNull(createdComment);
        assertEquals("This is a comment", createdComment.getBody());
        verify(threadRepository, times(1)).findById(1L);
        verify(threadCommentRepository, times(1)).save(comment);
    }

    @Test
    void testCreateComment_ThreadNotFound() {
        ThreadComment comment = new ThreadComment();
        comment.setBody("This is a comment");

        when(threadRepository.findById(1L)).thenReturn(Optional.empty());

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            threadService.createComment(1L, comment);
        });

        assertEquals("Thread not found with id: 1", exception.getMessage());
    }
}
