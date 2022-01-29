FROM node:lts-alpine3.14

WORKDIR /app

COPY package*.json ./

RUN npm install

RUN npm install -g serve

COPY . .

RUN npm run build

EXPOSE 4000

CMD ["serve", "-s", "build", "-p", "4000"]