package cn.fenqing.sortview.service.impl;

import cn.fenqing.sortview.commons.DataUtils;
import cn.fenqing.sortview.service.ISortService;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import javax.websocket.Session;

/**
 * @author Administrator
 */
@Service("shellSort")
public class ShellSortServiceImpl implements ISortService {

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
        //1. 先计算得到初始组数
        for (int group = len / 2; group >= 1; group /= 2){
            for (int i = 0; i < array.length; i++) {
                int insertNum = array[i];
                int insertIndex = i - group;
                while (insertIndex >= i % group && insertNum < array[insertIndex]){
                    array[insertIndex + group] = array[insertIndex];
                    insertIndex -= group;
                    DataUtils.send(session, sortData);
                }
                if(insertIndex != i - group){
                    array[insertIndex + group] = insertNum;
                    DataUtils.send(session, sortData);
                }
            }
        }
    }
}
