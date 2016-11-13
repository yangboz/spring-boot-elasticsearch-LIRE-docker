package info.smartkit.eip.obtuse_octo_prune.VOs;

/**
 * Created by smartkit on 2016/11/10.
 */
public class SearchQueryImageVO {
    private SearchQueryELImageVO my_img = new SearchQueryELImageVO();

    public SearchQueryImageVO(SearchQueryELImageVO my_img) {
        this.my_img = my_img;
    }

    public SearchQueryImageVO() {

    }

    public SearchQueryELImageVO getEl_image() {
        return my_img;
    }

    public void setEl_image(SearchQueryELImageVO my_img) {
        this.my_img = my_img;
    }
}
