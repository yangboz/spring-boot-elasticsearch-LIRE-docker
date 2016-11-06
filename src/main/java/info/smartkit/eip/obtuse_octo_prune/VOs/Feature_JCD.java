package info.smartkit.eip.obtuse_octo_prune.VOs;

import info.smartkit.eip.obtuse_octo_prune.utils.LireFeatures;

import java.util.Arrays;
import java.util.List;

/**
 * Created by smartkit on 2016/11/6.
 */
public class Feature_JCD {
    private List<String> hash = Arrays.asList(LireFeatures.JCD.getHash(),
            LireFeatures.LSH.getHash());
}
