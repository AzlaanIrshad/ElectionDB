import config from "../config";

export const AdminPageService = {
    fetchUsers() {
        return fetch(`${config.apiBaseUrl}/api/users`)
            .then(response => {
                if (!response.ok) {
                    throw new Error("Failed to fetch users");
                }
                return response.json();
            });
    },

    toggleUserActive(userId, isActive) {
        return fetch(`${config.apiBaseUrl}/api/users/${userId}`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({ active: isActive }),
        }).then(response => {
            if (!response.ok) {
                throw new Error("Failed to update user status");
            }
            return response;
        });
    },

    deleteUser(userId) {
        return fetch(`${config.apiBaseUrl}/api/users/delete/${userId}`, {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json',
            },
        }).then(response => {
            if (!response.ok) {
                throw new Error("Failed to delete user");
            }
            return response;
        });
    },
};
