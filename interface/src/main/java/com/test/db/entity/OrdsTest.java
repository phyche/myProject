package com.test.db.entity;

import com.test.base.entity.ValueObject;

public class OrdsTest extends ValueObject {
    private static final long serialVersionUID = 3179774500102166111L;
    /**
     * 名字
     */
    private String tname;

    public String getTname() {
        return tname;
    }

    public void setTname(String tname) {
        this.tname = tname;
    }

    @Override
    public String toString() {
        return "OrdsTest{" +
                "tname='" + tname + '\'' +
                '}';
    }
}