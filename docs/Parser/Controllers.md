### **How to Use the Election Results API**

In this section, I'll guide you on how to interact with the Election Results API in your Spring Boot application, including how to fetch the results based on the available years and how to interpret the response data.

### **1. API Overview**

There are two main endpoints in the `ElectionController`:

- **`/election-results`**: Fetches the total votes per party for a given year or a list of years.
- **`/election-results/all`**: Fetches more detailed results with additional information, including votes per city and breakdown of party votes, along with chart data.

---

### **2. API Endpoint 1: Get Total Votes Per Year**

**Endpoint**:  
`GET /election-results?years={year1},{year2},...`

This endpoint returns the total number of votes per party for the given years.

#### **Request Example**:

```bash
GET /election-results?years=2023,2021
```

#### **Response Example**:

```json
{
  "2023": {
    "Party A": 50000,
    "Party B": 120000,
    "Party C": 90000
  },
  "2021": {
    "Party A": 45000,
    "Party B": 110000,
    "Party C": 85000
  }
}
```

In this example, the response shows the total number of votes per party for both 2023 and 2021.

- The `Party A` received `50,000` votes in 2023 and `45,000` votes in 2021.
- Similarly, other parties' votes are displayed for each year.

---

### **3. API Endpoint 2: Get Filtered Results (With Additional Details)**

**Endpoint**:  
`GET /election-results/all?years={year1},{year2},...`

This endpoint provides a more detailed breakdown of the results, including the total votes per city and a detailed list of votes for each party and candidate, along with chart data for visualization.

#### **Request Example**:

```bash
GET /election-results/all?years=2023
```

#### **Response Example**:

```json
{
  "2023": [
    {
      "cityId": "city1",
      "cityName": "City A",
      "totalVotes": 200000,
      "parties": [
        {
          "partyId": "party1",
          "partyName": "Party A",
          "totalVotes": 100000,
          "candidates": [
            {
              "id": "candidate1",
              "validVotes": 50000
            },
            {
              "id": "candidate2",
              "validVotes": 50000
            }
          ]
        },
        {
          "partyId": "party2",
          "partyName": "Party B",
          "totalVotes": 100000,
          "candidates": [
            {
              "id": "candidate3",
              "validVotes": 70000
            },
            {
              "id": "candidate4",
              "validVotes": 30000
            }
          ]
        }
      ],
      "chartData": {
        "labels": ["Party A", "Party B"],
        "datasets": [
          {
            "label": "Totaal Stemmen",
            "data": [100000, 100000],
            "backgroundColor": ["#06b6d4", "#06b6d4"],
            "borderColor": ["rgba(0,0,0,1)", "rgba(0,0,0,1)"],
            "borderWidth": 1
          }
        ]
      }
    }
  ]
}
```

#### **Response Breakdown**:

- **`cityId`** and **`cityName`**: Each city's results.
- **`totalVotes`**: The total number of votes for all parties in that city.
- **`parties`**: A list of parties, each with:
    - `partyId`: Unique identifier for the party.
    - `partyName`: Name of the party.
    - `totalVotes`: Total votes for that party in the city.
    - **`candidates`**: A list of candidates for the party, showing the valid votes per candidate.
- **`chartData`**: Data for charting the party votes in a visual format.
    - **`labels`**: A list of party names.
    - **`datasets`**: The data itself, showing total votes for each party with additional styling properties like `backgroundColor` and `borderColor`.

---

### **4. How to Use the Data**

Once you retrieve the data from the API, you can use it for various purposes such as:
- **Displaying results**: Show total votes per party and per city.
- **Candidate breakdown**: Display the votes for individual candidates within each party.
- **Chart visualization**: Use the `chartData` to generate visual charts (e.g., bar charts or pie charts) to represent the voting distribution.

Here’s how you can visualize the data using a simple chart (using JavaScript or a frontend framework like Vue):

#### **Example Chart (Frontend Visualization)**:

You can use a charting library (like Chart.js or D3.js) to render the `chartData`:

```javascript
const chartData = {
  labels: ["Party A", "Party B"],
  datasets: [{
    label: "Totaal Stemmen",
    data: [100000, 100000],
    backgroundColor: ["#06b6d4", "#06b6d4"],
    borderColor: ["rgba(0,0,0,1)", "rgba(0,0,0,1)"],
    borderWidth: 1
  }]
};

const ctx = document.getElementById("voteChart").getContext("2d");
const myChart = new Chart(ctx, {
  type: 'bar',
  data: chartData,
  options: {
    scales: {
      x: {
        beginAtZero: true
      }
    }
  }
});
```

This will render a bar chart showing the total votes for each party.

---

### **5. Error Handling**

If the requested year or file is not found, you will receive an error response like this:

#### **Error Response Example**:

```json
{
  "message": "Error: resultatenbestand niet gevonden voor jaar 2024."
}
```

In this case, ensure that the correct files for the requested year are available in the `ParsedJson/{year}` folder.

---

### **Conclusion**

With the `ElectionController` API, you can easily access election results for multiple years, get a detailed breakdown by city and party, and visualize the data in charts. Make sure the necessary files are correctly placed in the `resources/ParsedJson/{year}` folder, and you should be able to fetch, process, and display the results efficiently.