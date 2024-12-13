package com.example.service;

import com.example.entity.Thread;
import com.example.entity.User;
import com.example.repository.ThreadRepository;
import com.example.util.JwtUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ThreadServiceTest {

    @Mock
    private ThreadRepository threadRepository;

    @Mock
    private JwtUtil jwtUtil;

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

        Thread createThread = threadService.createThread(thread);

        assertNotNull(createThread);
        assertEquals("test thread", createThread.getTitle());
        verify(threadRepository, times(1)).save(thread);
    }
}
