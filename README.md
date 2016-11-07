# spring-boot-elasticsearch-LIRE-docker
Walk through Spring-boot with ElasticSearch Restful.

1.How to install ElasticSearch?

    brew install elasticsearch

2.How to start up ElasticSearch?

    elasticsearch --config=/usr/local/opt/elasticsearch/config/elasticsearch.yml

3.How to start up Spring-boot?

    git clone https://github.com/yangboz/spring-boot-elasticsearch-LIRE-docker
    cd spring-boot-elasticsearch-LIRE-docker
    mvn spring-boot:run

4.Docker containerize it

    mvn package && java -jar target/spring-boot-elasticsearch-LIRE-docker-0.0.1.jar

    mvn package docker:build -DpushImage


5.Where is the RESTful result?

    http://localhost:8084/api/static/index.html

# References:

ElasticSearch: https://www.elastic.co/

LIRE: https://github.com/dermotte/lire

Spring-boot-elasticsearch 1.0: http://www.javacodegeeks.com/2015/03/head-first-elastic-search-on-java-with-spring-boot-and-data-features.html

Upgrade to Elasticsearch 2.0: https://jira.spring.io/browse/DATAES-211

ElasticSearch-LIRE: https://github.com/yangboz/elasticsearch-image

Spring-boot-docker: https://spring.io/guides/gs/spring-boot-docker/

# Troubleshoots

https://github.com/spring-projects/spring-data-elasticsearch/wiki/Spring-Data-Elasticsearch---Spring-Boot---version-matrix

http://stackoverflow.com/questions/35157642/nosuchmethoderror-com-google-common-util-concurrent-moreexecutors-directexecuto

http://stackoverflow.com/questions/33544863/java-elasticsearch-client-always-null
