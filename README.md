# How to launch project

## Preparations
Install docker machine on your local machine using one of proposed guide by this link: https://docs.docker.com/engine/install/

## Launch project

Go to root directory of project in terminal/command line and execute this command
```bash
docker-compose up --build
```
It will pull Keycloak image, build image of project and run it simultaneously in container.
App will be running on 8081 port and Keycloak on 8080.

## Working with project

Download Postman to perform HTTP requests.
Select version for your system and download here https://www.postman.com/downloads/postman-agent/

After starting Postman import collection using this link https://api.postman.com/collections/15826172-a989b383-a314-4aed-9990-56674dac1887?access_key=PMAT-01H7W1HH74ED4XJQ50NJ7DTJE7

`OAuthn2 token` folder commands are responsible for acquiring access token
`Service` folder commands are responsible for requests to Spring Boot app.

To acquire token you need to copy `Get token` request, paste to you browser and perform Sign In process in Keycloak server
Then after successful sign in, copy `code` part from URL.
Paste copied part into `Get access token` request and execute it in Postman.
In response, you will see data about access token that you have acquired.
`access_token` value will be copied to environment variable and will be used in requests to Spring Boot App.