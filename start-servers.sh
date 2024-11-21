#!/bin/bash

# Start the backend server on port 8000
cd backend
java -jar election-backend-1.0-SNAPSHOT.jar --server.port=8000 &
BACKEND_PID=$!

# Start the frontend server on port 8001
cd ../frontend
npx http-server -p 8001 &
FRONTEND_PID=$!

# Wait for both processes to terminate
wait $BACKEND_PID $FRONTEND_PID

#chmod +x start-servers.sh
#./start-servers.sh