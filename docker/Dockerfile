FROM openjdk:8-jdk-alpine
VOLUME /tmp
EXPOSE 8888
ARG JAR_FILE
COPY ${JAR_FILE} app.jar

ADD entry.sh entry.sh
ENTRYPOINT ["/entry.sh"]