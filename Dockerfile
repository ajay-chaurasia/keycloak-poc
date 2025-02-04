FROM openjdk:23
WORKDIR /app

COPY .mvn/ .mvn
COPY mvnw pom.xml ./

RUN ./mvnw dependency:go-offline

COPY src ./src

COPY certs/client-keystore.jks /app
COPY certs/client-truststore.jks /app

ENV JWT_SET_URI=https://192.168.1.7:8443/realms/myrealm/protocol/openid-connect/certs
ENV ISSUER_URI=https://192.168.1.7:8443/realms/myrealm
ENV AUTH_URI=https://192.168.1.7:8443/realms/myrealm/protocol/openid-connect/auth
ENV TOKEN_URI=https://192.168.1.7:8443/realms/myrealm/protocol/openid-connect/token
ENV USER_INFO_URI=https://192.168.1.7:8443/realms/myrealm/protocol/openid-connect/userinfo

ENV CLIENT_ID=spring-keycloak
ENV CLIENT_SECRET=FVoYzCW8iwZE9yxibKFRoh2xt9C9uB5R

ENV KEY_STORE_PATH=certs/client-keystore.jks
ENV KEY_STORE_PASSWORD=password
ENV TRUST_STORE_PATH=certs/client-truststore.jks
ENV TRUST_STORE_PASSWORD=password
