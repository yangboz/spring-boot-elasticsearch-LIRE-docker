package info.smartkit.eip.obtuse_octo_prune.VOs;

import net.semanticmetadata.lire.searchers.ImageSearchHits;
import org.apache.lucene.document.Document;

import java.util.List;

/**
 * Created by smartkit on 2016/11/13.
 */
public class ImageSearchHitsVO {

    private int total;
    private float max_score;
    private List<ImageSearchHitsVO> hits;

    public ImageSearchHitsVO(int total, float max_score, List<ImageSearchHitsVO> hits) {
        this.total = total;
        this.max_score = max_score;
        this.hits = hits;
    }

    public ImageSearchHitsVO() {
    }

    public List<ImageSearchHitsVO> getHits() {
        return hits;
    }

    public void setHits(List<ImageSearchHitsVO> hits) {
        this.hits = hits;
    }

    public int getTotal() {

        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public float getMax_score() {
        return max_score;
    }

    public void setMax_score(float max_score) {
        this.max_score = max_score;
    }
}
