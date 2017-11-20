package com.test.common.utils;

import com.test.common.entity.ImageGroup;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.im4java.core.*;
import org.springframework.util.Assert;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public final class ImageUtils
{
  private static Type type = Type.auto;
  private static String graphicsMagickPath;
  private static String imageMagickPath;
  private static final Color BACKGROUND_COLOR = Color.white;
  private static final int DEST_QUALITY = 88;

  public static void zoom(File srcFile, File destFile, String imageType, ImageGroup imageGroup, int destQuality)
  {
    Assert.notNull(srcFile);
    Assert.notNull(destFile);
    long tDestWidth = 0L; long tDestHeight = 0L;
    if ("large".equals(imageType)) {
      tDestWidth = imageGroup.getLargeWidth();
      tDestHeight = imageGroup.getLargeHeight();
    }
    else if ("medium".equals(imageType)) {
      tDestWidth = imageGroup.getMediumWidth();
      tDestHeight = imageGroup.getMediumHeight();
    }
    else if ("thumbnail".equals(imageType)) {
      tDestWidth = imageGroup.getThumbnailWidth();
      tDestHeight = imageGroup.getThumbnailHeight();
    }
    if ((tDestWidth < 1L) && (tDestHeight < 1L)) {
      throw new RuntimeException("图片压缩必须指定高宽!");
    }
    BufferedImage srcBufferedImage;
    try
    {
      srcBufferedImage = ImageIO.read(srcFile);
    } catch (IOException e1) {
      throw new RuntimeException("获取原图高宽比失败!");
    }
    int srcWidth = srcBufferedImage.getWidth();
    int srcHeight = srcBufferedImage.getHeight();

    imageGroup.setSourceWidth(srcWidth);
    imageGroup.setSourceHeight(srcHeight);

    int destWidth = Long.valueOf(tDestWidth > 0L ? tDestWidth : Math.round(srcWidth * 1.0D / srcHeight * tDestHeight)).intValue();
    int destHeight = Long.valueOf(tDestHeight > 0L ? tDestHeight : Math.round(srcHeight * 1.0D / srcWidth * tDestWidth)).intValue();

    if ("large".equals(imageType)) {
      imageGroup.setLargeWidth(destWidth);
      imageGroup.setLargeHeight(destHeight);
    }
    else if ("medium".equals(imageType)) {
      imageGroup.setMediumWidth(destWidth);
      imageGroup.setMediumHeight(destHeight);
    }
    else if ("thumbnail".equals(imageType)) {
      imageGroup.setThumbnailWidth(destWidth);
      imageGroup.setThumbnailHeight(destHeight);
    }
    if (type == Type.jdk) {
      Graphics2D graphics2D = null;
      ImageOutputStream imageOutputStream = null;
      ImageWriter imageWriter = null;
      try {
        int width = destWidth;
        int height = destHeight;

        if (srcHeight >= srcWidth)
          width = (int)Math.round(destHeight * 1.0D / srcHeight * srcWidth);
        else {
          height = (int)Math.round(destWidth * 1.0D / srcWidth * srcHeight);
        }
        BufferedImage destBufferedImage = new BufferedImage(destWidth, destHeight, 1);
        graphics2D = destBufferedImage.createGraphics();

        graphics2D.clearRect(0, 0, destWidth, destHeight);
        graphics2D.drawImage(srcBufferedImage.getScaledInstance(width, height, 4), destWidth / 2 - width / 2, destHeight / 2 - height / 2, null);

        imageOutputStream = ImageIO.createImageOutputStream(destFile);
        imageWriter = (ImageWriter)ImageIO.getImageWritersByFormatName(FilenameUtils.getExtension(destFile.getName())).next();
        imageWriter.setOutput(imageOutputStream);
        ImageWriteParam imageWriteParam = imageWriter.getDefaultWriteParam();
        imageWriteParam.setCompressionMode(2);
        imageWriteParam.setCompressionQuality((float)(destQuality / 100.0D));
        imageWriter.write(null, new IIOImage(destBufferedImage, null, null), imageWriteParam);
        imageOutputStream.flush();
      } catch (IOException e) {
        e.printStackTrace();
      } finally {
        if (graphics2D != null) {
          graphics2D.dispose();
        }
        if (imageWriter != null) {
          imageWriter.dispose();
        }
        if (imageOutputStream != null)
          try {
            imageOutputStream.close();
          } catch (IOException e) {
          }
      }
    }
    else {
      IMOperation operation = new IMOperation();
      operation.thumbnail(Integer.valueOf(destWidth), Integer.valueOf(destHeight));
      operation.gravity("center");
      operation.background(toHexEncoding(BACKGROUND_COLOR));
      operation.extent(Integer.valueOf(destWidth), Integer.valueOf(destHeight));
      operation.quality(Double.valueOf(destQuality));
      operation.addImage(new String[] { srcFile.getPath() });
      operation.addImage(new String[] { destFile.getPath() });
      if (type == Type.graphicsMagick) {
        ConvertCmd convertCmd = new ConvertCmd(true);
        if (graphicsMagickPath != null)
          convertCmd.setSearchPath(graphicsMagickPath);
        try
        {
          convertCmd.run(operation, new Object[0]);
        } catch (IOException e) {
          e.printStackTrace();
        } catch (InterruptedException e) {
          e.printStackTrace();
        } catch (IM4JavaException e) {
          e.printStackTrace();
        }
      } else {
        ConvertCmd convertCmd = new ConvertCmd(false);
        if (imageMagickPath != null)
          convertCmd.setSearchPath(imageMagickPath);
        try
        {
          convertCmd.run(operation, new Object[0]);
        } catch (IOException e) {
          e.printStackTrace();
        } catch (InterruptedException e) {
          e.printStackTrace();
        } catch (IM4JavaException e) {
          e.printStackTrace();
        }
      }
    }
  }

  public static void zoom(File srcFile, File destFile, int destWidth, int destHeight)
  {
    Assert.notNull(srcFile);
    Assert.notNull(destFile);
    Assert.state(destWidth > 0);
    Assert.state(destHeight > 0);
    if (type == Type.jdk) {
      Graphics2D graphics2D = null;
      ImageOutputStream imageOutputStream = null;
      ImageWriter imageWriter = null;
      try {
        BufferedImage srcBufferedImage = ImageIO.read(srcFile);
        int srcWidth = srcBufferedImage.getWidth();
        int srcHeight = srcBufferedImage.getHeight();
        int width = destWidth;
        int height = destHeight;
        if (srcHeight >= srcWidth)
          width = (int)Math.round(destHeight * 1.0D / srcHeight * srcWidth);
        else {
          height = (int)Math.round(destWidth * 1.0D / srcWidth * srcHeight);
        }
        BufferedImage destBufferedImage = new BufferedImage(destWidth, destHeight, 1);
        graphics2D = destBufferedImage.createGraphics();
        graphics2D.setBackground(BACKGROUND_COLOR);
        graphics2D.clearRect(0, 0, destWidth, destHeight);
        graphics2D.drawImage(srcBufferedImage.getScaledInstance(width, height, 4), destWidth / 2 - width / 2, destHeight / 2 - height / 2, null);

        imageOutputStream = ImageIO.createImageOutputStream(destFile);
        imageWriter = (ImageWriter)ImageIO.getImageWritersByFormatName(FilenameUtils.getExtension(destFile.getName())).next();
        imageWriter.setOutput(imageOutputStream);
        ImageWriteParam imageWriteParam = imageWriter.getDefaultWriteParam();
        imageWriteParam.setCompressionMode(2);
        imageWriteParam.setCompressionQuality(0.88F);
        imageWriter.write(null, new IIOImage(destBufferedImage, null, null), imageWriteParam);
        imageOutputStream.flush();
      } catch (IOException e) {
        e.printStackTrace();
      } finally {
        if (graphics2D != null) {
          graphics2D.dispose();
        }
        if (imageWriter != null) {
          imageWriter.dispose();
        }
        if (imageOutputStream != null)
          try {
            imageOutputStream.close();
          } catch (IOException e) {
          }
      }
    }
    else {
      IMOperation operation = new IMOperation();
      operation.thumbnail(Integer.valueOf(destWidth), Integer.valueOf(destHeight));
      operation.gravity("center");
      operation.background(toHexEncoding(BACKGROUND_COLOR));
      operation.extent(Integer.valueOf(destWidth), Integer.valueOf(destHeight));
      operation.quality(Double.valueOf(88.0D));
      operation.addImage(new String[] { srcFile.getPath() });
      operation.addImage(new String[] { destFile.getPath() });
      if (type == Type.graphicsMagick) {
        ConvertCmd convertCmd = new ConvertCmd(true);
        if (graphicsMagickPath != null)
          convertCmd.setSearchPath(graphicsMagickPath);
        try
        {
          convertCmd.run(operation, new Object[0]);
        } catch (IOException e) {
          e.printStackTrace();
        } catch (InterruptedException e) {
          e.printStackTrace();
        } catch (IM4JavaException e) {
          e.printStackTrace();
        }
      } else {
        ConvertCmd convertCmd = new ConvertCmd(false);
        if (imageMagickPath != null)
          convertCmd.setSearchPath(imageMagickPath);
        try
        {
          convertCmd.run(operation, new Object[0]);
        } catch (IOException e) {
          e.printStackTrace();
        } catch (InterruptedException e) {
          e.printStackTrace();
        } catch (IM4JavaException e) {
          e.printStackTrace();
        }
      }
    }
  }

  public static void addWatermark(File srcFile, File destFile, File watermarkFile, WatermarkPosition watermarkPosition, int alpha)
  {
    Assert.notNull(srcFile);
    Assert.notNull(destFile);
    Assert.state(alpha >= 0);
    Assert.state(alpha <= 100);
    if ((watermarkFile == null) || (!watermarkFile.exists()) || (watermarkPosition == null) || (watermarkPosition == WatermarkPosition.no)) {
      try {
        FileUtils.copyFile(srcFile, destFile);
      } catch (IOException e) {
        e.printStackTrace();
      }
      return;
    }
    if (type == Type.jdk) {
      Graphics2D graphics2D = null;
      ImageOutputStream imageOutputStream = null;
      ImageWriter imageWriter = null;
      try {
        BufferedImage srcBufferedImage = ImageIO.read(srcFile);
        int srcWidth = srcBufferedImage.getWidth();
        int srcHeight = srcBufferedImage.getHeight();
        BufferedImage destBufferedImage = new BufferedImage(srcWidth, srcHeight, 1);
        graphics2D = destBufferedImage.createGraphics();
        graphics2D.setBackground(BACKGROUND_COLOR);
        graphics2D.clearRect(0, 0, srcWidth, srcHeight);
        graphics2D.drawImage(srcBufferedImage, 0, 0, null);
        graphics2D.setComposite(AlphaComposite.getInstance(10, alpha / 100.0F));

        BufferedImage watermarkBufferedImage = ImageIO.read(watermarkFile);
        int watermarkImageWidth = watermarkBufferedImage.getWidth();
        int watermarkImageHeight = watermarkBufferedImage.getHeight();
        int x = srcWidth - watermarkImageWidth;
        int y = srcHeight - watermarkImageHeight;
        if (watermarkPosition == WatermarkPosition.topLeft) {
          x = 0;
          y = 0;
        } else if (watermarkPosition == WatermarkPosition.topRight) {
          x = srcWidth - watermarkImageWidth;
          y = 0;
        } else if (watermarkPosition == WatermarkPosition.center) {
          x = (srcWidth - watermarkImageWidth) / 2;
          y = (srcHeight - watermarkImageHeight) / 2;
        } else if (watermarkPosition == WatermarkPosition.bottomLeft) {
          x = 0;
          y = srcHeight - watermarkImageHeight;
        } else if (watermarkPosition == WatermarkPosition.bottomRight) {
          x = srcWidth - watermarkImageWidth;
          y = srcHeight - watermarkImageHeight;
        }
        graphics2D.drawImage(watermarkBufferedImage, x, y, watermarkImageWidth, watermarkImageHeight, null);

        imageOutputStream = ImageIO.createImageOutputStream(destFile);
        imageWriter = (ImageWriter)ImageIO.getImageWritersByFormatName(FilenameUtils.getExtension(destFile.getName())).next();
        imageWriter.setOutput(imageOutputStream);
        ImageWriteParam imageWriteParam = imageWriter.getDefaultWriteParam();
        imageWriteParam.setCompressionMode(2);
        imageWriteParam.setCompressionQuality(0.88F);
        imageWriter.write(null, new IIOImage(destBufferedImage, null, null), imageWriteParam);
        imageOutputStream.flush();
      } catch (IOException e) {
        e.printStackTrace();
      } finally {
        if (graphics2D != null) {
          graphics2D.dispose();
        }
        if (imageWriter != null) {
          imageWriter.dispose();
        }
        if (imageOutputStream != null)
          try {
            imageOutputStream.close();
          } catch (IOException e) {
          }
      }
    }
    else {
      String gravity = "SouthEast";
      if (watermarkPosition == WatermarkPosition.topLeft)
        gravity = "NorthWest";
      else if (watermarkPosition == WatermarkPosition.topRight)
        gravity = "NorthEast";
      else if (watermarkPosition == WatermarkPosition.center)
        gravity = "Center";
      else if (watermarkPosition == WatermarkPosition.bottomLeft)
        gravity = "SouthWest";
      else if (watermarkPosition == WatermarkPosition.bottomRight) {
        gravity = "SouthEast";
      }
      IMOperation operation = new IMOperation();
      operation.gravity(gravity);
      operation.dissolve(Integer.valueOf(alpha));
      operation.quality(Double.valueOf(88.0D));
      operation.addImage(new String[] { watermarkFile.getPath() });
      operation.addImage(new String[] { srcFile.getPath() });
      operation.addImage(new String[] { destFile.getPath() });
      if (type == Type.graphicsMagick) {
        CompositeCmd compositeCmd = new CompositeCmd(true);
        if (graphicsMagickPath != null)
          compositeCmd.setSearchPath(graphicsMagickPath);
        try
        {
          compositeCmd.run(operation, new Object[0]);
        } catch (IOException e) {
          e.printStackTrace();
        } catch (InterruptedException e) {
          e.printStackTrace();
        } catch (IM4JavaException e) {
          e.printStackTrace();
        }
      } else {
        CompositeCmd compositeCmd = new CompositeCmd(false);
        if (imageMagickPath != null)
          compositeCmd.setSearchPath(imageMagickPath);
        try
        {
          compositeCmd.run(operation, new Object[0]);
        } catch (IOException e) {
          e.printStackTrace();
        } catch (InterruptedException e) {
          e.printStackTrace();
        } catch (IM4JavaException e) {
          e.printStackTrace();
        }
      }
    }
  }

  public static void initialize()
  {
  }

  private static String toHexEncoding(Color color)
  {
    StringBuffer stringBuffer = new StringBuffer();
    String R = Integer.toHexString(color.getRed());
    String G = Integer.toHexString(color.getGreen());
    String B = Integer.toHexString(color.getBlue());
    R = R.length() == 1 ? "0" + R : R;
    G = G.length() == 1 ? "0" + G : G;
    B = B.length() == 1 ? "0" + B : B;
    stringBuffer.append("#");
    stringBuffer.append(R);
    stringBuffer.append(G);
    stringBuffer.append(B);
    return stringBuffer.toString();
  }

  static
  {
    if (graphicsMagickPath == null) {
      String osName = System.getProperty("os.name").toLowerCase();
      if (osName.indexOf("windows") >= 0) {
        String pathVariable = System.getenv("Path");
        if (pathVariable != null) {
          String[] paths = pathVariable.split(";");
          for (String path : paths) {
            File gmFile = new File(path.trim() + "/gm.exe");
            File gmdisplayFile = new File(path.trim() + "/gmdisplay.exe");
            if ((gmFile.exists()) && (gmdisplayFile.exists())) {
              graphicsMagickPath = path.trim();
              break;
            }
          }
        }
      }
    }

    if (imageMagickPath == null) {
      String osName = System.getProperty("os.name").toLowerCase();
      if (osName.indexOf("windows") >= 0) {
        String pathVariable = System.getenv("Path");
        if (pathVariable != null) {
          String[] paths = pathVariable.split(";");
          for (String path : paths) {
            File convertFile = new File(path.trim() + "/convert.exe");
            File compositeFile = new File(path.trim() + "/composite.exe");
            if ((convertFile.exists()) && (compositeFile.exists())) {
              imageMagickPath = path.trim();
              break;
            }
          }
        }
      }
    }

    if (type == Type.auto)
      try {
        IMOperation operation = new IMOperation();
        operation.version();
        IdentifyCmd identifyCmd = new IdentifyCmd(true);
        if (graphicsMagickPath != null) {
          identifyCmd.setSearchPath(graphicsMagickPath);
        }
        identifyCmd.run(operation, new Object[0]);
        type = Type.graphicsMagick;
      } catch (Throwable e1) {
        try {
          IMOperation operation = new IMOperation();
          operation.version();
          IdentifyCmd identifyCmd = new IdentifyCmd(false);
          identifyCmd.run(operation, new Object[0]);
          if (imageMagickPath != null) {
            identifyCmd.setSearchPath(imageMagickPath);
          }
          type = Type.imageMagick;
        } catch (Throwable e2) {
          type = Type.jdk;
        }
      }
  }

  public static enum WatermarkPosition
  {
    no, 

    topLeft, 

    topRight, 

    center, 

    bottomLeft, 

    bottomRight;
  }

  private static enum Type
  {
    auto, 

    jdk, 

    graphicsMagick, 

    imageMagick;
  }
}