package info.smartkit.eip.obtuse_octo_prune.VOs;

/**
 * Created by smartkit on 2016/11/10.
 */
public class SearchQueryImageVO {
    private SearchQueryELImageVO el_image = new SearchQueryELImageVO();

    public SearchQueryImageVO(SearchQueryELImageVO el_image) {
        this.el_image = el_image;
    }

    public SearchQueryImageVO() {

    }

    public SearchQueryELImageVO getEl_image() {
        return el_image;
    }

    public void setEl_image(SearchQueryELImageVO el_image) {
        this.el_image = el_image;
    }
}
