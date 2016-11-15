package info.smartkit.eip.obtuse_octo_prune.services;

import info.smartkit.eip.obtuse_octo_prune.VOs.*;


/**
 * Created by smartkit on 2016/10/28.
 * @see:https://github.com/yangboz/elasticsearch-image
 */
public interface ImageService {
    public HttpResponseVO setting(String index, SettingsVO settingsVO);
//    public HttpResponseVO mapping(String index,String item,MappingVO mappingVO);
    public HttpResponseVO mapping(String index,String item);
    public IndexResponseVO index(String name, String item, IndexImageVO indexImageVO);
    public SearchResponseVO search(String index,String item, SearchVO searchVO);
    public SearchResponseVO searchExisted(String index,String item,SearchExistedVO searchExistedVOVO);
}
