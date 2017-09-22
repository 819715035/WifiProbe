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
    private long birthday;
    private int age;
    private String number;
    private long date;

    public long getId()
    {
        return id;
    }

    public void setId(long id)
    {
        this.id = id;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public int getSex()
    {
        return sex;
    }

    public void setSex(int sex)
    {
        this.sex = sex;
    }

    public long getBirthday()
    {
        return birthday;
    }

    public void setBirthday(long birthday)
    {
        this.birthday = birthday;
    }

    public String getNumber()
    {
        return number;
    }

    public void setNumber(String number)
    {
        this.number = number;
    }

    public long getDate()
    {
        return date;
    }

    public void setDate(long date)
    {
        this.date = date;
    }

    public int getAge()
    {
        return age;
    }

    public void setAge(int age)
    {
        this.age = age;
    }

    @Override
    public String toString()
    {
        return "Patient{" +
                "  编号：'" + number + '\''+
                "  姓名：'" + name + '\'' +
                "  sex：" + sex +
                "  出生日期：" + birthday +
                "  年龄：'" + age + '\'' +
                '}';
    }
}
