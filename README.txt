Tag Cloud Summarizer
Master Thesis Project
Author: Angelina Velinska
Hamburg, 2010

Tag Cloud Summarizer defines the main concepts in the documents returned as a result from search queries, and presents them as a Tag Cloud. 
Users can search in a document collection, and based on their queries a document set is returned as a result. Users can see a tag cloud representation of the documents content of the whole query result set, as well as of single documents. When a large number of documents are returned from the query, the Tag Cloud Summarizer presents a quick overview of the content found, so that users can decide instantly if they will amend the search query.
The project implements Latent Semantic Analysis in order to retrieve key concepts from the documents, and to perform document indexing and querying.

    I. To compile the project, first you need to install additional jars. Call

      mvn install:install-file -DartifactId=name -DgroupId=group -Dversion=version -Dpackaging=jar -Dfile=lib\file.jar

    for each of the jars in lib folder.

    II. To build the war project, call

      mvn clean package -DskipTests

    in top directory.

    III. To run the webapp, package the project, and in tagcloud directory call:

        mvn jetty:run

    The application is running under

        http://localhost:8080/tagcloud