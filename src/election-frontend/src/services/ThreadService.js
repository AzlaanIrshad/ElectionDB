import config from "../config";

export const threadService = {
    fetchThreads() {
        return fetch(`${config.apiBaseUrl}/api/threads`)
            .then(response => {
                if (!response.ok) {
                    throw new Error("Failed to fetch threads");
                }
                return response.json();
            });
    },

    fetchThreadData(threadId) {
        return fetch(`${config.apiBaseUrl}/api/threads/${threadId}`)
            .then(response => {
                if (!response.ok) {
                    throw new Error("Failed to fetch thread data");
                }
                return response.json();
            });
    },

    fetchComments(threadId) {
        return fetch(`${config.apiBaseUrl}/api/threads/${threadId}/comments`)
            .then(response => {
                if (!response.ok) {
                    throw new Error("Failed to fetch comments");
                }
                return response.json();
            });
    },

    async createThread(threadData) {
        try {
            const response = await fetch(`${config.apiBaseUrl}/api/threads`, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify(threadData),
            });

            if (!response.ok) {
                throw new Error(`Error: ${response.statusText}`);
            }

            return response;
        } catch (error) {
            throw new Error(`Fout bij het aanmaken van de draad: ${error.message}`);
        }
    },

    async createComment(threadId, commentData) {
        try {
            const response = await fetch(`${config.apiBaseUrl}/api/threads/${threadId}/comments`, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify(commentData),
            });

            if (!response.ok) {
                throw new Error(`Error: ${response.statusText}`);
            }

            return response;
        } catch (error) {
            throw new Error(`Fout bij het maken van de reactie: ${error.message}`);
        }
    },
};
