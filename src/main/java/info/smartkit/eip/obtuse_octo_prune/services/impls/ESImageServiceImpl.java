package info.smartkit.eip.obtuse_octo_prune.services.impls;

import info.smartkit.eip.obtuse_octo_prune.VOs.*;
import info.smartkit.eip.obtuse_octo_prune.configs.ElasticSearchBean;
import info.smartkit.eip.obtuse_octo_prune.services.ESImageService;
import info.smartkit.eip.obtuse_octo_prune.utils.LireFeatures;
import net.semanticmetadata.lire.imageanalysis.features.LireFeature;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.StringUtils;
import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.index.query.image.ImageQueryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;
import sun.misc.IOUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static info.smartkit.eip.obtuse_octo_prune.utils.EsUtil.client;
import static info.smartkit.eip.obtuse_octo_prune.utils.ImageUtils.decodeImage;
import static info.smartkit.eip.obtuse_octo_prune.utils.ImageUtils.encodeImage;
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

        IndexResponse response = client.prepareIndex(name, item)
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
//                        "limit": 100
//            }
//        }
//    }
//}'
//@see: https://www.elastic.co/guide/en/elasticsearch/client/java-api/2.4/java-search.html
    @Override
    public SearchResponseVO search(SimpleSearchVO simpleSearchVO,byte[] imageData) throws IOException {

//        indexing at first then get indexed ID
        IndexImageVO indexImageVO = new IndexImageVO();
        // Converting Image byte array into Base64 String
        String imageDataString = StringUtils.newStringUtf8(Base64.encodeBase64(imageData, false));
        indexImageVO.setMy_img(imageDataString);
//        String indexedID = this.index(simpleSearchVO.getIndex(),simpleSearchVO.getItem(),indexImageVO).getId();
//        LOG.info("indexedID:"+indexedID);
        // instance a json mapper
        ObjectMapper mapper = new ObjectMapper(); // create once, reuse
        // generate json
        String json = mapper.writeValueAsString(simpleSearchVO);
        // image byte
//        SearchQueryELImageVO myImage = searchVO.getQuery().getImage().getMy_img();
//        byte[] decodedImg = org.apache.commons.codec.binary.Base64.decodeBase64(myImage.getImageStr().getBytes());
        ImageQueryBuilder queryBuilder = new ImageQueryBuilder("my_img")
                .image(imageData)
                .lookupField("my_img")
                .feature(simpleSearchVO.getFeature())
                .hash(simpleSearchVO.getHash())
                .lookupIndex(simpleSearchVO.getIndex())
                .lookupType(simpleSearchVO.getItem())
                .lookupId("");

        SearchResponse response = client.prepareSearch(simpleSearchVO.getIndex())
                .setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
                .setTypes(simpleSearchVO.getItem())
                .setQuery(queryBuilder)
//                .setPostFilter(QueryBuilders.rangeQuery("age").from(12).to(18))     // Query Filter
                .setFrom(simpleSearchVO.getFrom())
                .setSize(simpleSearchVO.getSize()).setExplain(false)
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
    public SearchResponseVO searchExisted(String index, String item, SearchExistedVO searchExistedVO) {
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

        SearchResponse response = client.prepareSearch(index)
                .setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
                .setTypes(item)
                .setQuery(queryBuilder)
//                .setPostFilter(QueryBuilders.rangeQuery("age").from(12).to(18))     // Query Filter
                .setFrom(searchExistedVO.getFrom())
                .setSize(searchExistedVO.getSize()).setExplain(false)
                .execute()
                .actionGet();
//        Scroll response
//        SearchResponse scrollResp = client.prepareSearch(index)
//                .addSort(SortParseElement.DOC_FIELD_NAME, SortOrder.ASC)
//                .setScroll(new TimeValue(60000))
//                .setQuery(queryBuilder)
//                .setSize(100).execute().actionGet(); //100 hits per shard will be returned for each scroll
////Scroll until no hits are returned
//        while (true) {
//
//            for (SearchHit hit : scrollResp.getHits().getHits()) {
//                //Handle the hit...
//            }
//            scrollResp = EsUtil.client.prepareSearchScroll(scrollResp.getScrollId()).setScroll(new TimeValue(60000)).execute().actionGet();
//            //Break condition: No hits are returned
//            if (scrollResp.getHits().getHits().length == 0) {
//                break;
//            }
//        }
        LOG.info("SearchResponse:"+response.toString());
        SearchResponseVO responseVO = new SearchResponseVO();
        responseVO.setHits(response.getHits());
//        LOG.info("SearchResponseVO:"+responseVO.toString());
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

//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_JSON);
//        String ID = searchExistedVO.getId();
//        String requestJson = "{\"query\": { \"image\": {\"my_img\": {\"feature\": \"CEDD\",\"index\": \"my_index\",\"type\": \"my_image_item\",\"id\":" +"\""+ID+"\","
//                +"\"hash\": \"BIT_SAMPLING\""+"\"limit\":2}}}}";
//        HttpEntity<String> entity = new HttpEntity<String>(requestJson,headers);
//        final String urlString = elasticSearchBean.getClusterUrl()+"/{index}/{item}/_search";
//
//        HttpClient httpClient = HttpClientBuilder.create().build();
//        ClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory(httpClient);
//        RestTemplate restTemplate = new RestTemplate(requestFactory);
//
//        HttpResponseVO result = new HttpResponseVO();
//        Map<String, String> params = new HashMap<String, String>();
//        params.put("index", index);//my_index
//        params.put("item", item);//my_image_item
//
//        // send request and parse result
//        ResponseEntity<String> response = null;
//        try {
//            response = restTemplate
//                    .exchange(urlString, HttpMethod.POST, entity, String.class, params);
//        }catch(Exception e) {
//            LOG.debug(e);
//        }
//        if (response.getStatusCode() == HttpStatus.OK) {
//            LOG.debug("response:"+response.toString());
////            JSONObject userJson = new JSONObject(response.getBody());
//        } else {
//            // nono... bad credentials
//            LOG.error(response);
//        }
////
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
