package cn.fenqing.sortview.controller;

import cn.fenqing.sortview.commons.DataUtils;
import cn.fenqing.sortview.config.ApplicationContextUtil;
import cn.fenqing.sortview.service.ISortService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Component;

import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

@Component
@ServerEndpoint("/sort")
public class SortWebSocker {

    /**
     * 收到客户端的消息
     *
     * @param message 消息
     * @param session 会话
     */
    @OnMessage
    public void onMessage(String message, Session session) {
        JSONObject jsonObject = JSON.parseObject(message);
        JSONArray arr = jsonObject.getJSONArray("arr");
        int[] arr1 = arr.stream().map(String::valueOf).mapToInt(Integer::parseInt).toArray();
        String type = jsonObject.getString("type");
        DataUtils.SortData sortData = new DataUtils.SortData();
        sortData.arr = arr1;
        sortData.ok = false;
        new Thread(() -> {
            ISortService iSortService = ApplicationContextUtil.getApplicationContext().getBean(type, ISortService.class);
            iSortService.sort(sortData, session);
        }).start();
    }

    @OnOpen
    public void onOpen(Session session) {
        DataUtils.webSocketSet.add(session);
        System.out.println("连接上了");
    }



}
