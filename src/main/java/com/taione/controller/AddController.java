package com.taione.controller;

import com.taione.pojo.DataEntity;
import com.taione.utlis.BigDecimalUtils;
import com.taione.utlis.ConnectionUtlis;
import com.taione.utlis.DateUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.tags.Param;

import javax.validation.Valid;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Consumer;

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
            Connection connection = ConnectionUtlis.getConnection("jdbc:sqlite:C:\\Users\\td\\adduser\\time_count.db");
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

    /**
     * 随机生成24条数据区间在根据数量决定
     * 随机每日数量
     * 计算公式
     *
     *
     * @param args
     */

    public static void main(String[] args) throws Exception {
        /*获取数据连接*/
        Connection connection = insertRandomTime();
        PreparedStatement preparedStatement = connection.prepareStatement("insert into random_time values(?,?,?)");
        /*设置一个全局变量记录ID*/
        int Id = 1;
        Integer flag = 0;
        /*设置7天时间的每一天的数量*/
        int[] dayCount = {101,127,135,89,76,65,142};
        /*定义需要排除的时间*/
        /*int[] otherTimes= {21,22,23,0,1,2,3,4,5};*/
        String otherTimes = "0,1,2,3,4,5,6,7,8";
        List<Integer> list = new ArrayList();
        for (int i=0;i<24; i++){
            list.add(i);
        }

        Object[] hours =  list.toArray();
        for (int count : dayCount) {
            loop1: for (Object hour : hours) {
                if (otherTimes.contains(hour.toString())){
                    continue loop1;
                }else {
                    preparedStatement.setInt(1,Id);
                    preparedStatement.setInt(2,getRandomtime(count));
                    preparedStatement.setInt(3,flag);
                    preparedStatement.executeUpdate();
                    System.out.println(count + "--------->" + hour + "------->" + getRandomtime(count));
                }
              /* for (Integer otherTime :otherTimes) {
                    if (otherTime == hour) {
                        System.out.println("此次时间为排除的时间" + otherTime);
                        continue loop1;
                    }
                }*/

                Id++;
            }
        }
        ResultSet resultSet = null;
        ConnectionUtlis.close(resultSet,preparedStatement,connection);
    }

    /**
     * 获取随机秒数
     * count 为总数，15个小时排除不需要的时间
     * 
     */
    private static  Integer getRandomtime(Integer count) throws IllegalAccessException {
        double average  = BigDecimalUtils.div(count.doubleValue(), 15d, 0);
        double second = BigDecimalUtils.div(3600, average, 0);
        Random random = new Random();
        Double v = random.nextInt(2000) + second;
        return v.intValue();
    }
    /**
     * 获取数据库，如果有则添加，如果没有则创建
     */
    private static Connection  insertRandomTime() throws Exception {
        Statement statement = null;
        try {
            Connection connection = ConnectionUtlis.getConnection("jdbc:sqlite:random_time.db");
            statement = connection.createStatement();
            statement.setQueryTimeout(30);  // set timeout to 30 sec.

            statement.executeUpdate("drop table if exists random_time");
            statement.executeUpdate("create table random_time (id integer, second INTEGER ,flag INTEGER )");
            return connection;
        } catch (Exception e) {
            throw  new RuntimeException("创建数据库失败=======>>>"+e);
        }

    }




}
