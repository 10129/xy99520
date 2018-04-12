import com.hand.xy99.liquibase.MigrationHelper

def mhi = MigrationHelper.getInstance()
dbType = MigrationHelper.getInstance().dbType()
databaseChangeLog(logicalFilePath:"2017-03-16-init-table-migration.groovy"){

    changeSet(author: "shuai.xie", id: "hmdm_init_table_YXHSCS_ITF_MON_APPLY") {
        sqlFile(path: mhi.dataPath("com/hand/xy99/db/data/" + dbType + "/tables/YXHSCS_ITF_MON_APPLY.sql"), encoding: "UTF-8")
    }
}