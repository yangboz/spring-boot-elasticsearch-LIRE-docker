package org.elasticsearch.index.query.image;

import com.google.common.collect.MapMaker;

import java.util.Map;

/**
 * Cache document score for {@link ImageHashQuery}
 */
public class ImageScoreCache {
    private Map<String, Float> scoreCache = new MapMaker().makeMap();

    public Float getScore(String key) {
        if (!scoreCache.containsKey(key)) {
            return null;
        }
        return scoreCache.get(key);
    }

    public void setScore(String key, Float score) {
        scoreCache.put(key, score);
    }
}
