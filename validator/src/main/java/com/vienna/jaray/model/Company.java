package com.vienna.jaray.model;

import com.vienna.jaray.groups.Create;

import javax.validation.constraints.NotEmpty;

public class Company {

    @NotEmpty(message = "企业名不能为空", groups = {Create.class})
    private String cpyName;

    public String getCpyName() {
        return cpyName;
    }

    public void setCpyName(String cpyName) {
        this.cpyName = cpyName;
    }
}
