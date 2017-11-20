package com.test.common.service.impl;

import com.test.common.entity.FtpConfig;
import com.test.common.service.FtpService;
import org.apache.commons.net.ftp.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;

/**
 * Created by caiqi on 2016/11/23.
 */
@Service("ftpService")
public class FtpServiceImpl implements FtpService {
    private Logger logger = LoggerFactory.getLogger(FtpServiceImpl.class);
    @Autowired
    private FtpConfig ftpConfig;
    /**
     * Description: 向FTP服务器上传文件
     * @param filePath FTP服务器文件存放路径。例如分日期存放：/2015/01/01。文件的路径为basePath+filePath
     * @param filename 上传到FTP服务器上的文件名
     * @param localFileName 准备上传的本地的文件名称
     * @return 成功返回true，否则返回false
     */
    public boolean uploadFile(String filePath, String filename, String localFileName) throws Exception {
        boolean result = false;
        FTPClient ftp = new FTPClient();
        try {
            InputStream input = new FileInputStream(ftpConfig.getFileLocalBasePath()+localFileName);
            int reply;
            ftp.setControlEncoding("UTF-8");
            FTPClientConfig conf = new FTPClientConfig(FTPClientConfig.SYST_UNIX);
            conf.setServerLanguageCode("zh");
            ftp.configure(conf);
            ftp.connect(ftpConfig.getHost(), Integer.valueOf(ftpConfig.getPort()));// 连接FTP服务器
            // 如果采用默认端口，可以使用ftp.connect(host)的方式直接连接FTP服务器
            ftp.login(ftpConfig.getUsername(), ftpConfig.getPassword());// 登录
            reply = ftp.getReplyCode();
            if (!FTPReply.isPositiveCompletion(reply)) {
                ftp.disconnect();
                return result;
            }
            //切换到上传目录
            if (!ftp.changeWorkingDirectory(ftpConfig.getBasePath()+filePath)) {
                //如果目录不存在创建目录
                String[] dirs = filePath.split("/");
                String tempPath = ftpConfig.getBasePath();
                for (String dir : dirs) {
                    if (null == dir || "".equals(dir)) continue;
                    tempPath += "/" + dir;
                    if (!ftp.changeWorkingDirectory(tempPath)) {
                        if (!ftp.makeDirectory(tempPath)) {
                            return result;
                        } else {
                            ftp.changeWorkingDirectory(tempPath);
                        }
                    }
                }
            }
            //设置上传文件的类型为二进制类型
            ftp.setFileType(FTP.BINARY_FILE_TYPE);
            //上传文件
            if (!ftp.storeFile(filename, input)) {
                return result;
            }
            input.close();
            ftp.logout();
            result = true;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (ftp.isConnected()) {
                try {
                    ftp.disconnect();
                } catch (IOException ioe) {
                }
            }
        }
        return result;
    }

    /**
     * Description: 从FTP服务器下载文件
     * @param remotePath FTP服务器上的相对路径
     * @param fileName 要下载的文件名
     * @return
     */
    public boolean downloadFile(String remotePath, String fileName) {
        boolean result = false;
        FTPClient ftp = new FTPClient();
        try {
            int reply;
            ftp.setControlEncoding("UTF-8");
            FTPClientConfig conf = new FTPClientConfig(FTPClientConfig.SYST_UNIX);
            conf.setServerLanguageCode("zh");
            ftp.configure(conf);
            ftp.connect(ftpConfig.getHost(), Integer.valueOf(ftpConfig.getPort()));// 连接FTP服务器
            // 如果采用默认端口，可以使用ftp.connect(host)的方式直接连接FTP服务器
            ftp.login(ftpConfig.getUsername(), ftpConfig.getPassword());// 登录
            reply = ftp.getReplyCode();
            if (!FTPReply.isPositiveCompletion(reply)) {
                ftp.disconnect();
                return result;
            }
            ftp.changeWorkingDirectory(remotePath);// 转移到FTP服务器目录
            FTPFile[] fs = ftp.listFiles();
            for (FTPFile ff : fs) {
                if (ff.getName().equals(fileName)) {
                    File localFile = new File(ftpConfig.getFileLocalBasePath() + "/" + ff.getName());

                    OutputStream is = new FileOutputStream(localFile);
                    ftp.retrieveFile(ff.getName(), is);
                    is.close();
                }
            }

            ftp.logout();
            result = true;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (ftp.isConnected()) {
                try {
                    ftp.disconnect();
                } catch (IOException ioe) {
                }
            }
        }
        return result;
    }
}
