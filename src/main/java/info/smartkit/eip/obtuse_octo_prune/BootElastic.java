package info.smartkit.eip.obtuse_octo_prune;

import info.smartkit.eip.obtuse_octo_prune.configs.ElasticSearchBean;
import info.smartkit.eip.obtuse_octo_prune.utils.EsUtil;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;

//@Configuration
//@EnableAutoConfiguration
//@ComponentScan
@SpringBootApplication
public class BootElastic implements CommandLineRunner {

    private static Logger LOG = LogManager.getLogger(BootElastic.class);


    //@see: http://docs.spring.io/spring-boot/docs/current/reference/html/howto-spring-mvc.html#howto-customize-the-jackson-objectmapper
    @Autowired
    public void configeJackson(Jackson2ObjectMapperBuilder jackson2ObjectMapperBuilder) {

//        jackson2ObjectMapperBuilder.serializationInclusion(JsonInclude.Include.NON_NULL);
        jackson2ObjectMapperBuilder.failOnEmptyBeans(false);
        jackson2ObjectMapperBuilder.failOnUnknownProperties(false);
    }

    @Autowired
    ElasticSearchBean elasticSearchBean;

    public void run(String... args) throws Exception {
//        addSomeMovies();
        // We indexed star wars and pricess bride to our movie
        // listing in elastic search

        //Lets query if we have a movie with Star Wars as name
//        List<Movie> starWarsNameQuery = movieService.getByName("Star Wars");
//        LOG.info("Content of star wars name query is {}" + starWarsNameQuery);
//
//        //Lets query if we have a movie with The Princess Bride as name
//        List<Movie> brideQuery = movieService.getByName("The Princess Bride");
//        LOG.info("Content of princess bride name query is {}" + brideQuery);
//
//
//        //Lets query if we have a movie with rating between 6 and 9
//        List<Movie> byRatingInterval = movieService.getByRatingInterval(6d, 9d);
//        LOG.info("Content of Rating Interval query is {}" + byRatingInterval);
        //@see: https://www.elastic.co/guide/en/elasticsearch/client/java-api/2.4/transport-client.html
//        EsUtil.client = TransportClient.builder().build()
//                .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("localhost"), 9300));
        Settings settings = Settings.settingsBuilder()
                .put("cluster.name", elasticSearchBean.getClusterName()).build();
        EsUtil.client = TransportClient.builder().settings(settings)
                .build().addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName(elasticSearchBean.getClusterNodeHost()), 9300));
        LOG.info("EsClient:"+EsUtil.client);

    }

    public static void main(String[] args) throws Exception {
        ConfigurableApplicationContext cac = SpringApplication.run(BootElastic.class, args);
//        ApplicationContext ctx = new AnnotationConfigApplicationContext(BootElastic.class);
        cac.addApplicationListener(new ApplicationListener<ContextClosedEvent>() {

            @Override
            public void onApplicationEvent(ContextClosedEvent event) {
                // on shutdown
                EsUtil.client.close();
            }
        });


    }
}