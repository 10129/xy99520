package com.hand.xy99.util.operation;

/**
 * @author bingbing.peng@hand-china.com
 * @version 1.0
 * @ClassName OperationType
 * @description
 * @date Create in 2019/4/12
 */

public class OperationType {
    /**
     * 提交订单
     */
    public static final String SUBMIT = "10";
    /**
     * 改单
     */
    public static final String CHANGE_ORDER = "20";
    /**
     * 审批
     */
    public static final String APPROVE = "30";
    /**
     * 拒绝订单
     */
    public static final String REJECT = "40";
    /**
     * 转交确认审批
     */
    public static final String DELIVER_APPROVE = "50";
    /**
     * 转交审批拒绝
     */
    public static final String DELIVER_REJECT = "60";
}
