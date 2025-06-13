package biz.baijing.service.impl;

import biz.baijing.mapper.CategoryMapper;
import biz.baijing.pojo.Category;
import biz.baijing.pojo.Result;
import biz.baijing.service.CategoryService;
import biz.baijing.utils.ThreadLocalUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    /**
     * 新增分类
     * @param category
     */
    public void add(Category category) {

        Category cg = categoryMapper.findByName(category.getCategoryName());
        if (cg != null) {
            throw new RuntimeException("分类已存在，请重新输入。");
        }

        // 创建人 ID ， 更新时间
        category.setCreateTime(LocalDateTime.now());
        category.setUpdateTime(LocalDateTime.now());

        Map<String,Object> map = ThreadLocalUtil.get();
        Integer loginUserId = (Integer) map.get("id");
        category.setCreateUser(loginUserId);

        // alias 不可为空格
        if (category.getCategoryAlias().isBlank()) {
            category.setCategoryAlias(category.getCategoryName());
        }

        categoryMapper.add(category);
    }

    /**
     * 分类列表根据用户id
     * @return
     */
    public List<Category> listById() {
        Map<String,Object> map = ThreadLocalUtil.get();
        Integer loginUserId = (Integer) map.get("id");

        return categoryMapper.listById(loginUserId);
    }

    /**
     * 查询分类详情根据Id
     * @param categoryId
     * @return
     */
    public Category findById(Integer categoryId) {
        Map<String,Object> map = ThreadLocalUtil.get();
        Integer loginUserId = (Integer) map.get("id");

        return categoryMapper.findById(categoryId,loginUserId);

    }

    @Override
    public void update(Category category) {

        Map<String,Object> map = ThreadLocalUtil.get();
        Integer loginUserId = (Integer) map.get("id");

        Category cg = categoryMapper.getById(category.getId());

        log.info("<UNK>{}", cg);
        if (category.getCategoryName().equals(cg.getCategoryName())) {
            throw new RuntimeException("分类名已存在，请重新输入。");
        }

        if (category.getCategoryAlias().equals(cg.getCategoryAlias())) {
            throw new RuntimeException("分类别名(alias)已存在，请重新输入。");
        }

        if (loginUserId != cg.getCreateUser()) {
            throw new RuntimeException("分类非当前用户创建，无法修改。");
        }

        category.setUpdateTime(LocalDateTime.now());

        categoryMapper.update(category);
    }
}
