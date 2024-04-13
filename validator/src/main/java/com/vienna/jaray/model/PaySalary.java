package com.vienna.jaray.model;

import com.vienna.jaray.custom.EnumString;
import com.vienna.jaray.groups.Create;
import com.vienna.jaray.groups.Query;

import javax.validation.Valid;
import javax.validation.constraints.*;

public class PaySalary {

    @NotEmpty(message = "卡号不能为空", groups = {Create.class})
    private String cardNo;

    @Pattern(regexp = "^(13[0-9]|14[5|7]|15[0|1|2|3|5|6|7|8|9]|18[0|1|2|3|5|6|7|8|9])\\d{8}$", message = "手机号有误", groups = {Query.class})
    @NotBlank(message = "手机号不能为空/空字符", groups = {Query.class})
    private String phone;

    @Min(message = "性别只允许0或1", value = 0, groups = {Query.class})
    @Max(message = "性别只允许0或1", value = 1, groups = {Query.class})
    private Integer sex;

    @NotBlank(message = "性别不能为空")
    @EnumString(value = {"F","M"}, message="性别只允许为F或M")
    private String sexStr;

    @NotBlank(message = "邮箱不能为空", groups = {Query.class})
    @Email(message = "邮箱格式不正确", groups = {Query.class})
    private String email;

    @Valid
    @NotNull(message = "企业信息不能为空")
    private Company company;

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public String getSexStr() {
        return sexStr;
    }

    public void setSexStr(String sexStr) {
        this.sexStr = sexStr;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }
}
