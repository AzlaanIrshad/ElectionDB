package com.example.controller;

import com.example.service.SearchService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class SearchControllerTest {

    private SearchService searchService;
    private SearchController searchController;

    @BeforeEach
    void setUp() {
        searchService = mock(SearchService.class);
        searchController = new SearchController(searchService);
    }

    @Test
    void searchParties_success() throws IOException {
        // Arrange
        String query = "partyA";
        List<Map<String, Object>> mockResponse = List.of(
                Map.of("partyName", "Party A", "votes", 123),
                Map.of("partyName", "Party B", "votes", 456)
        );
        when(searchService.searchParties(query)).thenReturn(mockResponse);

        // Act
        ResponseEntity<Object> response = searchController.searchParties(query);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockResponse, response.getBody());
        verify(searchService, times(1)).searchParties(query);
    }

    @Test
    void searchParties_fileNotFound() throws IOException {
        // Arrange
        String query = "partyB";
        when(searchService.searchParties(query)).thenThrow(new IOException());

        // Act
        ResponseEntity<Object> response = searchController.searchParties(query);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Error: resultatenbestand niet gevonden voor 2023.", response.getBody());
        verify(searchService, times(1)).searchParties(query);
    }
}
