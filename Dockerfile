FROM openjdk:11-jdk-slim


ARG GRADLE_VERSION=7.6
RUN apt-get update && apt-get install -y wget unzip \
    libglfw3 \
    libglfw3-dev \
    libx11-dev \
    libxi-dev \
    libxrandr-dev \
    libxinerama-dev \
    libxcursor-dev \
    && wget https://services.gradle.org/distributions/gradle-${GRADLE_VERSION}-bin.zip -P /tmp \
    && unzip -d /opt/gradle /tmp/gradle-${GRADLE_VERSION}-bin.zip \
    && rm /tmp/gradle-${GRADLE_VERSION}-bin.zip
ENV GRADLE_HOME /opt/gradle/gradle-${GRADLE_VERSION}
ENV PATH $PATH:$GRADLE_HOME/bin


WORKDIR /app

COPY . .

RUN gradle build

CMD ["gradle", "run"]
