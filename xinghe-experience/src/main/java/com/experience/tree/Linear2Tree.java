package com.experience.tree;

import com.experience.tree.bean.BuzApiOutput;
import com.experience.tree.dto.BuzOutputDTO;
import org.springframework.beans.BeanUtils;

import java.util.*;

/**
 * @author: sunlei
 * @date: 2022/5/4
 * @description: 将线性数组结构转换成树形结构
 * 例如：将如下线性格式
 * [{
 * 	"id": "17",
 * 	"snowId": "966295655892041728",
 * 	"code": "weather",
 * 	"parentId": "0",
 * },
 * {
 * 	 "id": "19",
 * 	 "snowId": "966295655892041729",
 * 	 "code": "id",
 * 	 "parentId": "966295655892041728",
 * }
 * }]
 *
 * 转换成如下树形格式
 * [{
 * 	"id": "17",
 * 	"snowId": "966295655892041728",
 * 	"code": "weather",
 * 	"parentId": "0",
 * 	"children": [
 *     {
 * 		 "id": "19",
 * 		 "snowId": "966295655892041729",
 * 		 "code": "id",
 * 		 "parentId": "966295655892041728",
 * 		 "children": []
 *     }
 * 	]
 * }]
 */
public class Linear2Tree {

    /**
     * 方法一
     * 将线性数组结构转换成树形结构, 时间复杂度为O(n)
     * @param buzOutputList
     * @return
     */
    private List<BuzOutputDTO> buildTree(List<BuzApiOutput> buzOutputList){
        Map<Long, BuzOutputDTO> tempMap = new HashMap<>();
        List<BuzOutputDTO> result = new ArrayList<>();
        for (int i = 0; i < buzOutputList.size(); i++) {
            BuzOutputDTO buzOutputDTO = new BuzOutputDTO();
            BeanUtils.copyProperties(buzOutputList.get(i), buzOutputDTO);
            tempMap.put(buzOutputList.get(i).getSnowId(),buzOutputDTO);
        }
        Iterator<Map.Entry<Long, BuzOutputDTO>> iterator = tempMap.entrySet().iterator();
        while (iterator.hasNext()){
            Map.Entry<Long, BuzOutputDTO> entry= iterator.next();
            Long parentId = entry.getValue().getParentId();
            if (parentId != 0){
                List<BuzOutputDTO> children = tempMap.get(parentId).getChildren();
                if (children == null){
                    tempMap.get(parentId).setChildren(new ArrayList<>());
                }
                tempMap.get(parentId).getChildren().add(entry.getValue());
            }else {
                result.add(entry.getValue());
            }
        }
        return result;

    }

    /**
     * 方法二
     * 将线性数组结构转换成树形结构, 使用递归的方式
     * @param buzOutputList
     * @return
     */
    private List<BuzOutputDTO> convertLinearToTree(List<BuzApiOutput> buzOutputList){
        List<BuzOutputDTO> buzOutputDTOs =  new ArrayList<>();
        // 获取第一层级的对象
        for (BuzApiOutput buzApiOutput : buzOutputList) {
            if(buzApiOutput.getParentId() == 0){
                BuzOutputDTO buzOutputDTO = new BuzOutputDTO();
                BeanUtils.copyProperties(buzApiOutput, buzOutputDTO);
                buzOutputDTOs.add(buzOutputDTO);
            }
        }
        for (BuzOutputDTO root : buzOutputDTOs) {
            root.setChildren(getChildren(buzOutputList,root));
        }
        return buzOutputDTOs;
    }

    private List<BuzOutputDTO> getChildren(List<BuzApiOutput> buzOutputList, BuzOutputDTO parent){
        List<BuzOutputDTO> childrenList =  new ArrayList<>();
        for (BuzApiOutput buzApiOutput : buzOutputList) {
            if(buzApiOutput.getParentId().equals(parent.getSnowId())){
                BuzOutputDTO buzOutputDTO = new BuzOutputDTO();
                BeanUtils.copyProperties(buzApiOutput, buzOutputDTO);
                buzOutputDTO.setChildren(getChildren(buzOutputList, buzOutputDTO));
                childrenList.add(buzOutputDTO);
            }
        }
        return childrenList;
    }
}
