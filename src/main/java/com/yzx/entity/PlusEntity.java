package com.yzx.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

@TableName("test")
public class PlusEntity {

    @TableField("id")
    @TableId(type= IdType.AUTO)
    private int hhh1;

    @TableField("content")
    private String hhh2;

    public int getHhh1() {
        return hhh1;
    }

    public void setHhh1(int hhh1) {
        this.hhh1 = hhh1;
    }

    public String getHhh2() {
        return hhh2;
    }

    public void setHhh2(String hhh2) {
        this.hhh2 = hhh2;
    }
}
