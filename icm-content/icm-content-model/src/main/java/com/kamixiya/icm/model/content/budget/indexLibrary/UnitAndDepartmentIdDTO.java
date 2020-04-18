package com.kamixiya.icm.model.content.budget.indexLibrary;

import lombok.Getter;
import lombok.Setter;

/**
 * UnitAndDepartmentIdDTO
 *
 * @author Zhu Jie
 * @date 2020/4/17
 */
@Getter
@Setter
public class UnitAndDepartmentIdDTO {
    private Long unitId;

    private Long departmentId;

    public boolean allHasValue() {
        return this.unitId != null && this.departmentId != null;
    }
}

