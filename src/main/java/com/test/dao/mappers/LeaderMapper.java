package com.test.dao.mappers;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;


public interface LeaderMapper {

    /*@Insert("insert ignore into leader_election ("
            + "    service_id, leader_id, last_seen_active"
            + " ) values ( "
            + "    @{service}, @{node}, now()"
            + ") on duplicate key update"
            + "    leader_id = if(last_seen_active < now() - interval 5 second, values(leader_id), leader_id),"
            + "    last_seen_active = if(leader_id = values(leader_id), values(last_seen_active), last_seen_active)"
            + ";")*/
    @Insert({"insert ignore into leader_election (leader_id, service_id, last_seen_active)"
            + " values (#{leader_id}, #{service_id}, now()) on duplicate key"
            + " update leader_id = if(last_seen_active < now() - interval 5 second, values(leader_id),"
            + " leader_id), last_seen_active = if(leader_id = values(leader_id), values(last_seen_active),"
            + " last_seen_active)"})
    void elect(@Param("service_id") String service_id, @Param("leader_id") String leader_id);

    // @Select("select count(*) as is_leader from leader_election where service_id = @{service} and leader_id = @{node}")
    @Select({"select count(*) as is_leader from leader_election where service_id = #{service} and leader_id = "
            + "#{node}"})
    int isLeader(@Param("service") String service, @Param("node") String node);
}
