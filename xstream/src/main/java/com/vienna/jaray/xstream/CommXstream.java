package com.vienna.jaray.xstream;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.security.AnyTypePermission;
import com.vienna.jaray.pojo.Person;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CommXstream {
    /**
     *
     * @param object
     * @param clazz
     * @return
     * XStream xstream = new XStream(new DomDriver());
     * XStream xstream = new XStream(new StaxDriver()); //不换行，适合网络传输
     */
    public String objectToXml(Object object, Class<?> clazz) {
        XStream xstream = new XStream();

        // 设置默认的安全校验
        XStream.setupDefaultSecurity(xstream);
        // 允许所有的类进行转换
        xstream.addPermission(AnyTypePermission.ANY);

        xstream.processAnnotations(clazz);

        // XML序列化
        String xml = xstream.toXML(object);
        return xml;
    }

    /**
     * @param xml
     * @return
     * XStream xstream = new XStream(new DomDriver());
     * XStream xstream = new XStream(new StaxDriver()); //不换行，适合网络传输
     */
    public <T> T xmlToObject(String xml, Class<T> clazz) {
        XStream xstream = new XStream();

        // 设置默认的安全校验
        XStream.setupDefaultSecurity(xstream);
        // 允许所有的类进行转换
        xstream.addPermission(AnyTypePermission.ANY);

        xstream.processAnnotations(clazz);

        // XML反序列化
        Object object = (Object) xstream.fromXML(xml);
        T cast = clazz.cast(object);
        return cast;
    }

}
