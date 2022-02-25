package com.yangdonglin.mcto.module;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class PageResult<T> implements Serializable {
    private static final long serialVersionUID = 277729936702602048L;

    /**
     * 分页数据列表
     */
    @JsonProperty("items")
    public List<T> items;

    /**
     * 查询数据总量
     */
    @JsonProperty("totalcount")
    public long totalCount;

    public static <T> PageResult<T> create(List<T> items, long total) {
        PageResult<T> result = new PageResult<>();
        result.setItems(items);
        result.setTotalCount(total);
        return result;
    }
}
