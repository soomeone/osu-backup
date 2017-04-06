package osubackup;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class ZipHandler {
	public static File makeZipFromFolder(final File Folder, String outputFolder, String ending) throws IOException {
		// All filed in folder will be added here
		ArrayList<File> files = new ArrayList<>();
		getAllFiles(files, Folder);

		// Create output folder if not existing
		File output = new File(outputFolder);
		output.mkdirs();

		// This file will be returned
		File outputzip = new File(output.getAbsoluteFile() + File.separator + Folder.getName() + ending);
		// System.out.println(outputzip.getAbsolutePath());

		FileOutputStream fout = new FileOutputStream(
				output.getAbsoluteFile() + File.separator + Folder.getName() + ending);
		ZipOutputStream zout = new ZipOutputStream(fout);
		
		for (File f : files) {
			String relativePath = f.getAbsolutePath().substring((int) Folder.getAbsolutePath().length() + 1);
			// System.out.println(relativePath);
			
			ZipEntry ze = new ZipEntry(relativePath);
			zout.putNextEntry(ze);
			
			byte[] file = Files.readAllBytes(f.toPath());
			zout.write(file, 0, file.length);
			
			zout.closeEntry();
		}
		zout.close();

		return outputzip;
	}

	private static void getAllFiles(ArrayList<File> files, File directory) {
		if (directory.isDirectory()) {
			for (File f : directory.listFiles()) {
				// All files in directory
				if (f.isDirectory())
					getAllFiles(files, f); // Get all files from subdir
				else {
					files.add(f);
					// System.out.println("Added file " + f.getPath());
				}
			}
		}
	}
}
