package com.test.dao;

import com.test.dao.mappers.LeaderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class LeaderDao {

    @Autowired
    LeaderMapper leaderMapper;


    public void leaderElect(String service, String node) {
        leaderMapper.elect(service, node);
    }

    public boolean isLeader(String service, String node) {
        return leaderMapper.isLeader(service, node) > 0;
    }
}
