package com.hand.xy99.user.test;

/**
 * Created by xieshuai on 2018/4/7.
 */
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;
/**
 * SqlSessionFactory的单例类
 */
public class SessionFactoryUtil {
    // 创建需要单例的对象实例
    private static SqlSessionFactory sessionFactory;

    // 私有化构造
    private SessionFactoryUtil(){}

    // 对外提供访问接口
    public static synchronized SqlSession getSession(){
        try {
            InputStream stream = Resources.getResourceAsStream("mybatis-config.xml");
            // 判断SqlSessionFactory是否为空，如果为空则创建
            
            if(sessionFactory==null) {
                sessionFactory = new SqlSessionFactoryBuilder().build( stream );
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sessionFactory.openSession();
    }


}
