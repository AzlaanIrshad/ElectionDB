import config from "../config";

export const ComparisonService = {
    fetchClosestCities(year) {
        return fetch(`${config.apiBaseUrl}/api/comparison/closest-cities?year=${year}`)
            .then(response => {
                if (!response.ok) {
                    throw new Error(
                        `API Error: ${response.status} - ${response.statusText}`
                    );
                }
                return response.json();
            });
    },

    fetchFarthestCities(year) {
        return fetch(`${config.apiBaseUrl}/api/comparison/farthest-cities?year=${year}`)
            .then(response => {
                if (!response.ok) {
                    throw new Error(
                        `API Error: ${response.status} - ${response.statusText}`
                    );
                }
                return response.json();
            });
    },

    fetchPercentageDeviations(year) {
        return fetch(
            `${config.apiBaseUrl}/api/comparison/city-percentage-deviation?year=${year}`
        ).then(response => {
            if (!response.ok) {
                throw new Error(
                    `API Error: ${response.status} - ${response.statusText}`
                );
            }
            return response.json();
        });
    },
};
