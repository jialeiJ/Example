package com.vienna.jaray;

import com.vienna.jaray.pojo.Person;
import com.vienna.jaray.pojo.PersonList;
import com.vienna.jaray.pojo.PersonName;
import com.vienna.jaray.xstream.CommXstream;
import com.vienna.jaray.xstream.ConverterXstream;
import com.vienna.jaray.xstream.ListXstream;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@SpringBootTest
class XstreamApplicationTests {
    @Autowired
    private CommXstream commXstream;
    @Autowired
    private ConverterXstream converterXstream;
    @Autowired
    private ListXstream listXstream;

    @Test
    void contextLoads() {
    }

    @Test
    public void testCommXstream(){
        Person person = new Person();
        person.setName("jialei");
        person.setAge(25);
        person.setGender("男");
        person.setAddress("西安");

        //javaBean转xml
        String xml = commXstream.objectToXml(person, Person.class);
        log.info("javaBean转xml值：{}", xml);

        //修改xml
        xml = xml.replace("jialei", "Jaray");

        //xml转javaBean
        Person result = commXstream.xmlToObject(xml, Person.class);
        log.info("xml转javaBean值：{}", result.toString());
    }

    @Test
    public void testConverterXstream(){
        PersonName personName = new PersonName("jialei", "jaray");

        //javaBean转xml
        String xml = converterXstream.objectToXml(personName, PersonName.class);
        log.info("javaBean转xml值：{}", xml);

        //修改xml
        xml = xml.replace("jialei", "Jaray");

        //xml转javaBean
        PersonName result = converterXstream.xmlToObject(xml, PersonName.class);
        log.info("xml转javaBean值：{}", result.toString());
    }

    @Test
    public void testListXstream(){
        PersonList personList = new PersonList();
        List<Person> persons = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            //注意需要把创建对象放在for中
            Person person = new Person();
            person.setIndex(i);
            person.setName("jialei");
            person.setAge(25+i);
            person.setGender("男");
            person.setAddress("西安");
            persons.add(person);
        }
        personList.setName("Jaray");
        personList.setXm("Jialei");
        personList.setPersons(persons);


        //List转xml
        String xml = listXstream.objectToXml(personList, PersonList.class);
        log.info("javaBean转xml值：{}", xml);

        //修改xml
        xml = xml.replace("jialei", "Jaray");

        //xml转List
        PersonList result = listXstream.xmlToObject(xml, PersonList.class);
        log.info("xml转javaBean值：{}", result.toString());
    }

}
