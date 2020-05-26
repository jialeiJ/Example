package com.vienna.jaray.model;

import com.alibaba.excel.annotation.ExcelProperty;

public class ExcelPropertyModel {
    @ExcelProperty(value = "姓名", index = 0)
    private String name;

    @ExcelProperty(value = "年龄", index = 1)
    private String age;

    @ExcelProperty(value = "邮箱", index = 2)
    private String email;

    @ExcelProperty(value = "地址", index = 3)
    private String address;

    @ExcelProperty(value = "性别", index = 4)
    private String sax;

    @ExcelProperty(value = "高度", index = 5)
    private String heigh;

    @ExcelProperty(value = "备注", index = 6)
    private String last;

    public ExcelPropertyModel() {
    }

    public ExcelPropertyModel(String name, String age, String email, String address, String sax, String heigh, String last) {
        this.name = name;
        this.age = age;
        this.email = email;
        this.address = address;
        this.sax = sax;
        this.heigh = heigh;
        this.last = last;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getSax() {
        return sax;
    }

    public void setSax(String sax) {
        this.sax = sax;
    }

    public String getHeigh() {
        return heigh;
    }

    public void setHeigh(String heigh) {
        this.heigh = heigh;
    }

    public String getLast() {
        return last;
    }

    public void setLast(String last) {
        this.last = last;
    }

    @Override
    public String toString() {
        return "ExcelPropertyModel [name=" + name + ", age=" + age + ", email=" + email + ", address="
                + address + ", sax=" + sax + ", heigh=" + heigh + ", last=" + last + "]";
    }

}
