package com.alham.ggudok.service.subs;

import com.alham.ggudok.entity.Tag;
import com.alham.ggudok.repository.subs.SubsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class SubsService {

    private final SubsRepository subsRepository;


    /**
     * 해당 subs 와 관련있는 태그들을 모두 찾아온다.
     * @param subsId
     * @return
     */
    public List<Tag> findTagsBySubsId(Long subsId) {
        subsRepository.findSubsByIdWithTag(subsId);
        return null;
    }
}
