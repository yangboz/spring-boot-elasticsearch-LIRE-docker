package info.smartkit.eip.obtuse_octo_prune.services;

import info.smartkit.eip.obtuse_octo_prune.domains.Image;
import info.smartkit.eip.obtuse_octo_prune.domains.LireImage;
import org.elasticsearch.search.aggregations.support.ValuesSource;

/**
 * Created by smartkit on 2016/10/28.
 */
public interface ImageService {
    public void mapping(Image image, ValuesSource.MetaData metaData);
    public void index(String base64Image);
    public void search(LireImage lireImage,int index);

}
