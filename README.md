# How to work with repository 

This repository contains 5 branches:
  - master
  - task-1-configure-keycloak-example
  - task-2-configure-web-security-example
  - task-3-working-with-spring-security-context-example
  - iam-poc-app

Master branch is starting point for learning.<br/>
task-1-..., task-3-... and task-3-... are branches that contain completed regarding task code examples.<br/>
iam-poc-app is a branch with first complete working example of project.
It has a lot of differences with final variant that you will have during doing tasks, but the logic is the same, just a bit improved.

Before starting doing you tasks you need to fork and clone this project and then do [How to launch project](#How-to-launch-project) instructions. <br/>
Tasks you can find here [Tasks](#Tasks). Do them one by one and if you stuck with some problem go to one of the branches related to the task.

# How to launch project
## Preparations
Install docker machine on your local machine using one of proposed guide by this link: https://docs.docker.com/engine/install/
Download Postman to perform HTTP requests.
Select version for your system and download here https://www.postman.com/downloads/postman-agent/

## Working with project
### How to launch app
Go to root directory of project in terminal/command line and execute this command
```bash
docker-compose up --build
```
It will pull Keycloak image, build image of project and run it simultaneously in container.
App will be running on 8081 port and Keycloak on 8080.

### Interact with app
Start Postman app and import collection using this link https://api.postman.com/collections/15826172-a989b383-a314-4aed-9990-56674dac1887?access_key=PMAT-01H7W1HH74ED4XJQ50NJ7DTJE7

`OAuthn2 token` folder commands are responsible for acquiring access token
`Service` folder commands are responsible for requests to Spring Boot app.

To acquire token you need to copy `Get token` request, paste to you browser and perform Sign In process in Keycloak server
Then after successful sign in, copy `code` part from URL.
Paste copied part into `Get access token` request and execute it in Postman.
In response, you will see data about access token that you have acquired.
`access_token` value will be copied to environment variable and will be used in requests to Spring Boot App.

# Tasks
1. [task-1-configure-keycloak.md](task-1-configure-keycloak.md)
2. [task-2-configure-web-security.md](task-2-configure-web-security.md)
3. [task-3-working-with-spring-security-context.md](task-3-working-with-spring-security-context.md)