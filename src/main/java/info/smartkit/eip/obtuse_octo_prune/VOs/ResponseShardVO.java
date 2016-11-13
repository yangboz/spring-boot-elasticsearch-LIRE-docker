package info.smartkit.eip.obtuse_octo_prune.VOs;

/**
 * Created by smartkit on 2016/11/8.
 */
public class ResponseShardVO {
//    "total": 2,
//            "successful": 1,
//            "failed": 0
    private int total;
    private int failed;
    private int successful;

    public ResponseShardVO(int total, int failed, int successful) {
        this.total = total;
        this.failed = failed;
        this.successful = successful;
    }

    public ResponseShardVO() {
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getFailed() {
        return failed;
    }

    public void setFailed(int failed) {
        this.failed = failed;
    }

    public int getSuccessful() {
        return successful;
    }

    public void setSuccessful(int successful) {
        this.successful = successful;
    }
}
