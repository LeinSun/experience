package com.activemq.demo.springactivemqproducer.producer;

import lombok.Data;

import java.io.Serializable;

/**
 * @author: sunlei
 * @date: 2022/3/11
 * @description:
 */
@Data
public class ScriptMessageDto implements Serializable {
    private String newScript;
    private String oldScript;
}
