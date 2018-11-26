package com.boot.test.controller;

import com.fasterxml.jackson.databind.util.JSONPObject;
import com.gitee.hengboy.mybatis.pageable.Page;
import com.gitee.hengboy.mybatis.pageable.request.PageableRequest;
import com.rnjf.bj.bean.Answer;
import com.boot.test.redis.RedisRepository;
import com.rnjf.bj.utils.AnswerUtil;
import com.rnjf.bj.utils.SnowflakeIdWorker;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Created by sunyulei on 2018/5/9.
 */
@RestController
@RequestMapping(value = "/test")
public class TestController extends BaseController{

    @Autowired
    RedisRepository redisRepository;

    @RequestMapping(value = "/setTestResult", method = RequestMethod.POST)
    public Answer setTestResult(){
        try {

            //唯一id获取方法
            SnowflakeIdWorker snowflakeIdWorker = new SnowflakeIdWorker();
            long id = snowflakeIdWorker.nextId();

            redisRepository.setExpire("sunyulei",id+"",60, TimeUnit.SECONDS);

            return AnswerUtil.success();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return AnswerUtil.fail();
    }


    @RequestMapping(value = "/getTestResult", method = RequestMethod.POST)
    public Answer getTestResult(){
        try {
            String dvlae = redisRepository.getValue("sunyulei");

            return AnswerUtil.successResult(dvlae);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return AnswerUtil.fail();
    }


    //分页示例
    @RequestMapping(value = "/testFenYe", method = RequestMethod.POST)
    public String testFenYe(){
        try {
            //Page<实体类或map> page = PageableRequest.of(当前页,1开始, 每页条数).request(() -> 查询方法,例如usermapper.findByid() );

            //例如  Page<UserEntity> page = PageableRequest.of(1, 5).request(() -> userMapper.selectAll());

            //Page 内字段含义

//            data 分页后的数据列表，具体的返回值可以使用Page<T>泛型接收
//            totalPages 总页数
//            totalElements 总条数
//            pageIndex 当前页码
//            pageSize 每页限制条数
//            offset 分页开始位置
//            endRow 分页结束位置
//            hasNext 是否存在下一页，true：存在，false：不存在
//            hasPrevious 是否存在上一页，true：存在，false：不存在
//            isFirst 是否为首页，true：首页，false：非首页
//            isLast 是否为末页，true：末页，false：非末页

        } catch (Exception e) {
            e.printStackTrace();
        }
        return "dd";
    }
}
