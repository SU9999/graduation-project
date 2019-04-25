package com.su.form;

import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class CategoryForm {

    /** 类目主键id */
    private Integer categoryId;

    /** 类目名称 */
    @NotEmpty(message = "类目名称不能为空")
    private String categoryName;

    /** 类目编号 */
    @NotNull(message = "类目类型不能为空")
    private Integer categoryType;
}
