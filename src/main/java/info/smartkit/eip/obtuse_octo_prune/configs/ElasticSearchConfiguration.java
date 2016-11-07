package info.smartkit.eip.obtuse_octo_prune.configs;

import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Created by smartkit on 2016/11/7.
 */
@Configuration
public class ElasticSearchConfiguration {
    @Value("${spring.data.elasticsearch.cluster-name}")
    private String clusterName;
    @Value("${spring.data.elasticsearch.cluster-nodes}")
    private String clusterNodes;

    @Bean
    public ElasticsearchTemplate elasticsearchTemplate() throws UnknownHostException {
        String server = clusterNodes.split(":")[0];
        Integer port = Integer.parseInt(clusterNodes.split(":")[1]);
        Settings settings = Settings.settingsBuilder()
                .put("cluster.name", clusterName).build();
        TransportClient client = TransportClient.builder().settings(settings).build()
                .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName(server), port));
        return new ElasticsearchTemplate(client);

    }
}