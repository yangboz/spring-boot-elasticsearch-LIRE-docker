package info.smartkit.eip.obtuse_octo_prune.VOs;

import net.semanticmetadata.lire.ImageSearchHits;
import org.apache.lucene.document.Document;

/**
 * Created by smartkit on 2016/11/13.
 */
public class ImageSearchHitsVO {

    private int length;
    private float score;
    private Document doc;

    public ImageSearchHitsVO() {
    }

    public ImageSearchHitsVO(int length, float score, Document doc) {
        this.length = length;
        this.score = score;
        this.doc = doc;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public float getScore() {
        return score;
    }

    public void setScore(float score) {
        this.score = score;
    }

    public Document getDoc() {
        return doc;
    }

    public void setDoc(Document doc) {
        this.doc = doc;
    }
}
