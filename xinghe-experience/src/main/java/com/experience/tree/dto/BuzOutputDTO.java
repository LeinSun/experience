package com.experience.tree.dto;

import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @author: sunlei
 * @date: 2022/4/16
 * @description:
 */
@Data
public class BuzOutputDTO {
    /**
     * 编号
     */
    private Long id;
    /**
     * 唯一标识ID
     */
    private Long snowId;
    /**
     * 参数中文名
     */
    private String name;
    /**
     * 参数编码，表示key
     */
    private String code;
    /**
     * 数据类型
     */
    private String dataType;
    /**
     * 业务接口ID，公共参数的function id 为 0
     */
    private Long buzApiId;
    /**
     * 是否必含，0表示非必含，1表示必含
     */
    private Boolean must;
    /**
     * 描述
     */
    private String description;
    /**
     * 默认值
     */
    private String defaultValue;

    /**
     * 接口版本号
     */
    private String apiVersion;
    /**
     * 创建日期 默认为当前时间
     */
    private Date createTime;
    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 父类的snow_id 表示其父层级的ID, parent_id为0，表示为第一层结构
     */
    private Long parentId;

    /**
     * 下一级的返回参数结构
     */
    private List<BuzOutputDTO> children;
    
}
