const config = {
    apiBaseUrl: window.location.hostname.includes("localhost")
        ? "http://localhost:8080"
        : "http://oege.ie.hva.nl:8000"
};

export default config;
