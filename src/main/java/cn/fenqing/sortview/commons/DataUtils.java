package cn.fenqing.sortview.commons;

import com.alibaba.fastjson.JSON;
import lombok.SneakyThrows;

import javax.websocket.Session;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * @author Administrator
 */
public class DataUtils {

    public final static Map<String, SortData> MAP = new ConcurrentHashMap<>();
    public static CopyOnWriteArraySet<Session> webSocketSet = new CopyOnWriteArraySet<>();

    @SneakyThrows
    public static void send(Session session, SortData sortData){
        session.getBasicRemote().sendText(JSON.toJSONString(sortData));
    }

    public static class SortData{
        public int[] arr;
        public boolean ok;
    }

}
