import config from "../config";

export const SearchService = {
    search(query) {
        return fetch(`${config.apiBaseUrl}/api/search/2023?query=${encodeURIComponent(query)}`)
            .then(response => {
                if (!response.ok) {
                    throw new Error(`Failed to fetch results: ${response.statusText}`);
                }
                return response.json();
            });
    },
};
