package info.smartkit.eip.obtuse_octo_prune.services.impls;

import info.smartkit.eip.obtuse_octo_prune.VOs.MappingVO;
import info.smartkit.eip.obtuse_octo_prune.VOs.SearchVO;
import info.smartkit.eip.obtuse_octo_prune.VOs.SettingsVO;
import info.smartkit.eip.obtuse_octo_prune.services.ImageService;
import org.apache.log4j.Logger;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by smartkit on 2016/10/28.
 * TODO:https://github.com/kzwang/elasticsearch-image
 */
@Service
public class ImageServiceImpl implements ImageService {

    @Value("${spring.data.elasticsearch.cluster-url}")
    private String clusterUrl;
    private static Logger LOG = org.apache.log4j.LogManager.getLogger(ImageServiceImpl.class);

//    curl -XPUT 'localhost:9200/my_index' -d '{
//            "settings": {
//        "number_of_shards": 5,
//                "number_of_replicas": 1,
//                "index.version.created": 1070499
//    }
//  }'
@Override
public void setting(String index, SettingsVO settingsVO) {
        final String uri = "http://localhost:9200/{index}";
        Map<String, String> params = new HashMap<String, String>();
        params.put("index", index);//my_index
//        SettingsVO settingsVO = new SettingsVO();
        RestTemplate restTemplate = new RestTemplate();
        LOG.info("PUT settingsVO:"+settingsVO.toString());
        restTemplate.put ( uri, settingsVO, params);
    }
//    curl -XPUT 'localhost:9200/my_index/my_image_item/_mapping' -d '{
//            "my_image_item": {
//        "properties": {
//            "my_img": {
//                "type": "image",
//                        "feature": {
//                    "CEDD": {
//                        "hash": ["BIT_SAMPLING"]
//                    },
//                    "JCD": {
//                        "hash": ["BIT_SAMPLING", "LSH"]
//                    }
//                },
//                "metadata": {
//                    "jpeg.image_width": {
//                        "type": "string",
//                                "store": "yes"
//                    },
//                    "jpeg.image_height": {
//                        "type": "string",
//                                "store": "yes"
//                    }
//                }
//            }
//        }
//    }
//}'

    @Override
    public void mapping(String index, String item, MappingVO mappingVO) {
        final String uri = clusterUrl+"/{index}/{item}/_mapping";
        Map<String, String> params = new HashMap<String, String>();
        params.put("index", index);//my_index
        params.put("item", item);//my_image_item
//        MappingVO mappingVO = new MappingVO();
        RestTemplate restTemplate = new RestTemplate();
        LOG.info("PUT mappingVO:"+mappingVO.toString());
        restTemplate.put ( uri, mappingVO, params);
    }

//    curl -XPOST 'localhost:9200/test/test' -d '{
//            "my_img": "... base64 encoded image ..."
//}'

    @Override
    public void index(String table, String imageStr) {

        final String uri = "http://localhost:9200/{table}";
        Map<String, String> params = new HashMap<String, String>();
        params.put("table", table);//test/test
        SettingsVO settingsVO = new SettingsVO();
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.put ( uri, settingsVO, params);
    }

//    curl -XPOST 'localhost:9200/test/test/_search' -d '{
//            "from": 0,
//            "size": 3,
//            "query": {
//        "image": {
//            "my_img": {
//                "feature": "CEDD",
//                        "image": "... base64 encoded image to search ...",
//                        "hash": "BIT_SAMPLING",
//                        "boost": 2.1,
//                        "limit": 100
//            }
//        }
//    }
//}'

    @Override
    public void search(String table, SearchVO searchVO) {

        final String uri = "http://localhost:9200/{table}/_search";
        Map<String, String> params = new HashMap<String, String>();
        params.put("table", table);//test/test
//        SearchVO searchVO = new SearchVO();
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.postForLocation( uri, searchVO, params);

    }

//    curl -XPOST 'localhost:9200/test/test/_search' -d '{
//            "query": {
//        "image": {
//            "my_img": {
//                "feature": "CEDD",
//                        "index": "test",
//                        "type": "test",
//                        "id": "image1",
//                        "hash": "BIT_SAMPLING"
//            }
//        }
//    }
//}'

    @Override
    public void searchExisted(SearchRequestBuilder queryBuilder) {

    }
}
