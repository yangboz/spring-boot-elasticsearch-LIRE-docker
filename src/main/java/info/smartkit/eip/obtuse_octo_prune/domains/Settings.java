package info.smartkit.eip.obtuse_octo_prune.domains;

/**
 * Created by smartkit on 2016/10/29.
 * @see: https://github.com/yangboz/elasticsearch-image
 */
public class Settings {
    private int numberOfShards=5;
    private int numberOfReplicas=1;
    private double indexVersionCreated=1070499;

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

    @Override
    public String toString() {
        return "Settings{" +
                "numberOfShards=" + numberOfShards +
                ", numberOfReplicas=" + numberOfReplicas +
                ", indexVersionCreated=" + indexVersionCreated +
                '}';
    }
}
