package com.api.daesung.events;

import com.api.daesung.common.RestDocsConfiguration;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.api.Assertions;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.springframework.restdocs.headers.HeaderDocumentation.*;
import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.linkWithRel;
import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.links;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
//@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureRestDocs
@Import(RestDocsConfiguration.class)
public class EventControllerTests {

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    //mock 객체라서 return되는 값이 다  null이다..
    //controller에서 id가 null이라 nullpoint 발생
//    @MockBean
    @Autowired
    EventRepository eventRepository;

    @DisplayName("정상적으로 이벤트를 생성하는 테스트")
    @Test
    public void test01() throws Exception {

        EventDto event = EventDto.builder()
                .name("spiring")
                .description("rest api")
                .beginEnrollmentDateTime(LocalDateTime.of(2018, 11, 22, 12, 33, 44))
                .closeEnrollmentDateTime(LocalDateTime.of(2018, 11, 22, 12, 33, 44))
                .beginEventDateTime(LocalDateTime.of(2018, 11, 22, 12, 33, 44))
                .endEventDateTime(LocalDateTime.of(2018, 11, 22, 12, 33, 44))
                .basePrice(100)
                .maxPrice(200)
                .limitOfEnrollment(100)
                .location("강남역 팩토리")
                .build();

        mockMvc.perform(post("/api/events/")
                         .contentType(MediaType.APPLICATION_JSON)
                         .accept(MediaTypes.HAL_JSON_VALUE)
                         .content(objectMapper.writeValueAsString(event))
                 )
                 .andDo(print())
                 .andExpect(status().isCreated())
                 .andExpect(jsonPath("id").exists())
                 .andExpect(header().exists(HttpHeaders.LOCATION))
                 .andExpect(header().string(HttpHeaders.CONTENT_TYPE,"application/hal+json;charset=utf-8"))
                 .andExpect(jsonPath("id").value(Matchers.not(100)))
                 .andExpect(jsonPath("free").value(Matchers.not(true)))
                //hateoas
                 .andExpect(jsonPath("_links.query-events").exists())
                 .andExpect(jsonPath("_links.self").exists())
                 .andExpect(jsonPath("_links.update-event").exists())
                 .andDo(document("create-event",
                         links(
                                 linkWithRel("self").description("link to self"),
                                 linkWithRel("query-events").description("link to query-events"),
                                 linkWithRel("update-event").description("link to update-event")
                         ),
                         requestHeaders(
                                 headerWithName(HttpHeaders.ACCEPT).description("accept header"),
                                 headerWithName(HttpHeaders.CONTENT_TYPE).description("content type")
                         ),
                         requestFields(
                            fieldWithPath("name").description("이름"),
                            fieldWithPath("description").description("설명"),
                            fieldWithPath("beginEnrollmentDateTime").description("new beginEnrollmentDateTime"),
                            fieldWithPath("closeEnrollmentDateTime").description("new closeEnrollmentDateTime"),
                            fieldWithPath("beginEventDateTime").description("new beginEventDateTime"),
                            fieldWithPath("endEventDateTime").description("new endEventDateTime"),
                            fieldWithPath("location").description("new location"),
                            fieldWithPath("basePrice").description("기본값"),
                            fieldWithPath("maxPrice").description("최고가"),
                            fieldWithPath("limitOfEnrollment").description("new limitOfEnrollment")
                         ),
                         responseHeaders(
                                 headerWithName(HttpHeaders.LOCATION).description("location header"),
                                 headerWithName(HttpHeaders.CONTENT_TYPE).description("content type")
                         ),
                         responseFields(
                                 fieldWithPath("id").description("아이디"),
                                 fieldWithPath("name").description("이름"),
                                 fieldWithPath("description").description("설명"),
                                 fieldWithPath("beginEnrollmentDateTime").description("new beginEnrollmentDateTime"),
                                 fieldWithPath("closeEnrollmentDateTime").description("new closeEnrollmentDateTime"),
                                 fieldWithPath("beginEventDateTime").description("new beginEventDateTime"),
                                 fieldWithPath("endEventDateTime").description("new endEventDateTime"),
                                 fieldWithPath("location").description("new location"),
                                 fieldWithPath("basePrice").description("기본값"),
                                 fieldWithPath("maxPrice").description("최고가"),
                                 fieldWithPath("limitOfEnrollment").description("new limitOfEnrollment"),
                                 fieldWithPath("free").description("무료 여부"),
                                 fieldWithPath("offline").description("오프라인 여부"),
                                 fieldWithPath("eventStatus").description("eventStatus 여부"),
                                 fieldWithPath("_links.self.href").description("self 여부"),
                                 fieldWithPath("_links.query-events.href").description("query-events 여부"),
                                 fieldWithPath("_links.update-event.href").description("update 여부")

                        )
                 ))
        ;

    }

