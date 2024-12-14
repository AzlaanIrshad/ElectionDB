# Production Stage
FROM node:18 AS build

# Set the working directory
WORKDIR /app

# Copy package.json and package-lock.json
COPY package*.json ./

# Install dependencies
RUN npm install

# Copy the rest of the application
COPY . .

# Build the production files
RUN npm run build

# Serve with NGINX
FROM nginx:alpine AS serve

# Copy the built files to NGINX's default public directory
COPY --from=build /app/dist /usr/share/nginx/html

# Copy custom NGINX config (optioneel)
# COPY nginx.conf /etc/nginx/conf.d/default.conf

# Expose the default NGINX port
EXPOSE 80

# Start NGINX
CMD ["nginx", "-g", "daemon off;"]
