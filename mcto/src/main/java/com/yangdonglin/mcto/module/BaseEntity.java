package com.yangdonglin.mcto.module;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class BaseEntity {

    @TableId("ID")
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
