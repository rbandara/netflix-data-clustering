package org.nc.filetransfer;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;

public class RemoteFileDownloader {
  /*  public static void main(String[] args) {
        String SFTPHOST = "hercules.uwb.edu";
        int    SFTPPORT = 22;
        String SFTPUSER = "ravindra";
        String SFTPPASS = "";
        String SFTPWORKINGDIR = "/data/ravindra/binary/clusters";

        Session 	session 	= null;
        Channel 	channel 	= null;
        ChannelSftp channelSftp = null;

        try{
            JSch jsch = new JSch();
            session = jsch.getSession(SFTPUSER,SFTPHOST,SFTPPORT);
            session.setPassword(SFTPPASS);
            java.util.Properties config = new java.util.Properties();
            config.put("StrictHostKeyChecking", "no");
            session.setConfig(config);
            session.connect();
            channel = session.openChannel("sftp");
            channel.connect();
            channelSftp = (ChannelSftp)channel;
            channelSftp.cd(SFTPWORKINGDIR);
            channelSftp.
            byte[] buffer = new byte[1024];
            BufferedInputStream bis = new BufferedInputStream(channelSftp.get("Test.java"));
            File newFile = new File("C:/Test.java");
            OutputStream os = new FileOutputStream(newFile);
            BufferedOutputStream bos = new BufferedOutputStream(os);
            int readCount;
//System.out.println("Getting: " + theLine);
            while( (readCount = bis.read(buffer)) &gt; 0) {
                System.out.println("Writing: " );
                bos.write(buffer, 0, readCount);
            }
            bis.close();
            bos.close();
        }catch(Exception ex){
            ex.printStackTrace();
        }

    }

    public static void main(String[] args) {
        String sftpFolder = "/test";
        String toLocalFolder = "C:\\myfiles\\project\\sftpProject\\writeToFolder";
        try {
            int status = SFTP.getFolder(sftpFolder, toLocalFolder, new BatchTransferProgressDefault(),"sftp.myhost.com", "sftp", "pass");
            if(FileTransferStatus.SUCCESS == status){
                System.out.println(sftpFolder + " got downloaded successfully to  folder "+toLocalFolder);
            }
            else if(FileTransferStatus.FAILURE == status){
                System.out.println("Fail to download  to  folder "+toLocalFolder);
            }
        } catch (FileTransferException e) {
            e.printStackTrace();
        }
    }*/
}
