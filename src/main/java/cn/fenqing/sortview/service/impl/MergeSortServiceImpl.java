package cn.fenqing.sortview.service.impl;

import cn.fenqing.sortview.commons.DataUtils;
import cn.fenqing.sortview.service.ISortService;
import org.springframework.stereotype.Service;

import javax.websocket.Session;

/**
 * @author Administrator
 */
@Service("mergeSort")
public class MergeSortServiceImpl implements ISortService {

    @Override
    public void sort(DataUtils.SortData sortData, Session session) {
        int[] temp = new int[sortData.arr.length];
        mergeSort(sortData, 0, sortData.arr.length - 1, temp, session);
        DataUtils.send(session, sortData);
    }

    public void mergeSort(DataUtils.SortData sortData, int left, int right, int[] temp, Session session){
        if(left < right){
            int mid = (left + right) / 2;
            //先向左递归进行分解
            mergeSort(sortData, left, mid, temp, session);
            //向右递归
            mergeSort(sortData, mid + 1, right, temp, session);

            merge(sortData, left, mid + 1, right, temp, session);
        }
    }

    /**
     * 合并
     * @param left 左边索引
     * @param mid 中间索引（左右分界点）
     * @param right 右边索引
     * @param temp 临时数组
     */
    public void merge(DataUtils.SortData sortData, int left, int mid, int right, int[] temp, Session session){
        int[] array = sortData.arr;
        int l = left;
        int r = mid;
        int end = right + 1;
        int cur = left;
        while (l < mid || r < end){
            int min = 0;
            if(l >= mid || (r < end && array[l] > array[r])){
                min = array[r];
                r++;
                DataUtils.send(session, sortData);
            }else if(r >= end || array[l] <= array[r]){
                min = array[l];
                l++;
                DataUtils.send(session, sortData);
            }
            temp[cur] = min;
            cur++;
            DataUtils.send(session, sortData);
        }
        for (int i = left; i <= right; i++) {
            array[i] = temp[i];
            DataUtils.send(session, sortData);
        }
    }
}
