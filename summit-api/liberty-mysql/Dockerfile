FROM sdaschner/open-liberty:javaee8-jdk11-b3

ADD https://repo1.maven.org/maven2/mysql/mysql-connector-java/8.0.16/mysql-connector-java-8.0.16.jar  ${INSTALL_DIR}lib/mysql-connector-java-8.0.16.jar

COPY mysql-driver.xml ${CONFIG_DIR}configDropins/defaults/
