package com.test.common.service.impl;

import com.test.common.entity.FtpConfig;
import com.test.common.service.FileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;

/**
 * Created by caiqi on 2016/11/23.
 */
@Service("fileService")
public class FileServiceImpl implements FileService {
    private Logger logger = LoggerFactory.getLogger(FileServiceImpl.class);
    @Autowired
    private FtpConfig ftpConfig;
    public boolean writeFile(String filename,String content) throws Exception {
        try {
            FileOutputStream fos = new FileOutputStream(ftpConfig.getFileLocalBasePath()+filename);
            fos.write(content.getBytes());
            fos.close();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
