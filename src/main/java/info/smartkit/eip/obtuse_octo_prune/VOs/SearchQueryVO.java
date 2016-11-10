package info.smartkit.eip.obtuse_octo_prune.VOs;

/**
 * Created by smartkit on 2016/11/10.
 */
public class SearchQueryVO {
    private SearchQueryImageVO image;

    public SearchQueryVO(SearchQueryImageVO image) {
        this.image = image;
    }

    public SearchQueryVO() {
    }

    public SearchQueryImageVO getImage() {
        return image;
    }

    public void setImage(SearchQueryImageVO image) {
        this.image = image;
    }
}
