package biz.baijing.service;

import biz.baijing.pojo.Category;
import jakarta.validation.constraints.Pattern;

import java.util.List;

public interface CategoryService {

    /**
     * 新增分类
     * @param category
     */
    void add(@Pattern(regexp = "^\\S{3,10}$") Category category);

    /**
     * 查询分类列表根据用户id
     * @return
     */
    List<Category> listById();

    /**
     * 根据Id查询分类详情
     * @param id
     * @return
     */
    Category findById(Integer id);
}
