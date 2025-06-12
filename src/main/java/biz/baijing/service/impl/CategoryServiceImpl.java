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
        category.setUpdateTime(LocalDateTime.now());

        Map<String,Object> map = ThreadLocalUtil.get();
        Integer loginUserId = (Integer) map.get("id");

        Integer createUserId = category.getCreateUser();

        log.info("创建用户ID {} 和 当前用户 ID {} ", createUserId,loginUserId);

/*        if (loginUserId != createUserId ) {
            throw new RuntimeException("修改用户和创建用户不一致，不可修改。");
        }*/

        categoryMapper.update(category);
    }
}
