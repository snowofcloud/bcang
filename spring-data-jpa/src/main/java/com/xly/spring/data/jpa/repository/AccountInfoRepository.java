package com.xly.spring.data.jpa.repository;

import com.xly.spring.data.jpa.entity.AccountInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @auther xuxq
 * @date 2018/11/18 10:45
 */
@Repository
public interface AccountInfoRepository extends JpaRepository<AccountInfo,Integer> {

}
