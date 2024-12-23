package com.example.stockmanager.Util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.example.stockmanager.model.Host;

public final class ManagerUtil {

    public static RestTemplate restTemplate = new RestTemplate();

    private static final Logger logger = LoggerFactory.getLogger(ManagerUtil.class);
    
    public static ResponseEntity<String> sendClearCacheNotification (Iterable<Host> hosts) {
        try {
            for (Host host : hosts) {
                ManagerUtil.restTemplate.delete(ManagerUtil.mountHost(host));
            }
            return new ResponseEntity<String>(HttpStatus.OK);   
        } catch (Exception e) {
            logger.error(e.getMessage() + ": " + e.getCause());
            return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public static String mountHost (Host host) {
        return "http://" + host.getHost() + ":" + host.getPort() + "/" + host.getEndpoint() + "/clear_cache";
    }
}
