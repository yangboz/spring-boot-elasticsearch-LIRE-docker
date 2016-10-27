# spring-boot-elasticsearch-LIRE-docker
Walk through Spring-boot with ElasticSearch Restful.

1.How to install ElasticSearch?

    brew install elasticsearch

2.How to start up ElasticSearch?

    elasticsearch --config=/usr/local/opt/elasticsearch/config/elasticsearch.yml

3.How to start up Spring-boot?

    git clone https://github.com/yangboz/obtuse-octo-prune
    cd obtuse-octo-prune
    mvn spring-boot:run

4.Containerize it

    mvn package && java -jar target/spring-boot-elasticsearch-LIRE-docker-0.0.1.jar

    mvn package docker -DpushImage


5.Where is the RESTful result?

    http://localhost:8084/api/static/index.html

# References:

ElasticSearch: https://www.elastic.co/

LIRE: https://github.com/dermotte/lire

Spring-boot-elasticsearch: http://www.javacodegeeks.com/2015/03/head-first-elastic-search-on-java-with-spring-boot-and-data-features.html

ElasticSearch-LIRE: https://github.com/kzwang/elasticsearch-image

Spring-boot-docker: https://spring.io/guides/gs/spring-boot-docker/
