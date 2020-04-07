package com.kamixiya.icm.service.common.service.util;

import com.kamixiya.icm.service.common.entity.organization.Organization;

/**
 * 数据过滤路径工具类
 *
 * @author Zhu Jie
 * @date 2020/4/7
 */
public class FilterPathUtil {

    private FilterPathUtil() {
    }

    public static String calculateFilterPath(Organization organization) {
        return recursionFilterPath(organization, "");
    }

    public static String calculateOrganizationFilterPath(Organization organization) {
        return recursionOrganizationFilterPath(organization, "");
    }

    private static String recursionFilterPath(Organization organization, String filterPath) {
        if (organization.getId() != null) {
            switch (organization.getOrganizationType()) {
                case UNIT:
                    filterPath = organization.getUnit().getId().toString() + "-" + filterPath;
                    break;
                case POSITION:
                    filterPath = organization.getPosition().getId() + "-" + filterPath;
                    break;
                case DEPARTMENT:
                    filterPath = organization.getDepartment().getId() + "-" + filterPath;
                    break;
                default:
                    break;
            }
        }
        if (organization.getParent() != null) {
            filterPath = recursionFilterPath(organization.getParent(), filterPath);
        }
        return filterPath;
    }


    private static String recursionOrganizationFilterPath(Organization organization, String orgFilterPath) {
        if (organization.getId() != null) {
            orgFilterPath = organization.getId().toString() + "-" + orgFilterPath;
        }
        if (organization.getParent() != null) {
            orgFilterPath = recursionOrganizationFilterPath(organization.getParent(), orgFilterPath);
        }
        return orgFilterPath;
    }

}
