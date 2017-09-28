package cndoppler.cn.wifiprobe.bean;

import org.litepal.crud.DataSupport;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

import cndoppler.cn.wifiprobe.utils.DateUtils;

/**
 * Created by Administrator on 2017/9/21 0021.
 */

public class Patient extends DataSupport implements Serializable{
    private long id;
    private String name = "临时病人";
    private int sex = 2;
    private long birthday = new Date().getTime();
    private int age = 0;
    private String number = DateUtils.dateAndTime();
    private long date =new Date().getTime();
    private ArrayList<CheckProgrem> checkProgrems = new ArrayList<>();

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

    public ArrayList<CheckProgrem> getCheckProgrems()
    {
        return checkProgrems;
    }

    public void setCheckProgrems(ArrayList<CheckProgrem> checkProgrems)
    {
        this.checkProgrems = checkProgrems;
    }

    @Override
    public String toString()
    {
        String chineseSex = sex==0?"男":(sex ==1?"女":"未知");
        return  "  id：" + id +
                "  编号：" + number +
                "  姓名：" + name +
                "  sex：" + chineseSex +
                "  出生日期：" + DateUtils.formatDatetime(new Date(birthday)) +
                "  年龄：" + age  +
                "  建表时间："+DateUtils.formatDatetime(new Date(date))
                ;
    }
}
