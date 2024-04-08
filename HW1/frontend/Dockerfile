FROM node:20

WORKDIR /app

COPY package.json .
COPY postcss.config.js .
COPY tailwind.config.js .
COPY yarn.lock .

RUN yarn

COPY . .

EXPOSE 3000

CMD ["yarn", "dev"]