# spring-boot-elasticsearch-LIRE-docker
Walk through Spring-boot with ElasticSearch microservices RESTful.

![Image of Architecture](https://github.com/yangboz/spring-boot-elasticsearch-LIRE-docker/blob/master/es-hadoop-diagram.jpg)

1.How to install ElasticSearch-2.4.1?

   MacOSX: 
   
   `
   brew install elasticsearch
   `
   
   Ubuntu: 
   
 `
   wget https://download.elastic.co/elasticsearch/release/org/elasticsearch/distribution/deb/elasticsearch/2.4.1/elasticsearch-2.4.1.deb
 `
 
 `
   dpkg -i elasticsearch-2.4.1.deb
 `

2.How to start up ElasticSearch?

   MacOSX: 
   
   `
   elasticsearch --config=/usr/local/opt/elasticsearch/libexec/config/elasticsearch.yml
   `
    
   Ubuntu: 
   `
   bin/elasticsearch -Des.insecure.allow.root=true -d
   `

3.How to start up Spring-boot?

    git clone https://github.com/yangboz/spring-boot-elasticsearch-LIRE-docker
    cd spring-boot-elasticsearch-LIRE-docker
    mvn spring-boot:run

4.Docker containerize it

    mvn package && java -jar target/spring-boot-elasticsearch-LIRE-docker-0.0.1.jar

    mvn package docker:build -DpushImage


5.Where is the RESTful result?

    http://localhost:8084/api/static/index.html

6.Facial analysis(flandmark)

6.1.Face Detection

6.2.Facial Feature Extraction

6.4.Face Recognition/Classification

# References:

ElasticSearch: https://www.elastic.co/

LIRE: https://github.com/dermotte/lire

Spring-boot-elasticsearch 1.0: http://www.javacodegeeks.com/2015/03/head-first-elastic-search-on-java-with-spring-boot-and-data-features.html

Upgrade to Elasticsearch 2.0: https://jira.spring.io/browse/DATAES-211

ElasticSearch-LIRE: 

https://www.elastic.co/blog/found-getting-started-with-lire-and-elasticsearch

https://github.com/yangboz/elasticsearch-image

Spring-boot-docker: https://spring.io/guides/gs/spring-boot-docker/

ElasticSearch with Hadoop: https://www.elastic.co/products/hadoop

https://db-blog.web.cern.ch/blog/prasanth-kothuri/2016-03-integrating-hadoop-and-elasticsearch-part-1-loading-and-querying

Facial Analysis: http://openimaj.org/tutorial/pt07.html

# Troubleshoots

http://ignaciosuay.com/how-to-connect-spring-boot-to-elasticsearch-2-x-x/

https://github.com/spring-projects/spring-data-elasticsearch/wiki/Spring-Data-Elasticsearch---Spring-Boot---version-matrix

http://stackoverflow.com/questions/35157642/nosuchmethoderror-com-google-common-util-concurrent-moreexecutors-directexecuto

http://stackoverflow.com/questions/33544863/java-elasticsearch-client-always-null

https://github.com/spring-projects/spring-boot/issues/4341

http://stackoverflow.com/questions/38937412/spring-boot-embedded-tomcat-not-starting

# TODO

1.Using Index/Mapping/SearchRequestBuilder(ElasticSearch API)/ImageQueryBuilder(ElasticSearch-image plugin) to re-factory.
2.Using MicroService architecture to seperate openIMAJ and ElasticSearch services.
