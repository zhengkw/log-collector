package com.zhengkw.dw.bean;

/**
 * @ClassName:Person
 * @author: zhengkw
 * @description:
 * @date: 20/03/26下午 9:30
 * @version:1.0
 * @since: jdk 1.8
 */
public class Person {
    private Integer id;
    private String name;
    private String gender;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    @Override
    public String toString() {
        return "Person" + '\t' +
                "id=" + id + '\t' +
                ", name='" + name + '\t' +
                ", gender='" + gender;
    }
}
