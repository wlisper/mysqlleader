package com.test;

import com.test.dao.LeaderDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import javax.annotation.PostConstruct;
import java.net.InetAddress;
import java.net.UnknownHostException;

@EnableScheduling
@Configuration
@Profile("default")
public class SchedulerTimer {

    @Value("${test.service}")
    private String service;

    private String node;

    @Value("${server.port}")
    private int port;

    @Autowired
    LeaderDao leaderDao;

    @PostConstruct
    public void afterConstruct() {
        try {
            InetAddress inetAddress = InetAddress.getLocalHost();
            this.node = inetAddress.getHostAddress() + ":" + port;
        } catch (UnknownHostException ex) {
            ex.printStackTrace();
        }
    }

    @Scheduled(fixedDelay = 1000)
    public void elect() {
        System.out.println("elect leader.." + service + ":" + node);
        try {
            leaderDao.leaderElect(service, node);
        } catch (Exception ex) {
            System.out.println(service + ":" + node + "exception:" + ex.getMessage());
        }

    }

    @Scheduled(fixedDelay = 1000)
    public void leader() {
        if (leaderDao.isLeader(service, node)) {
            System.out.println(service + ":" + node + " is leader");
        } else {
            System.out.println(service + ":" + node + " is not leader");
        }
    }
}
