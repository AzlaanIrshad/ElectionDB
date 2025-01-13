package com.example.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class ComparisonServiceTest {

    @InjectMocks
    private ComparisonService comparisonService;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        objectMapper = new ObjectMapper();
    }

    @Test
    void testCalculateOverallVotes() throws Exception {
        String json = """
                [
                  {
                    "count": {
                      "election": {
                        "contests": {
                          "contests": [
                            {
                              "totalVotes": {
                                "selections": [
                                  {
                                    "affiliationIdentifier": {
                                      "registeredName": "Party A"
                                    },
                                    "validVotes": 100
                                  },
                                  {
                                    "affiliationIdentifier": {
                                      "registeredName": "Party B"
                                    },
                                    "validVotes": 50
                                  }
                                ]
                              }
                            }
                          ]
                        }
                      }
                    }
                  }
                ]
                """;

        JsonNode root = objectMapper.readTree(json);
        Map<String, Integer> overallVotes = comparisonService.calculateOverallVotes(root);

        assertEquals(2, overallVotes.size());
        assertEquals(100, overallVotes.get("Party A"));
        assertEquals(50, overallVotes.get("Party B"));
    }

    @Test
    void testCalculateCityVotes() throws Exception {
        String json = """
                {
                  "count": {
                    "election": {
                      "contests": {
                        "contests": [
                          {
                            "totalVotes": {
                              "selections": [
                                {
                                  "affiliationIdentifier": {
                                    "registeredName": "Party A"
                                  },
                                  "validVotes": 80
                                },
                                {
                                  "affiliationIdentifier": {
                                    "registeredName": "Party B"
                                  },
                                  "validVotes": 30
                                }
                              ]
                            }
                          }
                        ]
                      }
                    }
                  }
                }
                """;

        JsonNode transaction = objectMapper.readTree(json);
        Map<String, Integer> cityVotes = comparisonService.calculateCityVotes(transaction);

        assertEquals(2, cityVotes.size());
        assertEquals(80, cityVotes.get("Party A"));
        assertEquals(30, cityVotes.get("Party B"));
    }

    @Test
    void testNormalizeVotes() {
        Map<String, Integer> votes = Map.of("Party A", 100, "Party B", 50);

        Map<String, Double> normalizedVotes = comparisonService.normalizeVotes(votes);

        assertEquals(2, normalizedVotes.size());
        assertEquals(66.67, normalizedVotes.get("Party A"), 0.01);
        assertEquals(33.33, normalizedVotes.get("Party B"), 0.01);
    }

    @Test
    void testCalculateManhattanDistance() {
        Map<String, Double> overallPercentages = Map.of(
                "Party A", 60.0,
                "Party B", 40.0
        );
        Map<String, Double> cityPercentages = Map.of(
                "Party A", 50.0,
                "Party B", 50.0
        );

        double distance = comparisonService.calculateManhattanDistance(overallPercentages, cityPercentages);

        assertEquals(20.0, distance, 0.01);
    }

    @Test
    void testCalculateCityDistances() throws Exception {
        String json = """
                [
                  {
                    "managingAuthority": {
                      "authorityIdentifier": {
                        "value": "City 1"
                      }
                    },
                    "count": {
                      "election": {
                        "contests": {
                          "contests": [
                            {
                              "totalVotes": {
                                "selections": [
                                  {
                                    "affiliationIdentifier": {
                                      "registeredName": "Party A"
                                    },
                                    "validVotes": 70
                                  },
                                  {
                                    "affiliationIdentifier": {
                                      "registeredName": "Party B"
                                    },
                                    "validVotes": 30
                                  }
                                ]
                              }
                            }
                          ]
                        }
                      }
                    }
                  }
                ]
                """;

        JsonNode root = objectMapper.readTree(json);
        Map<String, Integer> overallResults = Map.of("Party A", 100, "Party B", 50);

        List<Map<String, Object>> cityDistances = comparisonService.calculateCityDistances(root, overallResults);

        assertEquals(1, cityDistances.size());
        assertEquals("City 1", cityDistances.get(0).get("cityName"));
        assertEquals(20.0, cityDistances.get(0).get("distance"));
    }

    @Test
    void testCalculateCityPercentageDeviations() throws Exception {
        String json = """
                [
                  {
                    "managingAuthority": {
                      "authorityIdentifier": {
                        "value": "City 1"
                      }
                    },
                    "count": {
                      "election": {
                        "contests": {
                          "contests": [
                            {
                              "totalVotes": {
                                "selections": [
                                  {
                                    "affiliationIdentifier": {
                                      "registeredName": "Party A"
                                    },
                                    "validVotes": 70
                                  },
                                  {
                                    "affiliationIdentifier": {
                                      "registeredName": "Party B"
                                    },
                                    "validVotes": 30
                                  }
                                ]
                              }
                            }
                          ]
                        }
                      }
                    }
                  }
                ]
                """;

        JsonNode root = objectMapper.readTree(json);
        Map<String, Integer> overallResults = Map.of("Party A", 100, "Party B", 50);

        List<Map<String, Object>> cityDeviations = comparisonService.calculateCityPercentageDeviations(root, overallResults);

        assertEquals(1, cityDeviations.size());
        assertEquals("City 1", cityDeviations.get(0).get("cityName"));
        assertEquals(20.0, cityDeviations.get(0).get("percentageDeviation"));
    }
}
