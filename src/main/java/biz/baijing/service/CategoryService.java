package biz.baijing.service;

import biz.baijing.pojo.Category;
import jakarta.validation.constraints.Pattern;

public interface CategoryService {

    /**
     * 新增分类
     * @param category
     */
    void add(@Pattern(regexp = "^\\S{3,10}$") Category category);
}
