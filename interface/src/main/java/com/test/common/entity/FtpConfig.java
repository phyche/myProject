package com.test.common.entity;

public class FtpConfig {
    /**
     * FTP服务器hostname
     */
    private String host;
    /**
     * FTP服务器port
     */
    private String port;
    /**
     * FTP登录账号
     */
    private String username;
    /**
     * FTP登录密码
     */
    private String password;
    /**
     * FTP服务器基础目录
     */
    private String basePath;
    /**
     * 用于上传文件的本地路径
     */
    private String fileLocalBasePath;

    public String getFileLocalBasePath() {
        return fileLocalBasePath;
    }

    public void setFileLocalBasePath(String fileLocalBasePath) {
        this.fileLocalBasePath = fileLocalBasePath;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getBasePath() {
        return basePath;
    }

    public void setBasePath(String basePath) {
        this.basePath = basePath;
    }

    @Override
    public String toString() {
        return "FtpConfig{" +
                "host='" + host + '\'' +
                ", port='" + port + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", basePath='" + basePath + '\'' +
                '}';
    }
}
