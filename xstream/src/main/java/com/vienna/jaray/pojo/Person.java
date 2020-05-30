package com.vienna.jaray.pojo;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamOmitField;
import lombok.Data;

@Data
@XStreamAlias("person")  //定义类级别名
public class Person {
    @XStreamAlias("index")   //定义字段级别名
    @XStreamAsAttribute     //定义字段作为属性
    private int index;

    private String name;

    private int age;

    @XStreamOmitField       //省略不属于XML的字段
    private String gender;

    private String address;
}
