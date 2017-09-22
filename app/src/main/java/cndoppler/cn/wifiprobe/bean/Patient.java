package cndoppler.cn.wifiprobe.bean;

import org.litepal.crud.DataSupport;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/9/21 0021.
 */

public class Patient extends DataSupport implements Serializable{
    private long id;
    private String name;
    private int sex;
    private String doctor;
    private String deparment;
    private String body;
    private float bodyHeight;
    private float weight;
    private long birthday;
    private String pathogeny;
    private String number;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public String getDoctor() {
        return doctor;
    }

    public void setDoctor(String doctor) {
        this.doctor = doctor;
    }

    public String getDeparment() {
        return deparment;
    }

    public void setDeparment(String deparment) {
        this.deparment = deparment;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public float getBodyHeight() {
        return bodyHeight;
    }

    public void setBodyHeight(float bodyHeight) {
        this.bodyHeight = bodyHeight;
    }

    public float getWeight() {
        return weight;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }

    public long getBirthday() {
        return birthday;
    }

    public void setBirthday(long birthday) {
        this.birthday = birthday;
    }

    public String getPathogeny() {
        return pathogeny;
    }

    public void setPathogeny(String pathogeny) {
        this.pathogeny = pathogeny;
    }

    @Override
    public String toString() {
        return "Patient{" +
                "name='" + name + '\'' +
                ", sex=" + sex +
                ", doctor='" + doctor + '\'' +
                ", deparment='" + deparment + '\'' +
                ", body='" + body + '\'' +
                ", bodyHeight=" + bodyHeight +
                ", weight=" + weight +
                ", birthday=" + birthday +
                ", pathogeny='" + pathogeny + '\'' +
                ", number='" + number + '\'' +
                '}';
    }
}
