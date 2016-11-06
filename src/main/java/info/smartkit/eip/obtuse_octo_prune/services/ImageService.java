package info.smartkit.eip.obtuse_octo_prune.services;

import info.smartkit.eip.obtuse_octo_prune.VOs.MappingVO;
import info.smartkit.eip.obtuse_octo_prune.domains.Image;
import info.smartkit.eip.obtuse_octo_prune.domains.LireImage;
import info.smartkit.eip.obtuse_octo_prune.VOs.SettingsVO;


/**
 * Created by smartkit on 2016/10/28.
 * @see:https://github.com/yangboz/elasticsearch-image
 */
public interface ImageService {
    public void setting(SettingsVO settingsVO);
    public void mapping(MappingVO mappingVO);
    public void index(String imageStr);
    public void search(LireImage lireImage,int index);
    public void searchExisted(LireImage lireImage,int index);
}
