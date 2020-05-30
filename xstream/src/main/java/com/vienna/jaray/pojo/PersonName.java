package com.vienna.jaray.pojo;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Data;

@Data
@XStreamAlias("person")
public class PersonName {
    @XStreamAlias("name")
    private Name name;

    public PersonName(String firstName, String lastName) {
        this.name = new Name(firstName, lastName);
    }

    public Name getName(){
        return name;
    }

}
