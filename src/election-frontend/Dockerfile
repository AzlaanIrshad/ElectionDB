# Base stage with dependencies
FROM node:14 AS deps
WORKDIR /app
COPY package*.json ./
RUN npm install

# Final build stage with compiled code
FROM node:14
WORKDIR /app
COPY --from=deps /app ./
COPY . .
RUN npm run build