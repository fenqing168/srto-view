package cn.fenqing.sortview.service.impl;

import cn.fenqing.sortview.commons.DataUtils;
import cn.fenqing.sortview.service.ISortService;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import javax.websocket.Session;

/**
 * @author Administrator
 */
@Service("quickSort")
public class QuickSortServiceImpl implements ISortService {

    @Override
    public void sort(DataUtils.SortData sortData, Session session) {
        sort(sortData, 0, sortData.arr.length - 1, session);
        sortData.ok = true;
        DataUtils.send(session, sortData);
    }

    @SneakyThrows
    /**
     * 快速排序
     * 1. 在数组中选取一个适当的值，将比这个值小的数放数组左边，比这个值大的放数组右边
     *  1.1 挖坑
     * 2. 然后分别在分别将左边和右边重复第一步，直到最后无法分解，则能得到一个有序列表
     *
     * @param array 数组
     * @param left  左边的边界
     * @param right 右边的边界
     */
    private void sort(DataUtils.SortData sortData, int left, int right, Session session) {
        if(left >= right){
            return;
        }
        int pivotIndex = findPivotBySwap(sortData, left, right, session);
        sort(sortData, left, pivotIndex - 1, session);
        sort(sortData, pivotIndex + 1, right, session);
    }

    /**
     * 通过挖坑法找中轴
     * 1. 取数组第一个（start下标）为中轴，将其值保存起来，将当前下标视为一个"坑"
     * 2. 定义两个指针变量，left,right,left从start+1开始，right为end
     * 3. 从右边开始，向左找，直到找到一个比中轴值小的值，然后将该值赋值给“坑”,然后自己位置变为"坑"
     * 4. 然后从左边开始，向右找，直到找到一个比中轴大或者等于的值，然后将值赋值给“坑”,然后自己变为"坑"
     * 5. 反复此步骤，最后将中轴的值给最后坑的位置，即可，坑的位置就是中轴
     * @param array 数组
     * @param start 开始
     * @param end 结束
     * @return 中轴的下标
     */
    private int findPivotByPothole(int[] array, int start, int end){
        //取数组第一个（start下标）为中轴,将其值保存起来,将当前下标视为一个"坑"
        int pivot = array[start];
        int pit = start;
        //定义两个指针变量，left,right,left从start+1开始，right为end
        int left = start + 1, right = end;
        while (left <= right){
            //从右边开始，向左找，直到找到一个比中轴值小的值，然后将该值赋值给“坑”,然后自己位置变为"坑"
            while (array[right] > pivot){
                right--;
                if(right < left){
                    break;
                }
            }
            if(right < left){
                break;
            }
            array[pit] = array[right];
            pit = right;
            //然后从左边开始，向右找，直到找到一个比中轴大或者等于的值，然后将值赋值给“坑”,然后自己变为"坑"
            while (array[left] <= pivot){
                left++;
                if(right < left){
                    break;
                }
            }
            if(right < left){
                break;
            }
            array[pit] = array[left];
            pit = left;
        }
        //反复此步骤，最后将中轴的值给最后坑的位置，即可，坑的位置就是中轴
        array[pit] = pivot;
        return pit;
    }

    /**
     * 通过交换法找中轴
     * 1. 找到一个pivot（基准），left和right（一般pivot选择序列的第一个元素，left为第一个元素，right为最后一个元素）
     * 2. 向前移动right的位置，直到找到一个比pivot小的元素
     * 3. 向后移动left的位置，直到找到一个比pivot大的元素
     * 4. 交换当前的两个元素
     * 5. 重复2，3，4步位置，直到left>right的时候停止。
     * 6. 最后直接让right坐标与第一个交换即可（为什么是right：当left>right,则说明right当前的值在交会的时候一定被left检查过，
     *  是小于pivot的，所以可以将其放在中轴左边）
     * @param start 开始位置
     * @param end 结束位置
     * @param session
     * @return 中轴的下标
     */
    private int findPivotBySwap(DataUtils.SortData sortData, int start, int end, Session session){
        int[] array = sortData.arr;
        //找到一个pivot（基准）
        int pivot = array[start];
        //left和right（一般pivot选择序列的第一个元素，left为第一个元素，right为最后一个元素）
        int left = start + 1, right = end;
        while (left <= right){
            if (array[right] > pivot){
                right--;
                DataUtils.send(session, sortData);
                continue;
            }
            if (array[left] <= pivot){
                left++;
                DataUtils.send(session, sortData);
                continue;
            }
            //如果能到这里，则说明都找到了，则开始交换
            int temp = array[left];
            array[left] = array[right];
            array[right] = temp;
            DataUtils.send(session, sortData);
        }
        //已经全部交换完，开始和中轴交换
        array[start] = array[right];
        array[right] = pivot;
        DataUtils.send(session, sortData);
        return right;
    }
}
