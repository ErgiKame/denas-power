package main;

import java.io.File;
import java.io.IOException;
import java.net.SocketException;
import java.net.URISyntaxException;
import java.security.CodeSource;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;


public class global {
	
	public static final String TO_FILE = "file:///"; 
	public static String INSTALL_DIR = getTargetPath();
	public static final String INSTALL_DIR_IMAGES = INSTALL_DIR + "/img/";
	
	public static String getTargetPath() {
		CodeSource codeSource = Main.class.getProtectionDomain().getCodeSource();
		File jarFile = null;
		try {
			jarFile = new File(codeSource.getLocation().toURI().getPath());
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		String jarDir = jarFile.getParentFile().getPath();
		return jarDir;
	}
	
	//your filezilla server ip instead of 127.0.0.1
	public static final String server = "127.0.0.1";
	public static final int port = 21;
	public static final String user = "user";
	public static final String pass = "";
	public static FTPClient ftpClient = new FTPClient();
	
	public static void getFtpClient() throws SocketException, IOException {
		ftpClient.connect(server, port);
		ftpClient.login(user, pass);
		ftpClient.enterLocalPassiveMode();

		ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
	}
}
