FROM node:18

WORKDIR /app
COPY package*.json ./
RUN npm install
COPY . .
RUN npm run build
EXPOSE 8001 # Gebruik 8001 voor Oege-server
CMD ["npx", "http-server", "dist", "-p", "8001"]
