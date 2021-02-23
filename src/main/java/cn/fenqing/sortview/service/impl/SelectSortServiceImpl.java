package cn.fenqing.sortview.service.impl;

import cn.fenqing.sortview.commons.DataUtils;
import cn.fenqing.sortview.service.ISortService;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import javax.websocket.Session;

/**
 * @author Administrator
 */
@Service("selectSort")
public class SelectSortServiceImpl implements ISortService {

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
        int tail = len - 1;
        //遍历length - 1次
        for (int i = 0; i < tail; i++) {
            //假设最小的值的下标为当前的 i
            int min = i;
            for (int j = i + 1; j < len; j++) {
                //从i + 1开始，如果发现更小的，则换成新的下标
                if(array[min] > array[j]){
                    min = j;
                }
                DataUtils.send(session, sortData);
            }
            //交换彼此的值
            if(min != i){
                int temp = array[min];
                array[min] = array[i];
                array[i] = temp;
                DataUtils.send(session, sortData);
            }
        }
    }
}
