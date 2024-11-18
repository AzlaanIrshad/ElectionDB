FROM node:18 AS dev

WORKDIR /app
COPY package*.json ./
RUN npm install && npm install -g nodemon
COPY . .
EXPOSE 3000
CMD ["npm", "run", "dev"]
