package info.smartkit.eip.obtuse_octo_prune.VOs;

import info.smartkit.eip.obtuse_octo_prune.utils.LireFeatures;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by smartkit on 2016/11/6.
 */
public class Feature_CCED {
    private List<String> hash = Arrays.asList(LireFeatures.CEDD);

    public Feature_CCED(List<String> hash) {
        this.hash = hash;
    }

    public Feature_CCED() {
    }

    public List<String> getHash() {
        return hash;
    }

    public void setHash(List<String> hash) {
        this.hash = hash;
    }

    @Override
    public String toString() {
        return "Feature_CCED{" +
                "hash=" + hash +
                '}';
    }
}
