package info.smartkit.eip.obtuse_octo_prune.services.impls;

import info.smartkit.eip.obtuse_octo_prune.domains.Image;
import info.smartkit.eip.obtuse_octo_prune.domains.LireImage;
import info.smartkit.eip.obtuse_octo_prune.services.ImageService;
import org.elasticsearch.search.aggregations.support.ValuesSource;
import org.springframework.stereotype.Service;

/**
 * Created by smartkit on 2016/10/28.
 * TODO:https://github.com/kzwang/elasticsearch-image
 */
@Service
public class ImageServiceImpl implements ImageService {
    @Override
    public void mapping(Image image, ValuesSource.MetaData metaData) {

    }

    @Override
    public void index(String base64Image) {

    }

    @Override
    public void search(LireImage lireImage, int index) {

    }
}
