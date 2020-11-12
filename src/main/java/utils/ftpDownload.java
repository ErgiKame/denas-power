package utils;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.SocketException;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;

import main.global;

public class ftpDownload {


	public void downlaodFiles() throws SocketException, IOException {
		global.getFtpClient();
		global.ftpClient.enterLocalPassiveMode();

		String remoteDirPath = "/";
		String saveDirPath = global.INSTALL_DIR_IMAGES;
		downloadDirectory(global.ftpClient, remoteDirPath, "", saveDirPath);
	}

	public static void downloadDirectory(FTPClient ftpClient, String parentDir,
			String currentDir, String saveDir) throws IOException {
		String dirToList = parentDir;
		if (!currentDir.equals("")) {
			dirToList += "/" + currentDir;
		}

		FTPFile[] subFiles = ftpClient.listFiles(dirToList);
		if (subFiles != null && subFiles.length > 0) {
			for (FTPFile aFile : subFiles) {
				String currentFileName = aFile.getName();
				if (currentFileName.equals(".") || currentFileName.equals("..")) {
					continue;
				}
				String filePath = parentDir + "/" + currentDir + "/"
						+ currentFileName;
				if (currentDir.equals("")) {
					filePath = parentDir + "/" + currentFileName;
				}

				String newDirPath = saveDir + parentDir + File.separator
						+ currentDir + File.separator + currentFileName;
				if (currentDir.equals("")) {
					newDirPath = saveDir + parentDir + File.separator
							+ currentFileName;
				}
				
				if(!checkIfFileExist(filePath)) {
					boolean success = downloadSingleFile(ftpClient, filePath,
							newDirPath);
					System.out.println(success);
				}

			}

		}
	}

	private static boolean checkIfFileExist(String fileName) throws IOException {
		String pathNames[];
		File f = new File(global.INSTALL_DIR_IMAGES);
		pathNames = f.list();
		String newFileName = fileName.replace("//", "");
		for(String pathName : pathNames) {
			if(newFileName.equals(pathName))
				return true;
		}
		return false;
	}

	public static boolean downloadSingleFile(FTPClient ftpClient,
			String remoteFilePath, String savePath) throws IOException {
		File downloadFile = new File(savePath);

		File parentDir = downloadFile.getParentFile();
		if (!parentDir.exists()) {
			parentDir.mkdir();
		}

		OutputStream outputStream = new BufferedOutputStream(
				new FileOutputStream(downloadFile));
		try {
			ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
			return ftpClient.retrieveFile(remoteFilePath, outputStream);
		} catch (IOException ex) {
			throw ex;
		} finally {
			if (outputStream != null) {
				outputStream.close();
			}
		}
	}
}
