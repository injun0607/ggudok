package com.alham.ggudok.repository.subs;

import com.alham.ggudok.entity.subs.Subs;
import com.alham.ggudok.entity.subs.SubsContent;
import com.alham.ggudok.entity.subs.SubsRank;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface SubsRepositoryCustom {

    List<SubsRank>findSubsByIdWithAllContent(@Param("subsId") Long subsId);

}
