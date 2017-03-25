package info.smartkit.eip.obtuse_octo_prune.services;

import info.smartkit.eip.obtuse_octo_prune.VOs.*;
import org.elasticsearch.action.index.IndexResponse;

import java.io.IOException;


/**
 * Created by smartkit on 2016/10/28.
 * @see:https://github.com/yangboz/elasticsearch-image
 */
public interface ESImageService {
    public HttpResponseVO setting(String index, SettingsVO settingsVO);

    //    public HttpResponseVO mapping(String index,String item,MappingVO mappingVO);
    public HttpResponseVO mapping(String index, String item);

    public IndexResponse index(String name, String item, IndexImageVO indexImageVO) throws IOException;

    public SearchResponseVO query(String index, int from, int size, String query);

    public SearchResponseVO search(String index, String item, SearchVO searchVO) throws IOException;

    public SearchResponseVO searchExisted(String index, String item, SearchExistedVO searchExistedVO);
}

