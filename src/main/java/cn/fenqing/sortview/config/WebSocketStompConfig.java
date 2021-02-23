package cn.fenqing.sortview.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

/**
 * @author 2018年5月18日16:08:47
 * websocket的配置
 */
@Configuration
public class WebSocketStompConfig{
    @Bean
    public ServerEndpointExporter serverEndpointExporter() {
        return new ServerEndpointExporter();
    }
}
