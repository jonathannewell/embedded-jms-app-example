FROM java:8

MAINTAINER Jon Newell <jnewell@pivotal.io>

VOLUME /tmp

ARG JAR_FILE
ADD ${JAR_FILE} app.jar
RUN bash -c 'touch /app.jar'

ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]
