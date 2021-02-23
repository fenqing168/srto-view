package cn.fenqing.sortview.service.impl;

import cn.fenqing.sortview.commons.DataUtils;
import cn.fenqing.sortview.service.ISortService;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import javax.websocket.Session;

/**
 * @author Administrator
 */
@Service("insertSort")
public class InsertSortServiceImpl implements ISortService {

    @Override
    public void sort(DataUtils.SortData sortData, Session session) {
        sort1(sortData, session);
        sortData.ok = true;
        DataUtils.send(session, sortData);
    }

    @SneakyThrows
    public void sort1(DataUtils.SortData sortData, Session session) {
        int[] array = sortData.arr;
        int len = array.length;
        //下标从1开始，默认认为0~1是一个有序表
        for (int i = 1; i < len; i++) {
            int num = array[i];
            //在0~i之间找合适的位置
            int insertIndex = i - 1;
            while (insertIndex >= 0 && num < array[insertIndex]) {
                array[insertIndex + 1] = array[insertIndex];
                insertIndex--;
                DataUtils.send(session, sortData);
            }
            array[insertIndex + 1] = num;
            DataUtils.send(session, sortData);
        }
    }
}
