#!/bin/bash

# Start the backend server on port 8651
cd backend
java -jar election-backend-1.0-SNAPSHOT.jar --server.port=8651 &
BACKEND_PID=$!

# Start the frontend server on port 8652
cd ../frontend
npx http-server -p 8652 &
FRONTEND_PID=$!

# Wait for both processes to terminate
wait $BACKEND_PID $FRONTEND_PID

#chmod +x start-servers.sh
#./start-servers.sh