package com.vienna.jaray.service;

import com.octo.captcha.CaptchaFactory;
import com.octo.captcha.component.image.backgroundgenerator.FunkyBackgroundGenerator;
import com.octo.captcha.component.image.color.SingleColorGenerator;
import com.octo.captcha.component.image.fontgenerator.RandomFontGenerator;
import com.octo.captcha.component.image.textpaster.RandomTextPaster;
import com.octo.captcha.component.image.wordtoimage.ComposedWordToImage;
import com.octo.captcha.component.word.wordgenerator.RandomWordGenerator;
import com.octo.captcha.engine.image.gimpy.SimpleListImageCaptchaEngine;
import com.octo.captcha.image.gimpy.GimpyFactory;
import com.octo.captcha.service.captchastore.FastHashMapCaptchaStore;
import com.octo.captcha.service.image.DefaultManageableImageCaptchaService;
import com.octo.captcha.service.image.ImageCaptchaService;

import java.awt.*;

public class SimpleCustomImageService {
    private static ImageCaptchaService instance = initializeService();

    /**
     * 方法名：getInstance()
     * 功    能：用于获取 ImageCaptchaService 类型的返回值
     * 返回类型：ImageCaptchaService
     *
     */
    public static ImageCaptchaService getInstance() {
        return instance;
    }
    /**
     *
     * 方法名：initializeService
     * 功    能：该方法用于完成验证码生成的基本设置
     * 返回类型：ImageCaptchaService
     *
     */
    private static ImageCaptchaService initializeService() {
        // 设置随机字体大小的范围
        RandomFontGenerator fonts = new RandomFontGenerator(new Integer(24),
                new Integer(36));
        // ***FunkyBackgroundGenerator类,可以设置四种颜色点混合而成的背景***
        // 第一个参数是图片宽度
        // 第二个参数是图片高度
        // 第三至第六个参数分別是图片中,左上,左下,右上,右下四个部分的颜色点的颜色
        // 第七个参数是颜色混杂的程度，如：0.5为颜色相互混合的点占一半，最大1.0
        // ***设置四个颜色除了可以使用SingleColorGenerator外,
        // ***应该也可以使用RandomListColorGenerator类,让每个部分的颜色都不固定
        SingleColorGenerator leftUpColor = new SingleColorGenerator(Color.RED);
        SingleColorGenerator leftDownColor = new SingleColorGenerator(Color.YELLOW);
        SingleColorGenerator rightUpColor = new SingleColorGenerator(Color.CYAN);
        SingleColorGenerator rightDownColor = new SingleColorGenerator(Color.GREEN);
        FunkyBackgroundGenerator background = new FunkyBackgroundGenerator(200, 100,
                leftUpColor, leftDownColor, rightUpColor, rightDownColor, 0.5f);
        // 下面被注释掉的方法是利用 FileReaderRandomBackgroundGenerator 进行设置验证码背景图片
        // 第一个参数和第二个参数分别表示图片的宽度和高度
        // 第三个参数表示图片的文件夹，当你读取文件夹时，会随机取出一张图片作为背景
//		FileReaderRandomBackgroundGenerator background =
//				new FileReaderRandomBackgroundGenerator(
//							 200, 100, "D:\\Program Files\\apache-tomcat-6.0.20\\webapps\\image");

        // 设置单词长度，即：单词的位数，第二个是最大能接受字符的个数，最后一个参数是颜色
        RandomTextPaster textPaster =
                new RandomTextPaster(new Integer(5), new Integer(5), Color.BLUE);
        // 组成验证码，第一参数为字体的设置，第二参数为生成的背景，第三参数为设置生成的单词长度
        ComposedWordToImage cwti =
                new ComposedWordToImage(fonts, background, textPaster);
        // 设置随机取词范围，在这一阶段一定要排除难以辨认的字符
        RandomWordGenerator words =
                new RandomWordGenerator("ABCDEFGHIJKLMNOPQRSTUVWXYZ");
        // 设置 GimpyFactory 类，第一个参数为验证码组成，第二参数是随机取词范围
        GimpyFactory gimpy = new GimpyFactory(words, cwti);
        // 这个类，默认的添加了一种生成文字的工厂类
        SimpleListImageCaptchaEngine engine = new SimpleListImageCaptchaEngine();
        // 如果直接用add方法来添加,则会保留 SimpleListImageCaptchaEngine 类,默认添加的一个工厂类
        engine.setFactories(new CaptchaFactory[] { gimpy });
        // 验证码存储器
        FastHashMapCaptchaStore captchaStore = new FastHashMapCaptchaStore();
        // 创建一个用来生成图片的服务，参数如下:
        // 第一个参数是存储器，用来存储生成的文本，最终等待输入验证码后，验证输入是否正确
        // 第二个参数是生成图片的引擎
        // 第三个参数是最小保证存储的时间，单位是秒
        // 第四个参数是最大的存储大小，可能是生成图片最大使用的大小
        // 第五个参数是 Captcha 在缓存集合的验证码加载存储数量
        DefaultManageableImageCaptchaService defaultService = new
                DefaultManageableImageCaptchaService(captchaStore, engine, 180, 100000, 75000);

        return defaultService;
    }
}
