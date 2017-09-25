package cndoppler.cn.wifiprobe.bean;

import org.litepal.crud.DataSupport;

import java.io.Serializable;

/**
 * Created by admin on 2017/9/25.
 */

public class CheckProgrem extends DataSupport implements Serializable
{
    private long id;
    private String body;
    private long date;
    private long patientID;

    public long getId()
    {
        return id;
    }

    public void setId(long id)
    {
        this.id = id;
    }

    public String getBody()
    {
        return body;
    }

    public void setBody(String body)
    {
        this.body = body;
    }

    public long getDate()
    {
        return date;
    }

    public void setDate(long date)
    {
        this.date = date;
    }

    public long getPatientID()
    {
        return patientID;
    }

    public void setPatientID(long patientID)
    {
        this.patientID = patientID;
    }

    @Override
    public String toString()
    {
        return "CheckProgrem{" +
                "id=" + id +
                ", body='" + body + '\'' +
                ", date=" + date +
                '}';
    }
}
