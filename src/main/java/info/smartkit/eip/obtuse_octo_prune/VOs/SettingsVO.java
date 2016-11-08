package info.smartkit.eip.obtuse_octo_prune.VOs;

/**
 * Created by smartkit on 2016/10/29.
 * @see: https://github.com/yangboz/elasticsearch-image
 */
public class SettingsVO {
    private int numberOfShards=5;
    private int numberOfReplicas=1;
    private double indexVersionCreated=1070499;

    public SettingsVO() {
    }

    public int getNumberOfShards() {
        return numberOfShards;
    }

    public void setNumberOfShards(int numberOfShards) {
        this.numberOfShards = numberOfShards;
    }

    public int getNumberOfReplicas() {
        return numberOfReplicas;
    }

    public void setNumberOfReplicas(int numberOfReplicas) {
        this.numberOfReplicas = numberOfReplicas;
    }

    public double getIndexVersionCreated() {
        return indexVersionCreated;
    }

    public void setIndexVersionCreated(double indexVersionCreated) {
        this.indexVersionCreated = indexVersionCreated;
    }

    public SettingsVO(int numberOfShards, int numberOfReplicas, double indexVersionCreated) {
        this.numberOfShards = numberOfShards;
        this.numberOfReplicas = numberOfReplicas;
        this.indexVersionCreated = indexVersionCreated;
    }

    @Override
    public String toString() {
        return "SettingsVO{" +
                "numberOfShards=" + numberOfShards +
                ", numberOfReplicas=" + numberOfReplicas +
                ", indexVersionCreated=" + indexVersionCreated +
                '}';

    }
}
