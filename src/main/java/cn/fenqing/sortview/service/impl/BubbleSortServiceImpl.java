package cn.fenqing.sortview.service.impl;

import cn.fenqing.sortview.commons.DataUtils;
import cn.fenqing.sortview.service.ISortService;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import javax.websocket.Session;

/**
 * @author Administrator
 */
@Service("bubbleSort")
public class BubbleSortServiceImpl implements ISortService {

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
        int tail = len;
        for (int i = 0; i < len - 1; i++) {
            boolean flag = true;
            for (int p = 0, q = 1; q < tail; p++, q++){
                int pVal = array[p];
                int qVal = array[q];
                if(pVal > qVal){
                    flag = false;
                    array[p] = qVal;
                    array[q] = pVal;
                    DataUtils.send(session, sortData);
                }
            }
            if(flag){
                return;
            }
            tail--;
        }
    }
}
