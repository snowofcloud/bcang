package com.xly.spring.data.jpa.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @auther xuxq
 * @date 2018/11/17 17:53
 */
@Data
@Entity
@Table(name = "t_account_info")
public class AccountInfo implements Serializable {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @Column(length = 64)
    private long accountId;
    @Column(length = 64)
    private Integer balance;

}
