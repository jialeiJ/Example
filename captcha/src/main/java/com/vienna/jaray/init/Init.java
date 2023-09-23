package com.vienna.jaray.init;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URISyntaxException;

@Component
public class Init {

    @Value("${aj.captcha.jigsaw}")
    private String imgPath;

    @Autowired
    ResourceLoader resourceLoader;

    @PostConstruct
    public void dealImage() throws IOException, URISyntaxException {
        resizeImage(310, 155);
    }

    /**
     * @param width   转换后图片宽度
     * @param height  转换后图片高度
     */
    public void resizeImage(int width, int height) throws IOException, URISyntaxException {
        ClassLoader classLoader = Init.class.getClassLoader();
        String path = classLoader.getResource("").getPath();
        String projectName = path.substring(1, path.indexOf("/target"));
        System.out.println("当前项目名称： " + projectName + "/src/main/resources/images/jigsaw/original");

        File dirPath = new File(projectName + "/src/main/resources/images/jigsaw/original");
        if (dirPath.isDirectory()) {
            File[] files = dirPath.listFiles();
            for (File file : files) {
                Image srcImg = ImageIO.read(file);
                BufferedImage buffImg = null;
                buffImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
                //使用TYPE_INT_RGB修改的图片会变色
                buffImg.getGraphics().drawImage(
                        srcImg.getScaledInstance(width, height, Image.SCALE_SMOOTH), 0,
                        0, null);
                ImageIO.write(buffImg, "PNG", file);
            }
        }

        // 加载当前项目classpath下{imgPath}/original及其子文件夹中的所有文件
        Resource[] resources = new PathMatchingResourcePatternResolver().getResources(imgPath + "/original/*");
        // 遍历文件内容
        for(Resource resource : resources) {
            if (resource.isFile()) {
                System.out.println(resource.getFilename());
                InputStream is = resource.getInputStream();
                Image srcImg = ImageIO.read(is);
                BufferedImage buffImg = null;
                buffImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
                //使用TYPE_INT_RGB修改的图片会变色
                buffImg.getGraphics().drawImage(
                        srcImg.getScaledInstance(width, height, Image.SCALE_SMOOTH), 0,
                        0, null);
                ImageIO.write(buffImg, "PNG", resource.getFile());
            }
        }
    }
}
