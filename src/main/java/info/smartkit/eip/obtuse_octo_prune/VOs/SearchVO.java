package info.smartkit.eip.obtuse_octo_prune.VOs;

import org.apache.lucene.queryparser.flexible.core.builders.QueryBuilder;
import org.elasticsearch.action.search.SearchRequestBuilder;

/**
 * Created by smartkit on 2016/11/6.
 */
public class SearchVO {
    private int from = 0;
    private int size = 3;
    private SearchQueryVO query;

    public SearchVO() {
    }

    public SearchVO(int from, int size, SearchQueryVO query) {
        this.from = from;
        this.size = size;
        this.query = query;
    }

    @Override
    public String toString() {
        return "SearchVO{" +
                "from=" + from +
                ", size=" + size +
                ", query=" + query +
                '}';
    }

    public int getFrom() {
        return from;
    }

    public void setFrom(int from) {
        this.from = from;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public SearchQueryVO getQuery() {
        return query;
    }

    public void setQuery(SearchQueryVO query) {
        this.query = query;
    }
}
