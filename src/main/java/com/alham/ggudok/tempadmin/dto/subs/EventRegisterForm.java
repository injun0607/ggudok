package com.alham.ggudok.tempadmin.dto.subs;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class EventRegisterForm {


    private String infoTag = "";

    //이벤트 간략정보
    private String info = "";

    private String image = "";

    //시작일
    private String startDate;

    //종료일
    private String endDate;

    private boolean isValid;

}
