package biz.baijing.controller;

import biz.baijing.pojo.Category;
import biz.baijing.pojo.Result;
import biz.baijing.service.CategoryService;
import jakarta.validation.constraints.Pattern;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Update;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Validated
@RequestMapping("/category")
@Slf4j
public class CategoryContorller {

    @Autowired
    private CategoryService categoryService;

    /**
     * 新增分类
     * @param category
     * @return
     */
    @PostMapping
    public Result add(@RequestBody @Validated Category category) {
        log.info("新增分类：{}", category);

        categoryService.add(category);

        return  Result.success();
    }

    /**
     * 当前用户创建的所有分类
     * @return
     */
    @GetMapping
    public Result<List<Category>> listById() {

        List<Category> list = categoryService.listById();

        return Result.success(list);
    }

    /**
     * 查询分类详情根据Id,登录用户创建的
     * @param id
     * @return
     */
    @GetMapping("/detail")
    public Result<Category> detail(Integer id) {
        Category category = categoryService.findById(id);

        return Result.success(category);
    }

    /**
     * 更新文章分类
     * @param category
     * @return
     */
    @PutMapping
    public Result update(@RequestBody @Validated Category category) {

        categoryService.update(category);
        
        return Result.success();
    }

}
