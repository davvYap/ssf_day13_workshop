package sg.edu.nus.iss.workshop13.util;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.attribute.PosixFilePermission;
import java.nio.file.attribute.PosixFilePermissions;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class IOUtil {
    private static final Logger logger = LoggerFactory.getLogger(IOUtil.class);

    public static void createDir(String path){
        File dir = new File(path);
        boolean isDirExists = dir.exists();
        logger.info("Is Directory exists?", isDirExists);

        if(isDirExists){
            String osName = System.getProperty("os.name");
            // for macOS and linux OS
            if(!osName.contains("Windows")){
                String permission = "rwxrwx---";
                Set<PosixFilePermission> permissions = PosixFilePermissions.fromString(permission);
                try{
                    Files.setPosixFilePermissions(dir.toPath(), permissions);
                }catch(IOException e){
                    logger.error("Error",e);
                }
            }
        }else{
            dir.mkdir();
        }
    }
}
