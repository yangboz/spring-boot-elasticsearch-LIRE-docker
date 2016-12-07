package info.smartkit.eip.obtuse_octo_prune.utils;

/**
 * Created by smartkit on 2016/12/7.
 */
public class FileUtil {
    public static String getWorkingDir() {
        String workingDir = System.getProperty("user.dir");
        return workingDir;
    }

    public static String getUploads(Boolean isJar) throws Exception {
        // if(!new File("/uploads/").exists()) new File("/uploads/").mkdirs();
        // return "/uploads/";
        if (isJar) {
            //return getJarContainingFolder(Application.class) + "/uploads/";
            throw new Exception("to be fixed value!");
        } else {
            return getWorkingDir() + "/target/classes/uploads/";
        }
    }
}
