import config from "../config";

export const likeService = {
    async fetchLikes(threadId) {
        try {
            const response = await fetch(`${config.apiBaseUrl}/api/likes/thread/${threadId}`);
            if (!response.ok) {
                throw new Error("Failed to fetch likes");
            }
            return await response.json();
        } catch (err) {
            console.error("Error fetching likes:", err);
            throw err;
        }
    },

    async fetchVoteSummary(threadId) {
        try {
            const response = await fetch(`${config.apiBaseUrl}/api/likes/thread/${threadId}/summary`);
            if (!response.ok) {
                throw new Error("Failed to fetch vote summary");
            }
            return await response.json();
        } catch (err) {
            console.error("Error fetching vote summary:", err);
            throw err;
        }
    },

    async addVote(threadId, voteType, userId) {
        try {
            const response = await fetch(`${config.apiBaseUrl}/api/likes/thread/${threadId}/vote`, {
                method: "POST",
                headers: {
                    "Content-Type": "application/json",
                },
                body: JSON.stringify({ voteType, userId }),
            });

            if (!response.ok) {
                const errorText = await response.text();
                console.error("API Error:", errorText);
                throw new Error(`API responded with status: ${response.status} - ${response.statusText}`);
            }

            return await response.json();
        } catch (err) {
            console.error("Error adding vote:", err);
            throw err;
        }
    },
    async removeVote(threadId, voteType, userId) {
        try {
            const response = await fetch(`${config.apiBaseUrl}/api/likes/thread/${threadId}/vote?voteType=${voteType}&userId=${userId}`, {
                method: "DELETE",
                headers: {
                    "Content-Type": "application/json",
                },
            });

            if (!response.ok) {
                const errorText = await response.text();
                console.error("API Error:", errorText);
                throw new Error(`API responded with status: ${response.status} - ${response.statusText}`);
            }

            return await response.json();
        } catch (err) {
            console.error("Error removing vote:", err);
            throw err;
        }
    }
};