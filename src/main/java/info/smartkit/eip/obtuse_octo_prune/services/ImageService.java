package info.smartkit.eip.obtuse_octo_prune.services;

import info.smartkit.eip.obtuse_octo_prune.VOs.IndexImageVO;
import info.smartkit.eip.obtuse_octo_prune.VOs.MappingVO;
import info.smartkit.eip.obtuse_octo_prune.VOs.SearchVO;
import info.smartkit.eip.obtuse_octo_prune.VOs.SettingsVO;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.springframework.http.HttpStatus;


/**
 * Created by smartkit on 2016/10/28.
 * @see:https://github.com/yangboz/elasticsearch-image
 */
public interface ImageService {
    public HttpStatus setting(String index, SettingsVO settingsVO);
    public HttpStatus mapping(String index,String item,MappingVO mappingVO);
    public HttpStatus index(String database, String table, IndexImageVO indexImageVO);
    public void search(String database,String table, SearchVO searchVO);
    public void searchExisted(SearchRequestBuilder queryBuilder);
}
