import config from "../config";

export const likeService = {

    fetchLikes(threadId) {
        return fetch(`${config.apiBaseUrl}/api/likes/thread/${threadId}`)
            .then(response => {
                if (!response.ok) {
                    throw new Error("Failed to fetch likes");
                }
                return response.json();
            });
    },


    fetchVoteSummary(threadId) {
        return fetch(`${config.apiBaseUrl}/api/likes/thread/${threadId}/summary`)
            .then(response => {
                if (!response.ok) {
                    throw new Error("Failed to fetch vote summary");
                }
                return response.json();
            });
    },

    async addLike(likeData) {
        try {
            const response = await fetch(`${config.apiBaseUrl}/api/likes`, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify(likeData),
            });

            if (!response.ok) {
                throw new Error(`Error: ${response.statusText}`);
            }

            return response;
        } catch (error) {
            throw new Error(`Fout bij het toevoegen van de like: ${error.message}`);
        }
    },
};
