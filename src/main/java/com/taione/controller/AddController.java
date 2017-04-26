package com.taione.controller;

import com.taione.pojo.DataEntity;
import com.taione.utlis.BigDecimalUtils;
import com.taione.utlis.ConnectionUtlis;
import com.taione.utlis.DateUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;

/**
 * Created by td on 2017/4/26.
 */
@RestController
public class AddController {
    /**
     * 需要接受三个参数
     * 总数
     * 时间
     * 小时所对应的百分比
     * 每次设置就发送一个ajax来写入数据库
     */
    @RequestMapping(value = "/index",method = RequestMethod.GET)
    public String index(){
        return "hello";
    }


    @RequestMapping(value = "/add/user",method = RequestMethod.POST)
    public String addTrank(@Valid @RequestBody DataEntity entity) throws Exception {
        try {
            Connection connection = ConnectionUtlis.getConnection();
            String sql = "insert into time_count (data_count,one,two,three,day_time) values(?,?,?,?,?)";
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet rs = insertDataToLite(statement,entity.getDataCount(),entity.getDayTime(),entity.getOne(),entity.getTwo(),entity.getThree());
            ConnectionUtlis.close(rs,statement,connection);
            return "success";
        } catch (Exception e) {
            e.printStackTrace();
            throw  new RuntimeException("错误----------->>>"+e);
        }

    }
    /**
     * 这里是计算并执行插入操作的逻辑
     * @param statement
     * @param dataCount
     *@param dayTime
     * @param one
     * @param two
     * @param three @return
     */
    private  ResultSet insertDataToLite(PreparedStatement statement, String dataCount, String dayTime, String one, String two, String three) throws SQLException, IllegalAccessException {
        //TODO 准备数据库所需要数据，根据页面设置的总数来计算每个时间段所需要的秒数，最后添加到数据库中。
        /**
         * 1.页面需要给我总数
         * 2.需要确定那个时间段比较集中生成数据
         * 3.计算所需要秒数
         * 4.写入到sqlite的db文件中
         */
        Random random = new Random();
        int i1 = random.nextInt(50);
        int count = Integer.parseInt(dataCount) +i1;
        int time = getTime(count, 11,Double.valueOf(one));
        int time1 = getTime(count, 7,Double.valueOf(two));
        int time2 = getTime(count, 6,Double.valueOf(three));
        /*String dateTime = DateUtils.format(DateUtils.now(), DateUtils.CHINA_DATE_PATTERN);*/
        statement.setInt(1,count);
        statement.setInt(2,time);
        statement.setInt(3,time1);
        statement.setInt(4,time2);
        statement.setString(5,dayTime);
        int i = statement.executeUpdate();
        ResultSet resultSet = null;
        /*测试写入是否成功*/
        return resultSet;
    }
    private  int getTime(int count, int hour,Double percentum) throws IllegalAccessException {
        Random random = new Random();
        int nextInt = random.nextInt(10);
        double num = BigDecimalUtils.mul((double) count, percentum, 0);
        double mul = BigDecimalUtils.mul(Double.valueOf(hour), Double.valueOf("3600"));
        Double div = BigDecimalUtils.div(mul, num, 0);
        int abs = Math.abs(nextInt+div.intValue());
        return  abs;
    }

}