    @DisplayName("dto값을 벗어난 값이 들어왔을 경우에 에러 발생")
    @Test
    public void test01_badRequest() throws Exception {

        Event event = Event.builder()
                .id(100)
                .name("spiring")
                .description("rest api")
                .beginEnrollmentDateTime(LocalDateTime.of(2018, 11, 22, 12, 33, 44))
                .closeEnrollmentDateTime(LocalDateTime.of(2018, 11, 22, 12, 33, 44))
                .beginEventDateTime(LocalDateTime.of(2018, 11, 22, 12, 33, 44))
                .endEventDateTime(LocalDateTime.of(2018, 11, 22, 12, 33, 44))
                .basePrice(100)
                .maxPrice(200)
                .limitOfEnrollment(100)
                .location("강남역 팩토리")
                .free(true)
                .offline(false)
                .build();

        mockMvc.perform(post("/api/events/")
                         .contentType(MediaType.APPLICATION_JSON)
                         .accept(MediaTypes.HAL_JSON_VALUE)
                         .content(objectMapper.writeValueAsString(event))
                 )
                 .andDo(print())
                 .andExpect(status().isBadRequest())

        ;

    }
    @DisplayName("입력 값이 비어있는 경우에 에러가 발생하는 테스트")
    @Test
    public void createEvent_Empty() throws Exception {
        EventDto eventDto = EventDto.builder().build();
        //필드를 보내긴 하는데 값이 비어있음...
        //그런데 201 처리가 된다... 400으로 변경 필요...
        mockMvc.perform(post("/api/events")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(eventDto))
                )
                .andExpect(status().isBadRequest());
    }

    //시작날짜가 종료날짜보다 높은 경우
    //기본 가격이 max가격보다 높은 경우
    //일반 validation 어노테이션으로 처리가 안됨... validator를 추가해야함..
    @DisplayName("입력값이 잘못된 경우에 발생하는 테스트")
    @Test
    public void createEvent_Request_Wrong_Input() throws Exception {

        EventDto eventDto = EventDto.builder()
                .name("spiring")
                .description("rest api")
                .beginEnrollmentDateTime(LocalDateTime.of(2018, 11, 26, 12, 33, 44))
                .closeEnrollmentDateTime(LocalDateTime.of(2018, 11, 25, 12, 33, 44))
                .beginEventDateTime(LocalDateTime.of(2018, 11, 24, 12, 33, 44))
                .endEventDateTime(LocalDateTime.of(2018, 11, 23, 12, 33, 44))
                .basePrice(10000)
                .maxPrice(200)
                .limitOfEnrollment(100)
                .location("강남역 팩토리")
                .build();

        mockMvc.perform(post("/api/events")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(eventDto))
                )
                .andDo(print())
                .andExpect(status().isBadRequest())
                //global에러시에는 아래 부분이 깨지므로 주석
                .andExpect(jsonPath("$[0].objectName").exists())
//                .andExpect(jsonPath("$[0].field").exists())
                .andExpect(jsonPath("$[0].defaultMessage").exists())
                .andExpect(jsonPath("$[0].code").exists())
//                .andExpect(jsonPath("$[0].rejectedValue").exists())
        ;
    }


    @DisplayName(" Controller base,max price값에 따른 유료 무료 판단 및 location 유무에 따른 true")
    @Test
    public void test012() throws Exception {

        EventDto event = EventDto.builder()
                .name("spiring")
                .description("rest api")
                .beginEnrollmentDateTime(LocalDateTime.of(2018, 11, 22, 12, 33, 44))
                .closeEnrollmentDateTime(LocalDateTime.of(2018, 11, 22, 12, 33, 44))
                .beginEventDateTime(LocalDateTime.of(2018, 11, 22, 12, 33, 44))
                .endEventDateTime(LocalDateTime.of(2018, 11, 22, 12, 33, 44))
                .basePrice(100)
                .maxPrice(200)
                .limitOfEnrollment(100)
                .location("강남역 팩토리")
                .build();

        mockMvc.perform(post("/api/events/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaTypes.HAL_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(event))
                )
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("id").exists())
                .andExpect(header().exists(HttpHeaders.LOCATION))
                .andExpect(header().string(HttpHeaders.CONTENT_TYPE,"application/hal+json;charset=utf-8"))

                //2개의 값은 지금 기본값으로 되어있으므로 false -> 비지니스 로직을 만들어줘야한다...
                //비지니스 로직은 domain에 넣어두면 좋다...

                //기본,max 가격이 있으므로 무료가 아님
                .andExpect(jsonPath("free").value(false))
                //location에 값이 있으므로 offline은 true임
                .andExpect(jsonPath("offline").value(true))

        ;

    }


}











