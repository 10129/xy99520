package com.hand.xy99.util;

import org.pentaho.di.core.KettleEnvironment;
import org.pentaho.di.core.logging.LogLevel;
import org.pentaho.di.core.util.EnvUtil;
import org.pentaho.di.job.Job;
import org.pentaho.di.job.JobMeta;
import org.pentaho.di.trans.Trans;
import org.pentaho.di.trans.TransMeta;

import java.util.Map;

/**
 * @author shuai.xie
 * @since 1.0.0
 */
public class KettleUtil {
    /**
     * Java调用kettle job
     * @param jobPath
     */
    public  void runJob(String jobPath) {
        try {
            KettleEnvironment.init();
            // jobname 是Job脚本的路径及名称
            JobMeta jobMeta = new JobMeta(jobPath, null);
            Job job = new Job(null, jobMeta);
            // 向Job 脚本传递参数，脚本中获取参数值：${参数名}
            job.start();
            job.waitUntilFinished();
            if (job.getErrors() > 0) {
                throw new Exception(
                        "There are errors during job exception!(执行job发生异常)");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Java调用kettle转换
     * @param transFile
     * @param para
     * @throws Exception
     */
    public  void runTrans(String transFile, Map<String, String> para) throws Exception {
        KettleEnvironment.init();
        EnvUtil.environmentInit();
        String basePath = (String) this.getClass().getResource("/").getFile().replace("hap", "oos") + "com/kettle/";
        TransMeta tm = new TransMeta(basePath+transFile);
        tm.setLogLevel(LogLevel.DEBUG);
        Trans trans = new Trans(tm);
        for (Map.Entry<String, String> entry : para.entrySet()) {
            //设置命名参数
            trans.setVariable(entry.getKey(), entry.getValue());
        }
        trans.execute(null);
        trans.waitUntilFinished();
        // 抛出异常
        if (trans.getErrors() > 0) {
            throw new Exception("There ("+transFile+") are errors during transformation exception!");
        }
    }

}
