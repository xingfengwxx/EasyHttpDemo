package com.wangxingxing.easyhttpdemo.http.model;

import java.util.List;

/**
 * author : 王星星
 * date : 2023/10/24 17:54
 * email : 1099420259@qq.com
 * description : 统一接口列表数据结构
 */
public class HttpListData<T> extends HttpData<HttpListData.ListBean<T>> {

    public static class ListBean<T> {

        /** 当前页码 */
        private int pageIndex;
        /** 页大小 */
        private int pageSize;
        /** 总数量 */
        private int totalNumber;
        /** 数据 */
        private List<T> items;

        /**
         * 判断是否是最后一页
         */
        public boolean isLastPage() {
            return Math.ceil((float) totalNumber / pageSize) <= pageIndex;
        }

        public int getTotalNumber() {
            return totalNumber;
        }

        public int getPageIndex() {
            return pageIndex;
        }

        public int getPageSize() {
            return pageSize;
        }

        public List<T> getItems() {
            return items;
        }
    }
}