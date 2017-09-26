package cndoppler.cn.wifiprobe.bean;

import org.litepal.crud.DataSupport;

import java.io.Serializable;

/**
 * Created by admin on 2017/9/26.
 */

public class PicData extends DataSupport implements Serializable
{
    private long id;
    private int path;
    private long date;

    public long getId()
    {
        return id;
    }

    public void setId(long id)
    {
        this.id = id;
    }

    public int getPath()
    {
        return path;
    }

    public void setPath(int path)
    {
        this.path = path;
    }

    public long getDate()
    {
        return date;
    }

    public void setDate(long date)
    {
        this.date = date;
    }

    @Override
    public String toString()
    {
        return "PicData{" +
                "id=" + id +
                ", path=" + path +
                ", date=" + date +
                '}';
    }
}
