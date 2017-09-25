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
    private String name;
    private int sex;
    private long birthday;
    private int age;
    private String number;
    private long date;
    private ArrayList<CheckProgrem> checkProgrems;

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
        StringBuffer s = new StringBuffer();
        if (checkProgrems!=null){
            for (int i=0;i<checkProgrems.size();i++){
                s.append(checkProgrems.get(i).getBody());
            }
        }
        String chineseSex = sex==0?"男":(sex ==1?"女":"未知");
        return  "  编号：" + number +
                "  姓名：" + name +
                "  sex：" + chineseSex +
                "  出生日期：" + DateUtils.formatDatetime(new Date(birthday)) +
                "  年龄：" + age  +
                "  建表时间："+DateUtils.formatDatetime(new Date(date))+
                "  检查部位："+s

                ;
    }
}
