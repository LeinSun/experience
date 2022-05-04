package com.experience.tree;

import com.experience.tree.bean.BuzApiOutput;
import com.experience.tree.dto.BuzOutputDTO;
import com.experience.util.SnowflakeIDGeneratorUtil;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: sunlei
 * @date: 2022/5/4
 * @description:
 */
public class Tree2Linear {
    /**
     * 将返回参数具有层级结构的VO对象转换成数据库对象
     * @param outputDTOList
     * @param buzOutputList
     * @param parentId
     * @return
     */
    private List<BuzApiOutput> convertRespToDBObject(List<BuzOutputDTO> outputDTOList, List<BuzApiOutput> buzOutputList, Long parentId){
        if (buzOutputList == null){
            buzOutputList = new ArrayList<>();
        }
        for (BuzOutputDTO buzOutputDTO : outputDTOList){
            if (buzOutputDTO.getChildren() != null && buzOutputDTO.getChildren().size()>0){
                BuzApiOutput buzOutput = new BuzApiOutput();
                BeanUtils.copyProperties(buzOutputDTO, buzOutput);
                Long snowId = buzOutput.getSnowId();
                if(snowId == null || snowId.equals(0)) {
                    snowId = SnowflakeIDGeneratorUtil.newId();
                    buzOutput.setSnowId(snowId);
                }
                buzOutput.setParentId(parentId);
                buzOutputList.add(buzOutput);
                convertRespToDBObject(buzOutputDTO.getChildren(), buzOutputList, snowId);
            }
            else {
                BuzApiOutput buzOutput = new BuzApiOutput();
                BeanUtils.copyProperties(buzOutputDTO, buzOutput);
                if(buzOutput.getSnowId() == null || buzOutput.getSnowId().equals(0)) {
                    buzOutput.setSnowId(SnowflakeIDGeneratorUtil.newId());
                }
                buzOutput.setParentId(parentId);
                buzOutputList.add(buzOutput);
            }
        }
        return buzOutputList;
    }
}
