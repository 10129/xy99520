package com.hand.xy99.test.dto;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 项目资源DTO
 *
 * @author guan.li
 * @date 2018-11-05
 */

public class ProjectResource  {

    public static final String FIELD_ID = "id";
    public static final String FIELD_PROJECT_ID = "projectId";
    public static final String FIELD_RESOURCE_TYPE_CODE = "resourceTypeCode";
    public static final String FIELD_RESOURCE_CHILD_CODE = "resourceChildCode";
    public static final String FIELD_RESOURCE_CODE = "resourceCode";
    public static final String FIELD_RESOURCE_DESC = "resourceDesc";
    public static final String FIELD_TOTAL_COUNT = "totalCount";
    public static final String FIELD_INSIDE_COUNT = "insideCount";
    public static final String FIELD_EXTERNAL_COUNT = "externalCount";
    public static final String FIELD_RESOURCE_UOM = "resourceUom";
    public static final String FIELD_RESOURCE_STATUS = "resourceStatus";
    public static final String FIELD_PRICE = "price";
    public static final String FIELD_BUDGET_PRICE = "budgetPrice";
    public static final String FIELD_LAST_PRICE = "lastPrice";
    public static final String FIELD_PROGRAM_APPLICATION_ID = "programApplicationId";
    public static final String FIELD_PROGRAM_UPDATE_DATE = "programUpdateDate";
    public static final String FIELD_RESOURCE_NAME = "resourceName";
    public static final String FIELD_RESOURCE_CHILE_NAME= "resourceChildName";
    public static final String FIELD_RESOURCE_AVAILABLE_COUNT= "resourceAvailableCount";
    public static final String FIELD_RESOURCE_PERSON_COUNT = "resourcePersonCount";
    public static final String FIELD_RESOURCE_STATUS_MEANING = "resourceStatusMeaning";
    public static final String FIELD_TYPE_NAME = "typeName";


  
    private Long resourceId;

  
    private String sourceType;

    /**
     * 资源对应主键
     */
    private Long sourceKey;


    /**
     * 项目id
     */
    private Long projectId;

    /**
     * 申报部门
     */
    private Long resourceUnitId;

    /**
     * 提交人部门id
     */
    private Long submitUnitId;

    /**
     * 使用天数
     */
    private Long dayNumber;

    /**
     * 预算描述
     */
    private String resourceDesc;

    /**
     * 次数
     */
    private Long times;

    /**
     * 需求总数量
     */
    private Long totalCount;

    /**
     * 外包数量（人数）
     */
    private Long externalCount;

    /**
     * 内部数量(在库)
     */
    private Long insideCount;

    /**
     * 资源单位
     */
    private String resourceUom;

    /**
     * 预算状态
     */
    private String budgetStatus;

    /**
     * 资源状态
     */
    private String resourceStatus;

    /**
     * 参考单价
     */

    private String unitPrice;

    /**
     * 预算单价
     */
    private BigDecimal budgetPrice;

    /**
     * 上一版本预算金额
     */
    private BigDecimal lastPrice;

    /**
     * 项目编号
     */

    private String projectNumber;

    /**
     * 资源类型(资源的信息从资源对应的表中直接获取填充)
     */

    private String typeName;

    /**
     * 资源类型ID
     */

    private String typeId;

    /**
     * 资源名称
     */

    private String resourceName;

    /**
     * 提交人部门名称
     */

    private String submitUnitName;

    /**
     * 预算总价
     */

    private String totalPrice;

    /**
     * 申报部门名称
     */

    private String resourceUnitName;

    /**
     * 预算类型
     */

    private String budgetType;

    private Long programApplicationId;

    private Date programUpdateDate;

    /**
     * 在库数量
     */

    private BigDecimal resourceAvailableCount;

    /**
     * 节目名称
     */
     
    private String projectName;

    /**
     * 项目角色
     */
     
    private String roleCode;

    /**
     * 辅助字段，用来判断项目的某个资源是否有预算
     */
     
    private Boolean isTrue;

     
    private String flag;

    /**
     * 状态含义
     */
     
    private String resourceStatusMeaning;

    /**
     * 文件id
     */
    private Long fileId;

    /**
     * 资源编码
     */
     
    private String resourceCode;

    /**
     * 检查状态
     */
     
    private String checkStatus;

    /**
     * 项目播出方式
     */
     
    private String broadcastType;

    /**
     * 项目播出时段
     */
     
    private String broadcastPeriod;

    /**
     * 是否研发项目
     */
     
    private String researchProject;

    /**
     * 项目形式
     */
     
    private String projectWay;

    /**
     * 项目制作开始时间
     */
     
    private Date startDate;

    /**
     * 时间期间
     */
     
    private String yearProjectNumber;
}
