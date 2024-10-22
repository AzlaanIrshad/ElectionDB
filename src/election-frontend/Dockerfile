# Stage 1: Development with nodemon (hot reload)
FROM node:18 AS dev

# Set the working directory inside the container
WORKDIR /app

# Copy package.json and package-lock.json
COPY package*.json ./

# Install dependencies
RUN npm install && npm install -g nodemon

# Copy the rest of the application code
COPY . .

# Expose the Vite dev server port
EXPOSE 3000

# Start nodemon for development, watching for changes
CMD ["nodemon", "--watch", "src", "--exec", "npm", "run", "dev"]


