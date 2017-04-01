/*
 * Copyright (c) 2017. SMARTKIT.INFO reserved.
 */

package info.smartkit.eip.obtuse_octo_prune.VOs;

import info.smartkit.eip.obtuse_octo_prune.utils.LireFeatures;
import info.smartkit.eip.obtuse_octo_prune.utils.LireHashs;

/**
 * Created by smartkit on 28/03/2017.
 */
public class SimpleSearchVO {
    private String index;
    private String item;

    private String url;
    private String id;

    private int from=0;
    private int size=10;

    @Override
    public String toString() {
        return "SimpleSearchVO{" +
                "index='" + index + '\'' +
                ", item='" + item + '\'' +
                ", url='" + url + '\'' +
                ", id='" + id + '\'' +
                ", from=" + from +
                ", size=" + size +
                ", feature='" + feature + '\'' +
                ", hash='" + hash + '\'' +
                ", boost=" + boost +
                ", limit=" + limit +
                '}';
    }

    public SimpleSearchVO(String index, String item, String url, String id, int from, int size, String feature, String hash, double boost, int limit) {
        this.index = index;
        this.item = item;
        this.url = url;
        this.id = id;
        this.from = from;
        this.size = size;
        this.feature = feature;
        this.hash = hash;
        this.boost = boost;
        this.limit = limit;
    }

    public int getFrom() {

        return from;
    }

    public void setFrom(int from) {
        this.from = from;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public SimpleSearchVO(String index, String item, String url, String id, String feature, String hash, double boost, int limit) {
        this.index = index;
        this.item = item;
        this.url = url;
        this.id = id;
        this.feature = feature;
        this.hash = hash;
        this.boost = boost;
        this.limit = limit;
    }

    private String feature = LireFeatures.CEDD;

    public String getFeature() {
        return feature;
    }

    public void setFeature(String feature) {
        this.feature = feature;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public double getBoost() {
        return boost;
    }

    public void setBoost(double boost) {
        this.boost = boost;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    private String hash = LireHashs.CEDD;
    private double boost;
    private int limit;

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public SimpleSearchVO() {
    }

}
