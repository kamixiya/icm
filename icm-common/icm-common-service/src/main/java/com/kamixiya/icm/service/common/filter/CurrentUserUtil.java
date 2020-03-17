package com.kamixiya.icm.service.common.filter;

/**
 * 用户工具类
 *
 * @author Zhu Jie
 * @date 2020/3/11
 */
public class CurrentUserUtil {

    private final ThreadLocal<String> REMOTE_ADDRESS_USER_ID = new ThreadLocal<>();
    private final ThreadLocal<String> REMOTE_ADDRESS_WORKING_ORG_ID = new ThreadLocal<>();

    private static final CurrentUserUtil CURRENT_USER_UTIL = new CurrentUserUtil();

    /**
     * 客户端用户ID，例如业务系统调用流程服务器需将业务系统用户ID作为请求头传过去
     */
    private final ThreadLocal<String> CLIENT_USER_ID = new ThreadLocal<>();

    private CurrentUserUtil() {
    }

    public static CurrentUserUtil getInstance() {
        return CURRENT_USER_UTIL;
    }

    public String getUserId() {
        return REMOTE_ADDRESS_USER_ID.get();
    }

    public void setUserId(String userId) {
        REMOTE_ADDRESS_USER_ID.remove();
        REMOTE_ADDRESS_USER_ID.set(userId);
    }

    public String getClientUserId() {
        return CLIENT_USER_ID.get();
    }

    public void setClientUserId(String clientUserId) {
        CLIENT_USER_ID.remove();
        CLIENT_USER_ID.set(clientUserId);
    }

    public String getWorkingOrganizationId() {
        return REMOTE_ADDRESS_WORKING_ORG_ID.get();
    }

    public void setWorkingOrganizationId(String workingOrganizationId) {
        REMOTE_ADDRESS_WORKING_ORG_ID.remove();
        REMOTE_ADDRESS_WORKING_ORG_ID.set(workingOrganizationId);
    }

}
