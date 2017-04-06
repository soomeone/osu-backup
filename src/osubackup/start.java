package osubackup;

import java.io.File;
import java.io.IOException;

public class start {
	public static void main(String[] args) {
		try {
			if (args.length == 2) {
				String songpath = args[0];
				String output = args[1];

				// Get folder
				File songdir = new File(songpath);

				// Get number of folders for percentage
				int subfolders = songdir.listFiles().length;
				int currentfolder = 1;
				int lastpercent = 0;

				System.out.println("Started backuping " + subfolders + " maps");

				// Pack every map folder
				for (File subfolder : songdir.listFiles()) {
					if (subfolder.isDirectory()) {
						ZipHandler.makeZipFromFolder(subfolder, output, ".osz");

						// Get current percentage
						int percentage = (int) ((float) currentfolder / (float) subfolders * 100);
						currentfolder++;
						if (percentage != lastpercent) {
							System.out.println(percentage + "% - " + currentfolder + "/" + subfolders);
							lastpercent = percentage;
						}

						System.out.println("Exported " + subfolder.getName());
					}
				}

				System.out.println("Finished exporting");
			}
			else {
				System.out.println("Wrong format: osubackup.jar <song folder> <output folder>");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
