package info.smartkit.eip.obtuse_octo_prune.services.impls;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.SerializationFeature;
import info.smartkit.eip.obtuse_octo_prune.VOs.*;
import info.smartkit.eip.obtuse_octo_prune.configs.ElasticSearchBean;
import info.smartkit.eip.obtuse_octo_prune.services.ESImageService;
import info.smartkit.eip.obtuse_octo_prune.utils.EsUtil;
import org.apache.log4j.Logger;
import org.apache.lucene.queryparser.flexible.core.builders.QueryBuilder;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.QueryStringQueryBuilder;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.elasticsearch.index.query.image.ImageQueryBuilder;
import org.elasticsearch.search.internal.InternalSearchHits;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;

/**
 * Created by smartkit on 2016/10/28.
 * TODO:https://github.com/kzwang/elasticsearch-image
 */
@Service
public class ESImageServiceImpl implements ESImageService {

@Autowired
    ElasticSearchBean elasticSearchBean;

    //@see: http://stackoverflow.com/questions/22071198/adding-mapping-to-a-type-from-java-how-do-i-do-it
//    private  Client getClient() {
//        final Settings.Builder settings = Settings.settingsBuilder();
//        try (TransportClient transportClient = new TransportClient(settings)) {
//            transportClient = transportClient.addTransportAddress(new InetSocketTransportAddress("localhost", 9200));
//            LOG.info("ES client:"+transportClient);
//            return transportClient;
//        }
//    }

