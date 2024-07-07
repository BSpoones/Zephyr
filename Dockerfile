FROM amazoncorretto:21-alpine-jdk
LABEL maintainer="bspoones"
COPY build/libs/Zephyr-0.0.jar /Zephyr/Zephyr-1.0.jar
WORKDIR /Zephyr
ENTRYPOINT ["java", "-jar", "/Zephyr/Zephyr-1.0.jar"]