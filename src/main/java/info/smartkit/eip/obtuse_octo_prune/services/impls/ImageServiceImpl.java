package info.smartkit.eip.obtuse_octo_prune.services.impls;

import info.smartkit.eip.obtuse_octo_prune.VOs.*;
import info.smartkit.eip.obtuse_octo_prune.configs.ElasticSearchBean;
import info.smartkit.eip.obtuse_octo_prune.services.ImageService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by smartkit on 2016/10/28.
 * TODO:https://github.com/kzwang/elasticsearch-image
 */
@Service
public class ImageServiceImpl implements ImageService {

@Autowired
    ElasticSearchBean elasticSearchBean;

    private static Logger LOG = org.apache.log4j.LogManager.getLogger(ImageServiceImpl.class);

//    curl -XPUT 'localhost:9200/my_index' -d '{
//            "settings": {
//        "number_of_shards": 5,
//                "number_of_replicas": 1,
//                "index.version.created": 1070499
//    }
//  }'
@Override
public HttpResponseVO setting(String index, SettingsVO settingsVO) {
    LOG.info("elasticSearchBean.getClusterUrl():"+elasticSearchBean.getClusterUrl());
        final String uri = elasticSearchBean.getClusterUrl()+"/{index}";
        Map<String, String> params = new HashMap<String, String>();
        params.put("index", index);//my_index
//        SettingsVO settingsVO = new SettingsVO();
        RestTemplate restTemplate = new RestTemplate();
        LOG.info("PUT settingsVO:"+settingsVO.toString());
         HttpResponseVO result = new HttpResponseVO();
        try {
            restTemplate.put ( uri, settingsVO, params);
        } catch (HttpStatusCodeException exception) {
            result.setStatusCode(exception.getStatusCode().value());
            result.setBody(exception.getResponseBodyAsString());
            LOG.error(exception.getResponseBodyAsString());
        }
        return result;
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
    public HttpResponseVO mapping(String index, String item, MappingVO mappingVO) {
        final String uri = elasticSearchBean.getClusterUrl()+"/{index}/{item}/_mapping";
        Map<String, String> params = new HashMap<String, String>();
        params.put("index", index);//my_index
        params.put("item", item);//my_image_item
//        MappingItemVO mappingVO = new MappingItemVO();
        RestTemplate restTemplate = new RestTemplate();
        LOG.info("PUT mappingVO:"+mappingVO.toString());
        //
        HttpResponseVO result = new HttpResponseVO();
        try {
            restTemplate.put ( uri, mappingVO, params);
        } catch (HttpStatusCodeException exception) {
            result.setStatusCode(exception.getStatusCode().value());
            result.setBody(exception.getResponseBodyAsString());
        }
        return result;
    }

//    curl -XPOST 'localhost:9200/test/test' -d '{
//            "my_img": "... base64 encoded image ..."
//}'

    @Override
    public IndexResponseVO index(String database, String table, IndexImageVO indexImageVO) {

        final String uri = elasticSearchBean.getClusterUrl()+"/{database}/{table}";
        Map<String, String> params = new HashMap<String, String>();
        params.put("database", database);//test
        params.put("table", table);///test
//        SettingsVO settingsVO = new SettingsVO();
        RestTemplate restTemplate = new RestTemplate();

        IndexResponseVO result = new IndexResponseVO();
        try {
            result = restTemplate.postForObject( uri, indexImageVO,IndexResponseVO.class, params);
            LOG.info("restTemplate:"+restTemplate.toString());
        } catch (HttpStatusCodeException exception) {
//            result = exception.getStatusCode();
            LOG.error(exception.toString());
        }
        return result;
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
    public SearchResponseVO search(String database,String table, SearchVO searchVO) {

        final String uri = elasticSearchBean.getClusterUrl()+"/{database}/{table}/_search";
        Map<String, String> params = new HashMap<String, String>();
        params.put("database", database);//test
        params.put("table", table);///test
//        SearchVO searchVO = new SearchVO();
        RestTemplate restTemplate = new RestTemplate();
        SearchResponseVO result = new SearchResponseVO();
        try {
            result = restTemplate.postForObject( uri, searchVO,SearchResponseVO.class, params);
            LOG.info("restTemplate:"+restTemplate.toString());
        } catch (HttpStatusCodeException exception) {
//            result = exception.getStatusCode();
            LOG.error(exception.getResponseBodyAsString());
        }
        return result;


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
    public SearchResponseVO searchExisted(String database,String table,SearchExistedVO searchExistedVO) {
//        SearchRequestBuilder queryBuilder = searchClient.prepareSearch(INDEX)
//                .setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
//                .setTypes("Image")
//                .setFrom(from)
//                .setSize(size);
//        ImageQueryBuilder query = new ImageQueryBuilder("img");  //image field
//        query.feature(feature);
//        query.hash(hash);
//        query.lookupIndex(INDEX);
//        query.lookupType("Image");
//        query.lookupId(itemId);
        final String uri = elasticSearchBean.getClusterUrl()+"/{database}/{table}/_search";
        Map<String, String> params = new HashMap<String, String>();
        params.put("database", database);//test
        params.put("table", table);///test
//        SearchVO searchVO = new SearchVO();
        RestTemplate restTemplate = new RestTemplate();
        SearchResponseVO result = new SearchResponseVO();
        try {
            result = restTemplate.postForObject( uri, searchExistedVO,SearchResponseVO.class, params);
            LOG.info("restTemplate:"+restTemplate.toString());
        } catch (HttpStatusCodeException exception) {
//            result = exception.getStatusCode();
            LOG.error(exception.getResponseBodyAsString());
        }
        return result;

    }
}
