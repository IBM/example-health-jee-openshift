FROM openliberty/open-liberty:javaee8-ubi-min

ENV INSTALL_DIR /opt/ol/wlp/
ENV CONFIG_DIR /opt/ol/wlp/usr/servers/defaultServer/

ADD --chown=default:root https://repo1.maven.org/maven2/mysql/mysql-connector-java/8.0.16/mysql-connector-java-8.0.16.jar  ${INSTALL_DIR}lib/mysql-connector-java-8.0.16.jar
RUN chmod 644 ${INSTALL_DIR}lib/mysql-connector-java-8.0.16.jar
COPY liberty-mysql/mysql-driver.xml ${CONFIG_DIR}configDropins/defaults/

COPY liberty/server.xml $CONFIG_DIR
COPY liberty/jvm.options $CONFIG_DIR

COPY target/summit-api.war /opt/ol/wlp/usr/servers/defaultServer/apps
EXPOSE 9080
