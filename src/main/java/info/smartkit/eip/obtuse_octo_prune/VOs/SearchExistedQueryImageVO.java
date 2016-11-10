package info.smartkit.eip.obtuse_octo_prune.VOs;

/**
 * Created by smartkit on 2016/11/10.
 */
public class SearchExistedQueryImageVO {
   private SearchExistedQueryELImageVO el_image;

    public SearchExistedQueryImageVO(SearchExistedQueryELImageVO el_image) {
        this.el_image = el_image;
    }

    public SearchExistedQueryImageVO() {
    }

    public SearchExistedQueryELImageVO getEl_image() {
        return el_image;
    }

    public void setEl_image(SearchExistedQueryELImageVO el_image) {
        this.el_image = el_image;
    }
}
