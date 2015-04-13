import java.io.IOException;
import java.nio.file.*;

public class NewFilesType {

	public static void main(String[] args) throws IOException {

		Path path = Paths.get(".");
		
		System.out.println("path = " + path);

		System.out.println("Files.isDirectory(path) = "
				+ Files.isDirectory(path));
		
		System.out.println("Files.isRegularFile(path) = "
				+ Files.isRegularFile(path));

		// On Windows, files are always readable.
		System.out
				.println("Files.isReadable(path) = " + Files.isReadable(path));

		listContents(path);

		path = path.resolve("sampleDirectory");
		
		listContents(path);
	}

	private static void listContents(Path dir) throws IOException {

		System.out
				.println("=================================================================");

		DirectoryStream<Path> entries = Files.newDirectoryStream(dir);
		
		for (Path entry : entries) {			
			System.out.println("entry = " + entry);
			System.out.println("Files.isDirectory(entry) = "
					+ Files.isDirectory(entry));
			System.out.println("Files.isRegularFile(entry) = "
					+ Files.isRegularFile(entry));
			System.out.println("Files.isReadable(entry) = "
					+ Files.isReadable(entry));
			System.out
					.println("--------------------------------------------------------------");
		}
		
		entries.close();
	}

}
