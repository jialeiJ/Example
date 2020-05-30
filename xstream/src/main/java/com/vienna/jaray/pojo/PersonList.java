package com.vienna.jaray.pojo;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamImplicit;
import lombok.Data;

import java.util.List;

@Data
@XStreamAlias("persons")  //定义类级别名
public class PersonList {
    @XStreamAlias("name")   //定义字段级别名
    @XStreamAsAttribute     //定义字段作为属性
    private String name;

    @XStreamAlias("xm")   //定义字段级别名
    @XStreamAsAttribute     //定义字段作为属性
    private String xm;

    @XStreamImplicit
    private List<Person> persons;
}
