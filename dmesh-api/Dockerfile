FROM openjdk:8

ENV GRADLE_HOME="/opt/gradle"
ENV GRADLE_VERSION="6.7.1"
ENV PATH="${GRADLE_HOME}/bin:${PATH}"

# GRADLE
WORKDIR /opt
ADD "https://services.gradle.org/distributions/gradle-${GRADLE_VERSION}-bin.zip" .
RUN unzip -q gradle*.zip && rm -f gradle*.zip && mv gradle* gradle

# APP
WORKDIR /app
ADD . .
RUN gradle build

CMD java -jar build/libs/*.jar