    private static Logger LOG = org.apache.log4j.LogManager.getLogger(ESImageServiceImpl.class);

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
    public HttpResponseVO mapping(String index, String item) {
        final String uri = elasticSearchBean.getClusterUrl()+"/{index}/{item}/_mapping";
        Map<String, String> params = new HashMap<String, String>();
        params.put("index", index);//my_index
        params.put("item", item);//my_image_item
//        MappingItemVO mappingVO = new MappingItemVO();
        RestTemplate restTemplate = new RestTemplate();
//        LOG.info("PUT mappingVO:"+mappingVO.toString());
        //
        HttpResponseVO result = new HttpResponseVO();
//        try {
//            restTemplate.put ( uri, mappingVO, params);
//        } catch (HttpStatusCodeException exception) {
//            result.setStatusCode(exception.getStatusCode().value());
//            result.setBody(exception.getResponseBodyAsString());
//        }
//        return result;

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        String requestJson = "{\"my_image_item\": {\"properties\": {\"my_img\": {\"type\": \"image\", \"feature\": {\"CEDD\": {\"hash\": [\"BIT_SAMPLING\"] }, \"JCD\": {\"hash\": [\"BIT_SAMPLING\", \"LSH\"] } }, \"metadata\": {\"jpeg.image_width\": {\"type\": \"string\", \"store\": true}, \"jpeg.image_height\": {\"type\": \"string\", \"store\": true} } } } } }";
        HttpEntity<String> entity = new HttpEntity<String>(requestJson,headers);

        try {
            restTemplate.put(uri, entity,params);
        } catch (HttpStatusCodeException exception) {
            result.setStatusCode(exception.getStatusCode().value());
            result.setBody(exception.getResponseBodyAsString());
        }
        return result;

//        String mapping = jsonBuilder()
//                .startObject()
//                .startObject(ns)
//                .startObject("_ttl")
//                .field("enabled","true")
//                .endObject()
//                .startObject("_source")
//                .field("enabled","false")
//                .endObject()

//        Client client = this.getClient();
//
//        XContentBuilder mapping = null;
//        try {
//            mapping = XContentFactory.jsonBuilder()
//                    .startObject()
//                    .startObject("my_image_item")
//                    .startObject("properties")
//                    .startObject("my_img")
//                    .field("type", "image")
//                    .field("feature", "not_analyzed")
//                    .field("metadata", "not_analyzed")
//                    .endObject()
//                    .startObject("source")
//                    .field("type","string")
//                    .endObject();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        PutMappingResponse putMappingResponse = client.admin().indices()
//                .preparePutMapping("my_index")
//                .setType("my_image_item")
//                .setSource(mapping)
//                .execute().actionGet();
    }

//    curl -XPOST 'localhost:9200/test/test' -d '{
//            "my_img": "... base64 encoded image ..."
//}'
//@see: https://www.elastic.co/guide/en/elasticsearch/client/java-api/2.4/java-docs-index.html
    @Override
    public IndexResponse index(String name, String item, IndexImageVO indexImageVO) throws IOException {

        IndexResponse response = EsUtil.client.prepareIndex(name, item)
                .setSource(jsonBuilder()
                        .startObject()
                        .field("my_img", indexImageVO.getMy_img())
                        .endObject()
                )
                .get();
        return response;
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
//                        "limit": 100r
//            }
//        }
//    }
//}'
//@see: https://www.elastic.co/guide/en/elasticsearch/client/java-api/2.4/java-search.html
    @Override
    public SearchResponseVO search(String index,String item, SearchVO searchVO) throws IOException {
        //indexing at first then get indexed ID
        IndexImageVO indexImageVO = new IndexImageVO();
        indexImageVO.setMy_img(searchVO.getQuery().getImage().getMy_img().getImage());
        String indexedID = this.index(index,item,indexImageVO).getId();
        LOG.info("indexedID:"+indexedID);
        // instance a json mapper
        ObjectMapper mapper = new ObjectMapper(); // create once, reuse
        // generate json
        String json = mapper.writeValueAsString(searchVO);
        // image byte
        SearchQueryELImageVO myImage = searchVO.getQuery().getImage().getMy_img();
        byte[] decodedImg = org.apache.commons.codec.binary.Base64.decodeBase64(myImage.getImage().getBytes());
        ImageQueryBuilder queryBuilder = new ImageQueryBuilder("my_img")
                .lookupField("my_img")
                .feature(myImage.getFeature())
                .hash(myImage.getHash())
                .lookupIndex(index)
                .lookupType(item)
                .boost((float)myImage.getBoost())
                .lookupId(indexedID);

        SearchResponse response = EsUtil.client.prepareSearch(index)
                .setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
                .setTypes(item)
//                .setFrom(0)
//                .setSize(100)
                .setQuery(queryBuilder)
//                .setPostFilter(QueryBuilders.rangeQuery("age").from(12).to(18))     // Query Filter
                .setFrom(searchVO.getFrom()).setSize(searchVO.getSize()).setExplain(false)
                .execute()
                .actionGet();
        LOG.info("SearchResponse:"+response.toString());
        SearchResponseVO responseVO = new SearchResponseVO();
//        InternalSearchHits hits = response.getHits();
//        hits.hits()
        responseVO.setHits(response.getHits());
        LOG.info("SearchResponseVO:"+responseVO.toString());
        return responseVO;


//        final String uri = elasticSearchBean.getClusterUrl()+"/{index}/{item}/_search";
//        Map<String, String> params = new HashMap<String, String>();
//        params.put("index", index);//my_index
//        params.put("item", item);///my_image_item
////        SearchVO searchVO = new SearchVO();
//        RestTemplate restTemplate = new RestTemplate();
//        SearchResponseVO result = new SearchResponseVO();
//        try {
//             result = restTemplate.postForObject( uri, searchVO,SearchResponseVO.class, params);
//            LOG.info("restTemplate result:"+result.toString());
//        } catch (HttpStatusCodeException exception) {
////            result = exception.getStatusCode();
//            LOG.error(exception.getResponseBodyAsString());
//        }
//        return result;

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
    public SearchResponseVO searchExisted(String index,String item,SearchExistedVO searchExistedVO) {
        //@see: https://github.com/kiwionly/elasticsearch-image
        ObjectMapper mapper = new ObjectMapper();
        mapper.disable(SerializationConfig.Feature.FAIL_ON_EMPTY_BEANS);
//        mapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
        SearchExistedQueryELImageVO myImage = searchExistedVO.getQuery().getImage().getMy_img();
        ImageQueryBuilder queryBuilder = new ImageQueryBuilder("my_img")
                .lookupField("my_img")
        .feature(myImage.getFeature())
        .hash(myImage.getHash())
        .lookupIndex(index)
        .lookupType(item)
        .lookupId(myImage.getId());

        SearchResponse response = EsUtil.client.prepareSearch(index)
                .setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
                .setTypes(item)
//                .setFrom(0)
//                .setSize(100)
                .setQuery(queryBuilder)
//                .setPostFilter(QueryBuilders.rangeQuery("age").from(12).to(18))     // Query Filter
                .setFrom(searchExistedVO.getFrom()).setSize(searchExistedVO.getSize()).setExplain(false)
                .execute()
                .actionGet();
        LOG.info("SearchResponse:"+response.toString());
        SearchResponseVO responseVO = new SearchResponseVO();
        responseVO.setHits(response.getHits());
        LOG.info("SearchResponseVO:"+responseVO.toString());
        return responseVO;
//        final String uri = elasticSearchBean.getClusterUrl()+"/{index}/{item}/_search";
//        Map<String, String> params = new HashMap<String, String>();
//        params.put("index", index);//my_index
//        params.put("item", item);///my_image_item
////        SearchVO searchVO = new SearchVO();
//        RestTemplate restTemplate = new RestTemplate();
//        SearchResponseVO result = new SearchResponseVO();
//        try {
//            result = restTemplate.postForObject( uri, searchExistedVO,SearchResponseVO.class, params);
//            LOG.info("restTemplate result:"+result.toString());
//        } catch (HttpStatusCodeException exception) {
////            result = exception.getStatusCode();
//            LOG.error(exception.getResponseBodyAsString());
//        }
//        return result;

    }

    @Override
    public SearchResponseVO query(String index,int from, int size, String query) {
        final String uri = elasticSearchBean.getClusterUrl()+"/{index}/_search";
        Map<String, String> params = new HashMap<String, String>();
        params.put("index", index);//my_index
        params.put("from", String.valueOf(from));//0
        params.put("size", String.valueOf(size));//10
        params.put("q",query);//"*:*"
//        SearchVO searchVO = new SearchVO();
        RestTemplate restTemplate = new RestTemplate();
        SearchResponseVO result = new SearchResponseVO();
        try {
            result = restTemplate.getForObject(uri,SearchResponseVO.class,params);
            LOG.info("restTemplate result:"+result.toString());
        } catch (HttpStatusCodeException exception) {
//            result = exception.getStatusCode();
            LOG.error(exception.getResponseBodyAsString());
        }
        return result;
    }



}
