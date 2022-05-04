package com.activemq.demo.springactivemqproducer.controller;

import com.activemq.demo.springactivemqproducer.producer.Producer;
import com.activemq.demo.springactivemqproducer.producer.ScriptMessageDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author: sunlei
 * @date: 2022/3/10
 * @description:
 */
@RestController
@RequestMapping("activemq")
public class ActivemqContoller {

    @Autowired
    private Producer producer;

    @RequestMapping("/send")
    public void send() {
        String script1 = "import groovy.json.JsonOutput\nimport groovy.json.JsonSlurper\nimport  com.iflytek.ocp.pipes.util.Util\ndef parse(String dest, String businessParam) {\nMap map = new JsonSlurper().parseText(dest) as TreeMap\ndef paramStr = ''\nmap.each{ k, v ->\nif( k =='name'){\nv = URLEncoder.encode(v, \"utf-8\")\n}\nif( k!='apiVersion' && k!='URL'){\nparamStr = paramStr + k + '=' + v  + '&'\n}\n}\nparamStr = paramStr.substring(0, paramStr.length()-1) + '5ikq43ThTUMHPNTjLcTQv7Jrs66eTIoP'\ndef sign = Util.encodeByMD5(paramStr)\ndef url = map.URL + \"?chc=\" + map.chc + \"&sign=\" + sign\ndef result_ret = [URL:url]\nreturn JsonOutput.toJson(result_ret)\n}\n";
//        String script2 = "import groovy.json.JsonOutput\n  import groovy.json.JsonSlurper\n  import com.iflytek.auto.common.utils.GpsUtil\n def parse(String dest, String businessParam) {\n  def data= new JsonSlurper().parseText(dest);\n  def bizParam = new JsonSlurper().parseText(businessParam);\n  if(null != bizParam.longitude && null != bizParam.latitude){\n    Object dataList = data.list;\n   dataList.each{\n   it.distance = GpsUtil.GetDistance(bizParam.latitude.toDouble(), bizParam.longitude.toDouble(), it.latitude.doubleValue(), it.longitude.doubleValue())\n    }\n	}\n	 return JsonOutput.toJson(data);\n}";
        ScriptMessageDto messageDto = new ScriptMessageDto();
        messageDto.setNewScript(script1);
        messageDto.setOldScript(script1);
        //发送功能就一行代码~
        producer.sendTopic(messageDto);
    }

    @RequestMapping("/send2")
    public void send2() {
        String script1 = "import groovy.json.JsonOutput\nimport groovy.json.JsonSlurper\nimport  com.iflytek.ocp.pipes.util.Util\ndef parse(String dest, String businessParam) {\nMap map = new JsonSlurper().parseText(dest) as TreeMap\ndef paramStr = ''\nmap.each{ k, v ->\nif( k =='name'){\nv = URLEncoder.encode(v, \"utf-8\")\n}\nif( k!='apiVersion' && k!='URL'){\nparamStr = paramStr + k + '=' + v  + '&'\n}\n}\nparamStr = paramStr.substring(0, paramStr.length()-1) + '5ikq43ThTUMHPNTjLcTQv7Jrs66eTIoP'\ndef sign = Util.encodeByMD5(paramStr)\ndef url = map.URL + \"?chc=\" + map.chc + \"&sign=\" + sign\ndef result_ret = [URL:url]\nreturn JsonOutput.toJson(result_ret)\n}\n";
        String script2 = "import groovy.json.JsonOutput\n  import groovy.json.JsonSlurper\n  import com.iflytek.auto.common.utils.GpsUtil\n def parse(String dest, String businessParam) {\n  def data= new JsonSlurper().parseText(dest);\n  def bizParam = new JsonSlurper().parseText(businessParam);\n  if(null != bizParam.longitude && null != bizParam.latitude){\n    Object dataList = data.list;\n   dataList.each{\n   it.distance = GpsUtil.GetDistance(bizParam.latitude.toDouble(), bizParam.longitude.toDouble(), it.latitude.doubleValue(), it.longitude.doubleValue())\n    }\n	}\n	 return JsonOutput.toJson(data);\n}";
        ScriptMessageDto messageDto = new ScriptMessageDto();
        messageDto.setNewScript(script2);
        messageDto.setOldScript(script2);
        //发送功能就一行代码~
        producer.sendTopic(messageDto);
    }
}
