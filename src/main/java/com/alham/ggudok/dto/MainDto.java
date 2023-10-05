package com.alham.ggudok.dto;

import com.alham.ggudok.dto.subs.SubsDto;
import com.alham.ggudok.entity.subs.Subs;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 메인화면에서 사용하는 DTO
 */
@Data
@NoArgsConstructor
public class MainDto {

    private List<String> eventSubs = new ArrayList<>();
    private List<SubsDto> recommendBasic = new ArrayList<>();
    private List<SubsDto> recommendCustomized = new ArrayList<>();
    private List<SubsDto> defaultSubs = new ArrayList<>();



    public void transRecommendCustomized() {

    }

    /**
     * basic 추천서비스는 나이와 성별 기반
     * @param subsList - 나이와 성별이 속한 subsList
     */
    public void transRecommendBasic(List<Subs> subsList) {
        List<SubsDto> basicRecommend = new ArrayList<>();
        for (Subs subs : subsList) {
            SubsDto subsDto = new SubsDto();
            subsDto.setId(subs.getSubsId());
            subsDto.setName(subs.getSubsName());
            subsDto.setImage(subs.getImage());
            subsDto.setIcon(subs.getIcon());
            subsDto.setTags(subs.getSubsRelTags().stream()
                    .map(srt->srt.getTag())
                    .collect(Collectors.toList()));

            basicRecommend.add(subsDto);
        }
        this.recommendBasic = basicRecommend;
    }

    public void transDefaultSubs(List<Subs> subsList) {


    }


}
