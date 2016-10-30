package info.smartkit.eip.obtuse_octo_prune.services.impls;

import info.smartkit.eip.obtuse_octo_prune.domains.Image;
import info.smartkit.eip.obtuse_octo_prune.domains.LireImage;
import info.smartkit.eip.obtuse_octo_prune.domains.Settings;
import info.smartkit.eip.obtuse_octo_prune.domains.metadata.IMetaData;
import info.smartkit.eip.obtuse_octo_prune.services.ImageService;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * Created by smartkit on 2016/10/28.
 * TODO:https://github.com/kzwang/elasticsearch-image
 */
@Service
public class ImageServiceImpl implements ImageService {
//    curl -XPUT 'localhost:9200/my_index' -d '{
//            "settings": {
//        "number_of_shards": 5,
//                "number_of_replicas": 1,
//                "index.version.created": 1070499
//    }
//  }'
    @Override
    public void setting(Settings settings) {
        RestTemplate  restTemplate = new RestTemplate();
        Settings mySsettings = new Settings();
        restTemplate.put("http://localhost:9200/my_index",mySsettings,);
    }

    @Override
    public void mapping(Image image, IMetaData metaData) {

    }

    @Override
    public void index(String base64Image) {

    }

    @Override
    public void search(LireImage lireImage, int index) {

    }
}
