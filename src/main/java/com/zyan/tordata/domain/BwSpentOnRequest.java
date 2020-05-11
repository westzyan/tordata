package com.zyan.tordata.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.zyan.tordata.annotation.JSONField;

import java.util.Date;

public class BwSpentOnRequest {
    private Integer id;
    @JsonFormat(pattern = "yyyy-MM-dd",timezone="GMT+8")
    private Date date;
    private String dirread;
    private String dirwrite;
    private String dirauthread;
    private String dirauthwrite;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getDirread() {
        return dirread;
    }

    public void setDirread(String dirread) {
        this.dirread = dirread;
    }

    public String getDirwrite() {
        return dirwrite;
    }

    public void setDirwrite(String dirwrite) {
        this.dirwrite = dirwrite;
    }

    public String getDirauthread() {
        return dirauthread;
    }

    public void setDirauthread(String dirauthread) {
        this.dirauthread = dirauthread;
    }

    public String getDirauthwrite() {
        return dirauthwrite;
    }

    public void setDirauthwrite(String dirauthwrite) {
        this.dirauthwrite = dirauthwrite;
    }

    @Override
    public String toString() {
        return "BwSpentOnRequest{" +
                "id=" + id +
                ", date=" + date +
                ", dirread='" + dirread + '\'' +
                ", dirwrite='" + dirwrite + '\'' +
                ", dirauthread='" + dirauthread + '\'' +
                ", dirauthwrite='" + dirauthwrite + '\'' +
                '}';
    }
}
