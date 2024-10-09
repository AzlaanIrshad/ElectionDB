# Use the official Node.js image
FROM node:18 AS build

# Set the working directory inside the container
WORKDIR /app

# Copy package.json and package-lock.json
COPY package*.json ./

# Install dependencies
RUN npm install

# Copy the rest of the application code
COPY . .

# Build the application
RUN npm run build

# Use a server to serve the app
FROM nginx:alpine

# Copy the application to the server
COPY --from=build /app/dist /usr/share/nginx/html

# Expose the port that Nginx runs on
EXPOSE 80

# Start Nginx server
CMD ["nginx", "-g", "daemon off;"]
