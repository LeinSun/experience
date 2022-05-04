package com.experience.tree.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author: sunlei
 * @date: 2022/4/16
 * @description:
 */
@Data
public class BuzApiOutput implements Serializable {
    /**
     * 编号
     */
    @TableId(value = "id", type = IdType.AUTO)
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
    @TableField(value = "is_must")
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
     * 父类的snow_id 表示其父层级的ID, parent_id为0，表示为第一层结构
     */
    private Long parentId;
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

}
