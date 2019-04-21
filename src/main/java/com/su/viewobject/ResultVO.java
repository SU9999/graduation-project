package com.su.viewobject;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

/**
 * 作为前后端交互的最外层json格式对象
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResultVO<T> {

    /** 返回的错误码：0表示成功*/
    private Integer code;

    /** 返回的提示信息*/
    private String msg = "";

    /** 返回的数据 */
    private T data;
}
