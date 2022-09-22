package com.api.daesung.events;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder @NoArgsConstructor @AllArgsConstructor
@Data
public class EventDto {

    private String name;
    private String description;
    //등록시작일시
    private LocalDateTime beginEnrollmentDateTime;
    //등록종료일시
    private LocalDateTime closeEnrollmentDateTime;
    //이벤트 시작일시
    private LocalDateTime beginEventDateTime;
    private LocalDateTime endEventDateTime;
    private String location; // (optional) 이게 없으면 온라인 모임
    private int basePrice; // (optional)
    private int maxPrice; // (optional)
    private int limitOfEnrollment;

}
