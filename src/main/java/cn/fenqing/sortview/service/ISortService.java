package cn.fenqing.sortview.service;

import cn.fenqing.sortview.commons.DataUtils;

import javax.websocket.Session;

/**
 * @author Administrator
 */
public interface ISortService {
    /**
     * 排序
     * @param sortData
     */
    void sort(DataUtils.SortData sortData, Session session);

}
