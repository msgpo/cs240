
import java.io.File;

public class FileType {
    public static void main(String[] args) {
        File file = new File(".");        
        System.out.println("file = " + file);

        System.out.println("file.isDirectory() = " + file.isDirectory());
        System.out.println("file.isFile() = " + file.isFile());

        //On Windows, files are always readable.
        System.out.println("file.canRead() = " + file.canRead());

        listContents(file);

        file = new File(file, "sampleDirectory");
        listContents(file);
    }

    private static void listContents(File file) {
        System.out.println("=================================================================");
        File[] files = file.listFiles();
        for(int i = 0; i < files.length; i++) {
            System.out.println("files[" + i + "] = " + files[i]);
            System.out.println("files[" + i + "].isDirectory() = " + files[i].isDirectory());
            System.out.println("files[" + i + "].isFile() = " + files[i].isFile());
            System.out.println("files[" + i + "].canRead() = " + files[i].canRead());
            if(i < files.length - 1) {
                System.out.println("--------------------------------------------------------------");
            }
        }
    }
    
}
