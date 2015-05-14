package com.yi.admin.demo.model;

import com.yi.core.model.BaseModel;

import javax.persistence.Column;
import javax.persistence.Entity;

/**
 * Created by Cai on 2015/5/14 9:57.
 */
@Entity(name = "yi_demo")
public class DemoModel extends BaseModel {
    @Column(length = 10, nullable = false)
    private String name;
    private int age;
    private byte sex;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public byte getSex() {
        return sex;
    }

    public void setSex(byte sex) {
        this.sex = sex;
    }

    public enum Sex {
        FEMALE(0, "女"),
        MALE(1, "男");

        private int key;
        private String value;
        Sex(int key, String value) {
            this.key = key;
            this.value = value;
        }

        public int getKey() {
            return key;
        }

        public void setKey(int key) {
            this.key = key;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }
}
