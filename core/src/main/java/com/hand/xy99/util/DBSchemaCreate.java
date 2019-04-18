package com.hand.xy99.util;

import org.activiti.engine.ProcessEngineConfiguration;
import org.activiti.engine.impl.cfg.ProcessEngineConfigurationImpl;

/**
 * 创建activiti的数据库
 * @author admin
 *
 */
public class DBSchemaCreate {

  public static void main(String[] args) {
   ProcessEngineConfiguration
   .createProcessEngineConfigurationFromResourceDefault()
   .setDatabaseSchemaUpdate(ProcessEngineConfigurationImpl.DB_SCHEMA_UPDATE_CREATE)
   .buildProcessEngine();
   }
  
}
