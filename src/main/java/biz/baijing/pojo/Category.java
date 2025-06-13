package biz.baijing.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.groups.Default;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.ibatis.annotations.Update;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Category {
    @NotNull(groups = Update.class)
    private Integer id;//主键ID
    @NotEmpty
    @Pattern(regexp = "^\\S{3,10}$")
    private String categoryName;//分类名称
//    @NotEmpty(groups = {Add.class,Update.class})
    @NotEmpty
    @Pattern(regexp = "^\\S{3,10}$")
    private String categoryAlias;//分类别名
    private Integer createUser;//创建人ID
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;//创建时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;//更新时间


    // 无分组，默认属于 default
    // 分组继承， A extends B

    // 定义 groups 分组
    public interface Add extends Default {

    }

    public interface Update extends Default {

    }
}
