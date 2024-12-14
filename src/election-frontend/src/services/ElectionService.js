import config from '../config';

export const electionService = {
    fetchElectionResults,
};

async function fetchElectionResults(year) {
    try {
        const response = await fetch(`${config.apiBaseUrl}/api/election-results/all?years=${year}`);
        if (!response.ok) {
            throw new Error("Ophalen van verkiezingsresultaten mislukt");
        }
        const data = await response.json();
        return data[year] || []; // Return the data for the selected year
    } catch (error) {
        throw error;
    }
}
