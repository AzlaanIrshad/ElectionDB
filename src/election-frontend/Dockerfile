# Stage 1: Install Dependencies
FROM node:14 AS deps
WORKDIR /app
COPY package*.json ./
RUN npm install

# Stage 2: Build Application
FROM node:14 AS builder
WORKDIR /app
COPY --from=deps /app/node_modules ./node_modules
COPY . .
RUN npm run build  # Make sure you have a build script in package.json

# Final Stage: Serve with Nginx
FROM nginx:alpine
COPY --from=builder /app/dist /usr/share/nginx/html
EXPOSE 80
CMD ["nginx", "-g", "daemon off;"]
