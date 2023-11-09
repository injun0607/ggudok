package com.alham.ggudok.dto;

import com.alham.ggudok.dto.subs.EventSubsDto;
import com.alham.ggudok.dto.subs.SubsDto;
import com.alham.ggudok.entity.subs.Subs;
import com.alham.ggudok.tempadmin.dto.subs.category.CategoryDto;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 메인화면에서 사용하는 DTO
 */
@Data
@NoArgsConstructor
public class MainDto {

    private List<EventSubsDto> eventSubs = new ArrayList<>();
    private List<SubsDto> recommendBasic = new ArrayList<>();
    private List<SubsDto> recommendCustomized = new ArrayList<>();
    private List<SubsDto> defaultSubs = new ArrayList<>();

    private List<CategoryDto> categoryList = new ArrayList<>();

    private List<String> memberDefaultTag = new ArrayList<>();


    /**
     * customized 추천 서비스는 회원이 좋아하는 태그기반
     * @param subsList
     */
    public void transRecommendCustomized(List<Subs> subsList) {

        this.recommendCustomized = addSubsList(this.recommendCustomized , subsList);

    }

    /**
     * basic 추천서비스는 나이와 성별 기반
     * @param subsList - 나이와 성별이 속한 subsList
     */
    public void transRecommendBasic(List<Subs> subsList) {
        this.recommendBasic = addSubsList(this.recommendBasic,subsList);
    }

    /**
     * default 추천은 subsList 순위 기반
     * @param subsList
     */
    public void transDefaultSubs(List<Subs> subsList) {
        this.defaultSubs = addSubsList(this.defaultSubs,subsList);

    }

    public List<SubsDto> addSubsList(List<SubsDto> recommendSubsList, List<Subs> inputSubsList) {
        List<SubsDto> subsDtoList = new ArrayList<>();
        if (this.recommendBasic.size() == 0) {
            //recommendBasic을 만들때
            for (int i = 0; i < inputSubsList.size(); i ++){
                //최대 10개까지
                if (i > 9) {
                    break;
                }

                if (inputSubsList.get(i) != null) {
                    Subs subs = inputSubsList.get(i);

                    SubsDto subsDto = new SubsDto();
                    subsDto.setId(subs.getSubsId());
                    subsDto.setName(subs.getSubsName());
                    subsDto.setImage(subs.getImage());
                    subsDto.setIcon(subs.getIcon());
                    subsDto.setTags(subs.getSubsRelTags().stream()
                            .map(srt -> srt.getTag())
                            .collect(Collectors.toList()));

                    subsDtoList.add(subsDto);
                } else {
                    break;
                }
            }

        }else{
            //그 이외의 추천리스트를 만들때
            int i =-1;
            while (subsDtoList.size() != 10) {
                ++i;
                if (inputSubsList.get(i) != null) {

                    Subs subs = inputSubsList.get(i);
                    SubsDto subsDto = new SubsDto();
                    subsDto.setId(subs.getSubsId());
                    //중복추천 방지
                    if (this.recommendBasic.contains(subsDto)) {
                        continue;
                    }

                    subsDto.setName(subs.getSubsName());
                    subsDto.setImage(subs.getImage());
                    subsDto.setIcon(subs.getIcon());
                    subsDto.setTags(subs.getSubsRelTags().stream()
                            .map(srt -> srt.getTag())
                            .collect(Collectors.toList()));

                    subsDtoList.add(subsDto);
                }else {
                    break;
                }
            }

        }

        return subsDtoList;

    }


}
