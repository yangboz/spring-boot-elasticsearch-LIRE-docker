package info.smartkit.eip.obtuse_octo_prune.VOs;

/**
 * Created by smartkit on 2016/11/10.
 */
public class SearchExistedQueryVO {
    private SearchExistedQueryImageVO image;

    public SearchExistedQueryVO(SearchExistedQueryImageVO image) {
        this.image = image;
    }

    public SearchExistedQueryVO() {
    }

    public SearchExistedQueryImageVO getImage() {
        return image;
    }

    public void setImage(SearchExistedQueryImageVO image) {
        this.image = image;
    }
}
