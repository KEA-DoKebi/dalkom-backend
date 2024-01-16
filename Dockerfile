FROM openjdk:11-jdk AS builder
COPY gradlew .
COPY gradle gradle
COPY build.gradle .
COPY settings.gradle .
COPY src src
COPY elastic-apm-agent-1.44.0.jar .
RUN chmod +x ./gradlew
RUN ./gradlew bootJar
FROM openjdk:11-slim
COPY --from=builder build/libs/*.jar dalkom.jar
COPY --from=builder elastic-apm-agent-1.44.0.jar elastic-apm-agent-1.44.0.jar
VOLUME /tmp
ENTRYPOINT ["java", "-jar", "-Dspring.profiles.active=prod", "-javaagent:/elastic-apm-agent-1.44.0.jar", "-Delastic.apm.service_name=msa-user-query", "-Delastic.apm.secret_token=hyUExzAkUlugz8LsPW", "-Delastic.apm.server_url=https://f694429f0917434384e0abfab751507d.apm.us-west-2.aws.cloud.es.io:443", "-Delastic.apm.environment=msa-allways", "-Delastic.apm.application_packages=com.dokebi.dalkom", "msa-user-query.jar"]