# Summit Health JEE Application on Openshift

This project is a conceptual Java EE application running on Open Liberty for a health records system, designed to showcase best in class integration of modern cloud technology running on Openshift.

## Summit Health Context

Summit Health is a conceptual healthcare/insurance type company. It has been around a long time, and has 100s of thousands of patient records. Summit's health records look very similar to the health records of most insurance companies.

Originally, Summit Health used a monolithic application structure for their application. Their application structure was a full stack Java application running on Websphere connected to a DB2 database. Here's what the original architecture for Summit Health looked like: 

![](readme_images/original_architecture.png)

Recently, Summit Health decided to modernize their application and break it up into microservices. They decided to move to a SQL database connected to a Java EE application running on Open Liberty for the business logic and a Node.js application for the Patient UI. In addition, Summit Health also decided to bring these applications to Openshift in the Cloud. The new current architecture for Summit Health looks like this: 

![](readme_images/new_architecture.png)

Since moving to Openshift, Summit Health has expanded to include new microservices that include an Admin application and an Analytics application. These along with the Patient UI can be found in seperate code patterns.

# Architecture

![](readme_images/architecture.png)

1. User makes a call to one of the APIs for the Java EE application which is located in Openshift's application load balancer.
2. The API in Openshift's application load balancer triggers the API endpoint code in the Java EE application that is running on an Open Liberty server in a Docker container on Openshift.
3. The Java EE application queries the MySQL database to get the desired data.
4. The MySQL database sends back the data to the Java EE application where it gets handled accordingly.
5. The data gets configured into JSON format that gets returned to the API and User.

# Steps

1. Install the following prerequisite tools.
    * A Java 8 (or higher) JDK such as [OpenJDK](https://openjdk.java.net/install/index.html)
    * [maven](https://maven.apache.org/download.cgi)
    * [docker](https://www.docker.com/get-started)
    * [ibmcloud CLI](https://cloud.ibm.com/docs/cli?topic=cloud-cli-getting-started)
    * [openshift (oc) CLI](https://www.okd.io/download.html)

2. [Sign up for an IBM Cloud account](https://cloud.ibm.com/docs/account?topic=account-signup) if you do not have one.
   You must have a Pay-As-You-Go or Subscription account to deploy this code pattern.  See https://cloud.ibm.com/docs/account?topic=account-upgrading-account to upgrade your account.

3. Create a [IBM Cloud Red Hat OpenShift Container Platform cluster](https://cloud.ibm.com/docs/containers?topic=containers-openshift_tutorial)

4. Create a [Compose for MySQL database](https://cloud.ibm.com/catalog/services/compose-for-mysql).  After the database is provisioned, make note of its URL, port, user and password.

5. Clone this project.
    ```
    git clone https://github.com/IBM/summit-jee-openshift.git
    ```

6. Edit the file `summit-api/liberty/server.xml` to change the `datasource` properties to the URL, port, user and password of your MySql database.

7. Build the Java EE application.
    ```
    cd summit-api
    mvn package
    ```

8. Build the Java EE docker image.
   ```
   docker build -t summit-api:1
   ```

9. Create a repository in your dockerhub account and push the Java EE docker image to it.  (Substitute your account name into the commands.)

   ```
   docker tag summit-api:1 YOURACCOUNT/summit-api:1
   docker login -u YOURACCOUNT
   docker push YOURACCOUNT/summit-api:1
   ```

10. Edit the file `summit-api/deploy-summit-api.yaml` to change the `image` key to your docker image.

11. Deploy the application to your cluster.
    ````
    oc apply -f deploy-summit-api.yaml
    ```` 

12. Create a route to expose the application to the internet.
    ````
    oc expose svc summit-svc
    ````

13. Verify that the application is working.  First obtain the hostname assigned to the route.
    ````
    oc get route summit-svc
    ````

    In a browser window, navigate to `<hostname>/openapi/ui/`.  An OpenAPI specification of the endpoints and operations supported by the Java EE application appears.

14. Generate synthentic patient health records and populate the MySQL database by running the `generate.sh` script in `generate/`. Refer to the script's [README](generate/README.md) for instructions on how to run the script. 
> NOTE: The script populates the MySQL database at about 125 patients per hour

# License

This code pattern is licensed under the Apache License, Version 2. Separate third-party code objects invoked within this code pattern are licensed by their respective providers pursuant to their own separate licenses. Contributions are subject to the [Developer Certificate of Origin, Version 1.1](https://developercertificate.org/) and the [Apache License, Version 2](https://www.apache.org/licenses/LICENSE-2.0.txt).

[Apache License FAQ](https://www.apache.org/foundation/license-faq.html#WhatDoesItMEAN)
