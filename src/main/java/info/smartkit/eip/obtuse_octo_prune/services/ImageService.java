package info.smartkit.eip.obtuse_octo_prune.services;

import info.smartkit.eip.obtuse_octo_prune.domains.Image;
import info.smartkit.eip.obtuse_octo_prune.domains.LireImage;
import info.smartkit.eip.obtuse_octo_prune.domains.Settings;
import info.smartkit.eip.obtuse_octo_prune.domains.metadata.IMetaData;
import org.elasticsearch.search.aggregations.support.ValuesSource;

/**
 * Created by smartkit on 2016/10/28.
 * @see:https://github.com/yangboz/elasticsearch-image
 */
public interface ImageService {
    public void setting(Settings settings);
    public void mapping(Image image, IMetaData metaData);
    public void index(String base64Image);
    public void search(LireImage lireImage,int index);

}
