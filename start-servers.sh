#!/bin/bash

# Start the backend server on port 8000
cd backend
echo "Starting backend on port 8000..."
java -jar election-backend-1.0-SNAPSHOT.jar --server.port=8000 &
BACKEND_PID=$!
echo "Backend server started with PID $BACKEND_PID"

# Start the frontend server on port 8001
cd ../frontend
echo "Starting frontend on port 8001..."
npx http-server -p 8001 &
FRONTEND_PID=$!
echo "Frontend server started with PID $FRONTEND_PID"

# Wait for both processes to terminate
wait $BACKEND_PID $FRONTEND_PID

echo "Both servers have stopped."

#chmod +x start-servers.sh
#./start-servers.sh