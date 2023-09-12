package com.alham.ggudok.tempadmin.service.subs;

import com.alham.ggudok.entity.subs.Category;
import com.alham.ggudok.entity.subs.Subs;
import com.alham.ggudok.entity.subs.SubsContent;
import com.alham.ggudok.entity.subs.SubsRank;
import com.alham.ggudok.repository.subs.CategoryRepository;
import com.alham.ggudok.repository.subs.SubsRepository;
import com.alham.ggudok.tempadmin.dto.subs.AdminSubsRankDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 기본적인 Transactional 설정
 *
 */
@Service
@RequiredArgsConstructor
@Transactional
public class AdminSubsService {

    private final CategoryRepository categoryRepository;
    private final SubsRepository subsRepository;

    public Category createCategory(String categoryName) {

        Category saveCategory = categoryRepository.save(new Category(categoryName));

        return saveCategory;

    }

    public void deleteCategory(Long categoryId) {
        categoryRepository.findById(categoryId).ifPresent(category -> categoryRepository.delete(category));
    }

    public Subs createSubs(String subsName, Long categoryId) {

        Subs subs = new Subs(subsName);
        categoryRepository.findById(categoryId).ifPresent(category -> subs.addCategory(category));
        subsRepository.save(subs);

        return subs;
    }

    public Long addSubsRank(Long subsId, AdminSubsRankDto subsRankDto) {


        SubsRank subsRank = new SubsRank(subsRankDto.getRankName(), subsRankDto.getPrice(), subsRankDto.getRankLevel());
        Subs subs = subsRepository.findById(subsId).get();

        subsRank.addSubs(subs);

        return subsId;
    }

    public int addSubsContent(Long subsId, String subsRankName, String content) {
        Subs subs = subsRepository.findById(subsId).get();
        SubsContent subsContent = new SubsContent(content);

        SubsRank subsRank = subs.getSubsRanks().stream()
                .filter(s -> s.getRankName().equals(subsRankName))
                .findFirst().get();

        subsContent.addSubsRank(subsRank);

        return subsRank.getContents().size();

    }


    public void deleteSubsContent(Long subsId,Long ContentId) {


    }

    public void updateSubsContent(Long subsId, String subsRankName, String content) {


    }


    public List<Subs> findSubs() {
        return subsRepository.findAll();
    }
}
