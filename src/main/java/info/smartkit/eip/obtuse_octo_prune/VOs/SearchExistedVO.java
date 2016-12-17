package info.smartkit.eip.obtuse_octo_prune.VOs;

/**
 * Created by smartkit on 2016/11/10.
 */
public class SearchExistedVO
{
        private int from = 0;
        private int size = 100;
    private SearchExistedQueryVO query;

    public SearchExistedVO(SearchExistedQueryVO query) {
        this.query = query;
    }

    public SearchExistedVO() {
    }

    public SearchExistedQueryVO getQuery() {
        return query;
    }

    public void setQuery(SearchExistedQueryVO query) {
        this.query = query;
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

        @Override
        public String toString() {
            return "SearchExistedVO{" +
                    "from=" + from +
                    ", size=" + size +
                    ", query=" + query +
                    '}';
        }
}
