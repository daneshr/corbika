
FROM java:8-jdk-alpine
RUN adduser -Dh /home/bfwg bfwg
RUN apk update && apk add bash
RUN mkdir /home/bfwg/app
WORKDIR /home/bfwg/app
COPY corbika-0.1.0-SNAPSHOT.jar /home/bfwg/app/corbika-0.1.0-SNAPSHOT.jar
RUN chmod +x /home/bfwg/app/corbika-0.1.0-SNAPSHOT.jar
ENTRYPOINT ["java", "-jar", "/home/bfwg/app/corbika-0.1.0-SNAPSHOT.jar"]

