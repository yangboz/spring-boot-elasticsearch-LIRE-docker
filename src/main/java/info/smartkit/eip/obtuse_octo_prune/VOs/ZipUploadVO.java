package info.smartkit.eip.obtuse_octo_prune.VOs;

/**
 * Created by smartkit on 2016/12/7.
 */

        import java.io.File;
        import java.util.ArrayList;
        import java.util.List;

public class ZipUploadVO {
    //Overview
    private int numOfSucc=0;
    private int numOfFail=0;
    private int totalNum = 0;

    private List<File> details = new ArrayList<File>();

    public int getNumOfSucc() {
        return numOfSucc;
    }

    public void setNumOfSucc(int numOfSucc) {
        this.numOfSucc = numOfSucc;
    }

    public int getNumOfFail() {
        return numOfFail;
    }

    public void setNumOfFail(int numOfFail) {
        this.numOfFail = numOfFail;
    }

    public List<File> getDetails() {
        return details;
    }

    public void setDetails(List<File> details) {
        this.details = details;
    }

    public int getTotalNum() {
        return totalNum;
    }

    public void setTotalNum(int totalNum) {
        this.totalNum = totalNum;
    }

    @Override
    public String toString() {
        return "ZipUploadVO{" +
                "numOfSucc=" + numOfSucc +
                ", numOfFail=" + numOfFail +
                ", totalNum=" + totalNum +
                ", details=" + details +
                '}';
    }
}
