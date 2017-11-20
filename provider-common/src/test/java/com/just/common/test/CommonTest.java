/**   
* @Title: UserVipTest.java 
* @Package com.cyber.vip.test 
* @Description: TODO(用一句话描述该文件做什么) 
* @author cssuger@163.com   
* @date 2016年6月21日 上午11:13:49 
* @version V1.0   
*/
package test.java.com.just.common.test;

import com.alibaba.fastjson.JSONObject;
import com.test.ExcelHelper.vo.JgReconciliationVO;
import com.test.common.service.ExcelService;
import com.test.common.service.FileService;
import com.test.common.service.FtpService;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;

/**
 * @ClassName: UserVipTest 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @author cssuger@163.com 
 * @date 2016年6月21日 上午11:13:49 
 *  
 */
public class CommonTest {
    ApplicationContext ctx = null;
    FtpService ftpService = null;
    FileService fileService = null;
    ExcelService excelService = null;
    @Before
    public void Init(){
        List<String> resourceList = new LinkedList<>();
        resourceList.add("spring/applicationContext.xml");
        try {
            List<String> packKey = new LinkedList<>();
            packKey.add("dev");
            packKey.add("justgotest");
            packKey.add("jdcloudtest");

            packKey.forEach(key->{
                ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();      //将首先通过
                try {
                    Resource[] tmpRes = resolver.getResources("classpath*:"+key+"/*.xml");
                    if(tmpRes.length>0){
                        for(Resource resTmp : tmpRes){
                            resourceList.add(key+"/"+resTmp.getFilename());
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(resourceList.toArray(new String[0]));
            ctx = context;
        }catch (Exception e){
            e.printStackTrace();
        }
        ftpService =(FtpService) ctx.getBean("ftpService");
        fileService =(FileService) ctx.getBean("fileService");
        excelService =(ExcelService) ctx.getBean("excelService");
    }
    @Test
    public void testUploadFile(){
        try {
            boolean res = ftpService.uploadFile("ddmmz","abctestfile","offline_FtnInfo.txt");
            System.out.println("返回参数："+ JSONObject.toJSONString(res));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Test
    public void testDownloadFile(){
        try {
            boolean res = ftpService.downloadFile("/weconex/ddmmz","AA2017101205552810000023FFFF00101");
            System.out.println("返回参数："+ JSONObject.toJSONString(res));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Test
    public void testWriteFile(){
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            String filename = sdf.format(Calendar.getInstance().getTime());
            StringBuffer sb = new StringBuffer();
            for(int i=0;i<100;i++){
                sb.append("测试数据行数："+i);
                sb.append("\r\n");
            }
            boolean res = fileService.writeFile(filename,sb.toString());
            System.out.println("返回参数："+ JSONObject.toJSONString(res));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Test
    public void testWriteAndUpload(){
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            String filename = sdf.format(Calendar.getInstance().getTime());
            StringBuffer sb = new StringBuffer();
            for(int i=0;i<100;i++){
                sb.append("测试数据行数："+i);
                sb.append("\r\n");
            }
            boolean res = fileService.writeFile(filename,sb.toString());
            System.out.println("生成文件："+ JSONObject.toJSONString(res));
            if(res){
                System.out.println("开始上传===============");
                res = ftpService.uploadFile("ddmmz","ftpfile",filename);
                System.out.println("上传ftp文件："+ JSONObject.toJSONString(res));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Test
    public void testJgReconciliationExcelToList(){
        try {
            String filename="商户对账单.xls";
            String sheetName="对账单";
            List<JgReconciliationVO> jgReconciliationVOS = excelService.jgReconciliationExcelToList(filename, sheetName);
            System.out.println("获取对账集合："+ JSONObject.toJSONString(jgReconciliationVOS));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
