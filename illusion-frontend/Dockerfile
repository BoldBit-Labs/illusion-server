FROM node:20.16.0-alpine as base
WORKDIR /usr/src/app

COPY package*.json ./
RUN npm install --production

COPY . .

EXPOSE 3333
CMD ["npm", "start"]
