package info.smartkit.eip.obtuse_octo_prune.configs;

import info.smartkit.eip.obtuse_octo_prune.BootElastic;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * Created by smartkit on 2016/11/8.
 */
@Component
public class ElasticSearchBean {

    private static Logger LOG = LogManager.getLogger(ElasticSearchBean.class);

    public String getClusterUrl() {
        return clusterUrl;
    }

    @Value("${spring.data.elasticsearch.cluster-url}")
    private String clusterUrl;

    public String getClusterNodeHost() {
        return clusterNodeHost;
    }

    @Value("${elasticsearch.cluster-nodes.1}")
    private String clusterNodeHost;


    public String getClusterName() {
        return clusterName;
    }

    @Value("${spring.data.elasticsearch.cluster-name}")
    private String clusterName;

    public ElasticSearchBean() {
    }

    @PostConstruct
    public void init() {
        LOG.info("ES,clusterUrl================== " + clusterUrl + " ================== ");
        LOG.info("ES,clusterNodeHost================== " + clusterNodeHost + " ================== ");
    }
}
