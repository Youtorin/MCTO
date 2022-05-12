package com.yangdonglin.mcto.module;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import java.io.Serializable;

@Getter
@Setter
@Accessors(chain = true)
public class PageQueryParams {

    /**
     * 默认关键字属性,其他属性可以通过 继承此类后增加新的属性.
     */
    @JsonProperty("keywords")
    private String keywords;

    /**
     * 默认状态属性
     */
    private Integer status;

    /**
     * 用户id
     */
    private String userId;


    /**
     * 分页对象,常用的分页参数集合
     */
    @JsonProperty("pagination")
    private PageQueryPagination pagination;

    @Getter
    @Setter
    public static class PageQueryPagination implements Serializable {

        @JsonProperty("cateId")
        private String cateId;

        /**
         * 每页的数据个数
         */
        @JsonProperty("rows")
        private Integer pageSize;

        /**
         * 当前页,currentPage
         */
        @JsonProperty("page")
        private Integer page;

        /***
         * 排序方式字段比如ID,具体是数据库字段名
         */
        @JsonProperty("sidx")
        private String sidx;

        /**
         * 排序方式 ASC,DES等等
         */
        @JsonProperty("sord")
        private String sord;

        /**
         * 是否为升序排序
         * @return true是 | false不是
         */
        public Boolean isAsc() {
            return "asc".equalsIgnoreCase(sord);
        }

        /**
         * 是否为降序排序
         * @return true是 | false不是
         */
        public Boolean isDesc() {
            return "desc".equalsIgnoreCase(sord);
        }

        /**
         * 判断是否需要分页
         * @return true需要 | false不需要
         */
        public Boolean requirePaging() {
            return null != page && null != pageSize && page > 0 && pageSize > 0;
        }
    }
}