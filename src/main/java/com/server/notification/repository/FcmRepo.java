package com.server.notification.repository;

import com.server.notification.entity.UserFcm;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface FcmRepo extends CrudRepository<UserFcm,String> {


    List<UserFcm> findAllByUserIdIn(List<String> uidList);


}

