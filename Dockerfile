FROM bellsoft/liberica-openjdk-alpine:15

MAINTAINER vlaship@outlook.com

ARG DEPENDENCY=build/dependency
COPY ${DEPENDENCY}/BOOT-INF/lib /app/lib
COPY ${DEPENDENCY}/META-INF /app/META-INF
COPY ${DEPENDENCY}/BOOT-INF/classes /app  

ENV JAVA_OPTS=""

ENTRYPOINT exec java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -cp app:app/lib/* vlaship.price.h2.App

USER 1000

EXPOSE 8090
