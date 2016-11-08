package info.smartkit.eip.obtuse_octo_prune.VOs;

/**
 * Created by smartkit on 2016/11/6.
 */
public class SearchVO {
    private int from = 0;
    private int size = 3;
    private Object query;

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

    public Object getQuery() {
        return query;
    }

    public void setQuery(Object query) {
        this.query = query;
    }
}
