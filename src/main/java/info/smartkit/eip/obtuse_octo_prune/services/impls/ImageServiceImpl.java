package info.smartkit.eip.obtuse_octo_prune.services.impls;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import info.smartkit.eip.obtuse_octo_prune.VOs.*;
import info.smartkit.eip.obtuse_octo_prune.configs.ElasticSearchBean;
import info.smartkit.eip.obtuse_octo_prune.services.ImageService;
import org.apache.log4j.Logger;
import org.openimaj.image.ImageUtilities;
import org.openimaj.image.MBFImage;
import org.openimaj.image.processing.face.detection.DetectedFace;
import org.openimaj.image.processing.face.detection.HaarCascadeDetector;
import org.openimaj.image.processing.face.util.SimpleDetectedFaceRenderer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;

/**
 * Created by smartkit on 2016/10/28.
 * TODO:https://github.com/kzwang/elasticsearch-image
 */
@Service
public class ImageServiceImpl implements ImageService {

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

    @Override
    public IndexResponseVO index(String name, String item, IndexImageVO indexImageVO) {

        final String uri = elasticSearchBean.getClusterUrl()+"/{name}/{item}";
        Map<String, String> params = new HashMap<String, String>();
        params.put("name", name);//my_index
        params.put("item", item);///my_image_item
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
    public SearchResponseVO search(String index,String item, SearchVO searchVO) {

        final String uri = elasticSearchBean.getClusterUrl()+"/{index}/{item}/_search";
        Map<String, String> params = new HashMap<String, String>();
        params.put("index", index);//my_index
        params.put("item", item);///my_image_item
//        SearchVO searchVO = new SearchVO();
        RestTemplate restTemplate = new RestTemplate();
        SearchResponseVO result = new SearchResponseVO();
        try {
             result = restTemplate.postForObject( uri, searchVO,SearchResponseVO.class, params);
            LOG.info("restTemplate result:"+result.toString());
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
    public SearchResponseVO searchExisted(String index,String item,SearchExistedVO searchExistedVO) {
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
        final String uri = elasticSearchBean.getClusterUrl()+"/{index}/{item}/_search";
        Map<String, String> params = new HashMap<String, String>();
        params.put("index", index);//my_index
        params.put("item", item);///my_image_item
//        SearchVO searchVO = new SearchVO();
        RestTemplate restTemplate = new RestTemplate();
        SearchResponseVO result = new SearchResponseVO();
        try {
            result = restTemplate.postForObject( uri, searchExistedVO,SearchResponseVO.class, params);
            LOG.info("restTemplate result:"+result.toString());
        } catch (HttpStatusCodeException exception) {
//            result = exception.getStatusCode();
            LOG.error(exception.getResponseBodyAsString());
        }
        return result;

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

    @Override
    public AnalysisResVO analysis(File imgFile) throws IOException {
        MBFImage mbf = ImageUtilities.readMBF(imgFile);
        // A simple Haar-Cascade face detector
        AnalysisResVO analysisResponseVO = new AnalysisResVO();
        HaarCascadeDetector det1 = new HaarCascadeDetector();
        DetectedFace face1 = det1.detectFaces(mbf.flatten()).get(0);
        new SimpleDetectedFaceRenderer()
                .drawDetectedFace(mbf,10,face1);
        analysisResponseVO.setDetectedFace(face1);
//// Get the facial keypoints
//        FKEFaceDetector det2 = new FKEFaceDetector();
//        KEDetectedFace face2 = det2.detectFaces(mbf.flatten()).get(0);
//        new KEDetectedFaceRenderer()
//                .drawDetectedFace(mbf,10,face2);
//
//        analysisResponseVO.setKeDetectedFace(face2);
////// With the CLM Face Model
//        CLMFaceDetector det3 = new CLMFaceDetector();
//        CLMDetectedFace face3 = det3.detectFaces(mbf.flatten()).get(0);
//        new CLMDetectedFaceRenderer()
//                .drawDetectedFace(mbf,10,face3);
//        analysisResponseVO.setClmDetectedFace(face3);
//        DisplayUtilities.displayName(mbf, "OPenIMAJ Analysis");//for GUI testing.

        ObjectMapper objectMapper = new ObjectMapper();
        // Jackson version 1.9 or earlier
//        objectMapper.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);

// Jackson 2.0 or later
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        return analysisResponseVO;
    }

}
