FROM openjdk:20-jdk-slim-buster
MAINTAINER "Leon Phung"
WORKDIR app
EXPOSE 8080

ARG GIT_BRANCH="local"
ARG GIT_COMMIT="ffffff"

ENV GIT_BRANCH=${GIT_BRANCH}
ENV GIT_COMMIT=${GIT_COMMIT}

LABEL BRANCH=${GIT_BRANCH}
LABEL COMMIT=${GIT_COMMIT}
LABEL PROJECT="graph-creator-backend"

COPY ../build/libs/ .

ENTRYPOINT ["java","-jar","graph-0.0.1-SNAPSHOT.jar"]
