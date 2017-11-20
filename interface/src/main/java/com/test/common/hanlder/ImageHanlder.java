package com.test.common.hanlder;

import com.test.common.entity.ImageModel;
import com.test.common.utils.ImageUtils;
import com.test.common.entity.ImageGroup;
import com.test.common.utils.FreemarkerUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ImageHanlder
{
  private static final String DEST_EXTENSION = "jpg";
  private static final String DEST_CONTENT_TYPE = "image/jpeg";
  private static String watermarkImagePath = "/Users/jtao/Desktop/imagetest/2.png";

  private static ImageUtils.WatermarkPosition watermarkPosition = ImageUtils.WatermarkPosition.no;
  private static int watermarkAlpha = 100;

  private static int largeProductImageWidth = 400;
  private static int largeProductImageHeight = 400;

  private static int mediumProductImageWidth = 200;
  private static int mediumProductImageHeight = 200;

  private static int thumbnailProductImageWidth = 100;
  private static int thumbnailProductImageHeight = 100;

  public static void upload(String path, File file, String contentType) {
    File destFile = new File(path);
    try
    {
      FileUtils.moveFile(file, destFile);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private static void addTask(String sourcePath, String largePath, String mediumPath, String thumbnailPath, File tempFile, String contentType, ImageGroup imageGroup, int destQuality)
  {
    try
    {
      String tempPath = System.getProperty("java.io.tmpdir");
      File watermarkFile = new File(watermarkImagePath);
      File largeTempFile = new File(tempPath + "/upload_" + UUID.randomUUID() + "." + "jpg");
      File mediumTempFile = new File(tempPath + "/upload_" + UUID.randomUUID() + "." + "jpg");
      File thumbnailTempFile = new File(tempPath + "/upload_" + UUID.randomUUID() + "." + "jpg");
      try {
        ImageUtils.zoom(tempFile, largeTempFile, "large", imageGroup, destQuality);
        ImageUtils.zoom(tempFile, mediumTempFile, "medium", imageGroup, destQuality);
        ImageUtils.zoom(tempFile, thumbnailTempFile, "thumbnail", imageGroup, destQuality);
        upload(sourcePath, tempFile, contentType);
        upload(largePath, largeTempFile, "image/jpeg");
        upload(mediumPath, mediumTempFile, "image/jpeg");
        upload(thumbnailPath, thumbnailTempFile, "image/jpeg");
      } finally {
        FileUtils.deleteQuietly(tempFile);
        FileUtils.deleteQuietly(largeTempFile);
        FileUtils.deleteQuietly(mediumTempFile);
        FileUtils.deleteQuietly(thumbnailTempFile);
      }
    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }

  private static void addTask(String sourcePath, String largePath, String mediumPath, String thumbnailPath, File tempFile, String contentType, ImageGroup imageGroup)
  {
    try
    {
      String tempPath = System.getProperty("java.io.tmpdir");
      File watermarkFile = new File(watermarkImagePath);
      File largeTempFile = new File(tempPath + "/upload_" + UUID.randomUUID() + "." + "jpg");
      File mediumTempFile = new File(tempPath + "/upload_" + UUID.randomUUID() + "." + "jpg");
      File thumbnailTempFile = new File(tempPath + "/upload_" + UUID.randomUUID() + "." + "jpg");
      try {
        ImageUtils.zoom(tempFile, largeTempFile, "large", imageGroup, 88);
        ImageUtils.zoom(tempFile, mediumTempFile, "medium", imageGroup, 88);
        ImageUtils.zoom(tempFile, thumbnailTempFile, "thumbnail", imageGroup, 88);
        upload(sourcePath, tempFile, contentType);
        upload(largePath, largeTempFile, "image/jpeg");
        upload(mediumPath, mediumTempFile, "image/jpeg");
        upload(thumbnailPath, thumbnailTempFile, "image/jpeg");
      } finally {
        FileUtils.deleteQuietly(tempFile);
        FileUtils.deleteQuietly(largeTempFile);
        FileUtils.deleteQuietly(mediumTempFile);
        FileUtils.deleteQuietly(thumbnailTempFile);
      }
    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }

  private static void addTask(String sourcePath, String largePath, String mediumPath, String thumbnailPath, File tempFile, String contentType)
  {
    try {
      String tempPath = System.getProperty("java.io.tmpdir");
      File watermarkFile = new File(watermarkImagePath);
      File largeTempFile = new File(tempPath + "/upload_" + UUID.randomUUID() + "." + "jpg");
      File mediumTempFile = new File(tempPath + "/upload_" + UUID.randomUUID() + "." + "jpg");
      File thumbnailTempFile = new File(tempPath + "/upload_" + UUID.randomUUID() + "." + "jpg");
      try {
        ImageUtils.zoom(tempFile, largeTempFile, largeProductImageWidth, largeProductImageHeight);

        ImageUtils.zoom(tempFile, mediumTempFile, mediumProductImageWidth, mediumProductImageHeight);

        ImageUtils.zoom(tempFile, thumbnailTempFile, thumbnailProductImageWidth, thumbnailProductImageHeight);
        upload(sourcePath, tempFile, contentType);
        upload(largePath, largeTempFile, "image/jpeg");
        upload(mediumPath, mediumTempFile, "image/jpeg");
        upload(thumbnailPath, thumbnailTempFile, "image/jpeg");
      } finally {
        FileUtils.deleteQuietly(tempFile);
        FileUtils.deleteQuietly(largeTempFile);
        FileUtils.deleteQuietly(mediumTempFile);
        FileUtils.deleteQuietly(thumbnailTempFile);
      }
    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }

  public static ImageModel build(MultipartFile multipartFile, String imagePath, String requertPath)
  {
    ImageModel imageModel = new ImageModel();
    try {
      Map model = new HashMap();
      model.put("uuid", UUID.randomUUID().toString());
      String uploadPath = FreemarkerUtils.process(imagePath, model, null);
      String uuid = UUID.randomUUID().toString();
      String sourcePath = uploadPath + uuid + "-source." + FilenameUtils.getExtension(multipartFile.getOriginalFilename());
      String largePath = uploadPath + uuid + "-large." + "jpg";
      String mediumPath = uploadPath + uuid + "-medium." + "jpg";
      String thumbnailPath = uploadPath + uuid + "-thumbnail." + "jpg";
      String sourcePathRequest = requertPath + uuid + "-source." + FilenameUtils.getExtension(multipartFile.getOriginalFilename());
      String largePathRequest = requertPath + uuid + "-large." + "jpg";
      String mediumPathRequest = requertPath + uuid + "-medium." + "jpg";
      String thumbnailPathRequest = requertPath + uuid + "-thumbnail." + "jpg";

      File tempFile = new File(System.getProperty("java.io.tmpdir") + "/upload_" + UUID.randomUUID() + ".tmp");
      if (!tempFile.getParentFile().exists()) {
        tempFile.getParentFile().mkdirs();
      }
      multipartFile.transferTo(tempFile);

      imageModel.setLargePath(largePath);
      imageModel.setLargePathRequest(largePathRequest);
      imageModel.setMediumPath(mediumPath);
      imageModel.setMediumPathRequest(mediumPathRequest);
      imageModel.setSourcePath(sourcePath);
      imageModel.setSourcePathRequest(sourcePathRequest);
      imageModel.setThumbnailPath(thumbnailPath);
      imageModel.setThumbnailPathRequest(thumbnailPathRequest);
      addTask(sourcePath, largePath, mediumPath, thumbnailPath, tempFile, multipartFile.getContentType());
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
    return imageModel;
  }

  public static ImageGroup build(MultipartFile multipartFile, String imagePath, String requertPath, long largeWidth, long largeHeiht, long mediumWidth, long mediumHeiht, long thumbnailWidth, long thumbnailHeiht)
  {
    ImageGroup imageGroup = new ImageGroup();
    if ((multipartFile != null) && (!multipartFile.isEmpty())) {
      try {
        Map model = new HashMap();
        model.put("uuid", UUID.randomUUID().toString());
        String uploadPath = FreemarkerUtils.process(imagePath, model, null);
        String uuid = UUID.randomUUID().toString();
        String sourcePath = uploadPath + uuid + "-source." + FilenameUtils.getExtension(multipartFile.getOriginalFilename());
        String largePath = uploadPath + uuid + "-large." + "jpg";
        String mediumPath = uploadPath + uuid + "-medium." + "jpg";
        String thumbnailPath = uploadPath + uuid + "-thumbnail." + "jpg";
        String sourcePathRequest = requertPath + uuid + "-source." + FilenameUtils.getExtension(multipartFile.getOriginalFilename());
        String largePathRequest = requertPath + uuid + "-large." + "jpg";
        String mediumPathRequest = requertPath + uuid + "-medium." + "jpg";
        String thumbnailPathRequest = requertPath + uuid + "-thumbnail." + "jpg";

        File tempFile = new File(System.getProperty("java.io.tmpdir") + "/upload_" + UUID.randomUUID() + ".tmp");
        if (!tempFile.getParentFile().exists()) {
          tempFile.getParentFile().mkdirs();
        }
        multipartFile.transferTo(tempFile);

        imageGroup.setSourceUrl(sourcePathRequest);
        imageGroup.setLargeUrl(largePathRequest);
        imageGroup.setMediumUrl(mediumPathRequest);
        imageGroup.setThumbnailUrl(thumbnailPathRequest);

        imageGroup.setLargeHeight(largeHeiht);
        imageGroup.setLargeWidth(largeWidth);

        imageGroup.setMediumHeight(mediumHeiht);
        imageGroup.setMediumWidth(mediumWidth);

        imageGroup.setThumbnailHeight(thumbnailHeiht);
        imageGroup.setThumbnailWidth(thumbnailWidth);

        addTask(sourcePath, largePath, mediumPath, thumbnailPath, tempFile, multipartFile.getContentType(), imageGroup);
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
    return imageGroup;
  }

  public static ImageGroup build(MultipartFile multipartFile, String imagePath, String requertPath, long largeWidth, long largeHeiht, long mediumWidth, long mediumHeiht, long thumbnailWidth, long thumbnailHeiht, int destQuality)
  {
    ImageGroup imageGroup = new ImageGroup();
    if ((multipartFile != null) && (!multipartFile.isEmpty())) {
      try {
        Map model = new HashMap();
        model.put("uuid", UUID.randomUUID().toString());
        String uploadPath = FreemarkerUtils.process(imagePath, model, null);
        String uuid = UUID.randomUUID().toString();
        String sourcePath = uploadPath + uuid + "-source." + FilenameUtils.getExtension(multipartFile.getOriginalFilename());
        String largePath = uploadPath + uuid + "-large." + "jpg";
        String mediumPath = uploadPath + uuid + "-medium." + "jpg";
        String thumbnailPath = uploadPath + uuid + "-thumbnail." + "jpg";
        String sourcePathRequest = requertPath + uuid + "-source." + FilenameUtils.getExtension(multipartFile.getOriginalFilename());
        String largePathRequest = requertPath + uuid + "-large." + "jpg";
        String mediumPathRequest = requertPath + uuid + "-medium." + "jpg";
        String thumbnailPathRequest = requertPath + uuid + "-thumbnail." + "jpg";

        File tempFile = new File(System.getProperty("java.io.tmpdir") + "/upload_" + UUID.randomUUID() + ".tmp");
        if (!tempFile.getParentFile().exists()) {
          tempFile.getParentFile().mkdirs();
        }
        multipartFile.transferTo(tempFile);

        imageGroup.setSourceUrl(sourcePathRequest);
        imageGroup.setLargeUrl(largePathRequest);
        imageGroup.setMediumUrl(mediumPathRequest);
        imageGroup.setThumbnailUrl(thumbnailPathRequest);

        imageGroup.setLargeHeight(largeHeiht);
        imageGroup.setLargeWidth(largeWidth);

        imageGroup.setMediumHeight(mediumHeiht);
        imageGroup.setMediumWidth(mediumWidth);

        imageGroup.setThumbnailHeight(thumbnailHeiht);
        imageGroup.setThumbnailWidth(thumbnailWidth);

        addTask(sourcePath, largePath, mediumPath, thumbnailPath, tempFile, multipartFile.getContentType(), imageGroup, destQuality);
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
    return imageGroup;
  }

  public static void main(String[] args) throws IOException
  {
//    System.out.println(BeanUtil.apply(ImageGroup.class, "imageGroup", ImageGroup.class, "imageGroup"));
  }
}