package com.yangdonglin.mcto.module;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;

public class BaseEntity {

    @TableId(value = "ID", type = IdType.INPUT)
    public String id;

    @TableField("createTime")
    public String createTime;

    @TableField("viewOrder")
    public String viewOrder;

    @TableField("status")
    public String status;

    @TableField("timeStamp")
    public String timeStamp;
}
