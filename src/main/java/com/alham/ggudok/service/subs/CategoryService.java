package com.alham.ggudok.service.subs;

import com.alham.ggudok.entity.subs.Category;
import com.alham.ggudok.repository.subs.CategoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
@Slf4j
@Transactional
public class CategoryService {

    private final CategoryRepository categoryRepository;


    public Category findCateByEngWithSubs(String categoryEng) {
        log.info("findCateByEngWithSubs()");
        return categoryRepository.findCateByEngWithSubs(categoryEng).orElse(new Category("category Nothing"));
    }

    public List<Category> findAllCategoryWithSubsCnt() {
        log.info("findAllCategoryWithSubsCnt()");
        return categoryRepository.findAllCategoryWithSubsCnt();
    }

    public List<Category> findAllCategory() {
        log.info("findAllCategory()");
        return categoryRepository.findAll();
    }
}
