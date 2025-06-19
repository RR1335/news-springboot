package biz.baijing.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import static biz.baijing.common.NotionMessage.*;

/**
 * 统一响应结果
 * @param <T>
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Result<T> {
    private Integer code;//业务状态码  0-成功  1-失败
    private String message;//提示信息
    private T data;//响应数据

    //快速返回操作成功响应结果(带响应数据)
    public static <E> Result<E> success(E data) {
        return new Result<>(RESULT_SUCCESS, RESULT_STATUS, data);
    }

    //快速返回操作成功响应结果
    public static Result success() {
        return new Result(RESULT_SUCCESS, RESULT_STATUS, null);
    }

    public static Result error(String message) {
        return new Result(RESULT_ERROR, message, null);
    }
}
