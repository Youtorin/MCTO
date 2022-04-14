package com.yangdonglin.mcto.module;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.util.Date;

@Data
public class BaseEntity {

    @TableId("ID")
    public String id;

    @TableField("createTime")
    public Date createTime;

    @TableField("viewOrder")
    public String viewOrder;

    @TableField("status")
    public Integer status;

    @TableField("timeStamp")
    public String timeStamp;
}
