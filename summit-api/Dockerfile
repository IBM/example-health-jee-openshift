FROM openliberty-mysql:1

COPY liberty/server.xml $CONFIG_DIR
RUN mkdir -p  /opt/wlp/usr/servers/defaultServer/apps
RUN chmod 777 /opt/wlp/usr/servers/defaultServer
RUN chmod 777 /opt/wlp/lib
RUN chmod 644 /opt/wlp/lib/mysql-connector-java-8.0.16.jar

COPY target/summit-api.war /opt/wlp/usr/servers/defaultServer/apps
EXPOSE 9080
