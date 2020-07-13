package com.lp.adapter.bean;

import java.io.Serializable;

public class ConfigBean implements Serializable {

    private static final long serialVersionUID = 1L;

    private String interfaceType = "";

    private Integer threadNum = 0;

    public String getInterfaceType() {
        return interfaceType;
    }

    public void setInterfaceType(String interfaceType) {
        this.interfaceType = interfaceType;
    }

    public Integer getThreadNum() {
        return threadNum;
    }

    public void setThreadNum(Integer threadNum) {
        this.threadNum = threadNum;
    }
}
