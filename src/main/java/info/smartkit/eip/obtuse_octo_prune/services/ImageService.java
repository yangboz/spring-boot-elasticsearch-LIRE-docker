package info.smartkit.eip.obtuse_octo_prune.services;

import info.smartkit.eip.obtuse_octo_prune.VOs.MappingVO;
import info.smartkit.eip.obtuse_octo_prune.VOs.SearchVO;
import info.smartkit.eip.obtuse_octo_prune.VOs.SettingsVO;
import org.elasticsearch.action.search.SearchRequestBuilder;


/**
 * Created by smartkit on 2016/10/28.
 * @see:https://github.com/yangboz/elasticsearch-image
 */
public interface ImageService {
    public void setting(String index,SettingsVO settingsVO);
    public void mapping(String index,String item,MappingVO mappingVO);
    public void index(String table,String imageStr);
    public void search(String table, SearchVO searchVO);
    public void searchExisted(SearchRequestBuilder queryBuilder);
}
