import config from "../config";

export const partyService = {
    fetchPartyData(partyId, selectedYear) {
        return fetch(`${config.apiBaseUrl}/api/party-result/${partyId}?years=${selectedYear}`)
            .then(response => {
                if (!response.ok) {
                    throw new Error("Failed to fetch party data");
                }
                return response.json();
            })
            .then(data => {
                return transformPartyData(data);
            });
    }
};

function transformPartyData(data) {
    const years = Object.keys(data);
    if (years.length === 0) return null;

    const partyData = data[years[0]];
    return {
        partyName: partyData.partyName,
        totalVotes: partyData.totalVotes,
        description: partyData.description,
        candidates: partyData.candidates || [],
        chartDataForCandidates: prepareChartData(partyData.candidates || [])
    };
}

function prepareChartData(candidates) {
    if (!candidates || candidates.length === 0) return null;

    const topCandidates = candidates
        .sort((a, b) => b.validVotes - a.validVotes)
        .slice(0, 10);

    const chartLabels = topCandidates.map((candidate) => candidate.name);
    const chartData = topCandidates.map((candidate) => candidate.validVotes);

    return {
        labels: chartLabels,
        datasets: [
            {
                label: "Votes",
                data: chartData,
                backgroundColor: "#06b6d4",
            },
        ],
    };
}
