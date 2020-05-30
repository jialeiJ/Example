package com.vienna.jaray.converter;

import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.vienna.jaray.pojo.PersonName;

/**
 * 自定义转换器
 */
public class PersonConverter implements Converter {
    /**
     * 判断需要转换的类型
     * @param type
     * @return
     */
    @Override
    public boolean canConvert(Class type) {
        return type.equals(PersonName.class);
    }

    /**
     * java对象到xml的逻辑
     * @param source
     * @param writer
     * @param context
     */
    @Override
    public void marshal(Object source, HierarchicalStreamWriter writer, MarshallingContext context) {
        PersonName person = (PersonName) source;
        writer.startNode("name");
        writer.setValue(person.getName().getFirstName() + "," + person.getName().getLastName());
        writer.endNode();
    }

    /**
     * xml到java对象的逻辑
     * @param reader
     * @param context
     * @return
     */
    @Override
    public Object unmarshal(HierarchicalStreamReader reader, UnmarshallingContext context) {
        reader.moveDown();
        String[] nameparts = reader.getValue().split(",");
        PersonName person = new PersonName(nameparts[0],nameparts[1]);
        reader.moveUp();
        return person;
    }
}
