package com.test.common.service;

public interface FtpService {
    /**
     * Description: 向FTP服务器上传文件
     * @param filePath FTP服务器文件存放路径。例如分日期存放：/2015/01/01。文件的路径为basePath+filePath
     * @param filename 上传到FTP服务器上的文件名
     * @param localFileName 准备上传的本地的文件名称
     * @return 成功返回true，否则返回false
     */
    public boolean uploadFile(String filePath, String filename, String localFileName) throws Exception;
    /**
     * Description: 从FTP服务器下载文件
     * @param remotePath FTP服务器上的相对路径
     * @param fileName 要下载的文件名
     * @return
     */
    public boolean downloadFile(String remotePath, String fileName);
}
