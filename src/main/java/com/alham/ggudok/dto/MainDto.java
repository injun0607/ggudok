package com.alham.ggudok.dto;

import com.alham.ggudok.dto.subs.SubsDto;
import com.alham.ggudok.entity.subs.Subs;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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


    public void transRecommendBasic(Map<String,List<Subs>> basicRecommend) {
        //성별과 나이를 동시에 가지고있는걸 추천해주어야한다.


    }

    public void transRecommendCustomized(Map<String,List<Subs>> customizedRecommend) {

    }

    public void transDefaultSubs(Map<String,List<Subs>> defaultSubs) {

    }


}
