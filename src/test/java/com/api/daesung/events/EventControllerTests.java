package com.api.daesung.events;

import com.api.daesung.common.BaseControllerTest;
import com.api.daesung.common.RestDocsConfiguration;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.api.Assertions;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.stream.IntStream;

import static org.springframework.restdocs.headers.HeaderDocumentation.*;
import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.linkWithRel;
import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.links;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.put;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
//@AutoConfigureMockMvc

public class EventControllerTests extends BaseControllerTest {

    //mock ???????????? return?????? ?????? ???  null??????..
    //controller?????? id??? null?????? nullpoint ??????
//    @MockBean
    @Autowired
    EventRepository eventRepository;

//    @DisplayName("??????????????? ???????????? ???????????? ?????????")
//    @Test
//    public void test01() throws Exception {
//
//        EventDto event = EventDto.builder()
//                .name("spiring")
//                .description("rest api")
//                .beginEnrollmentDateTime(LocalDateTime.of(2018, 11, 22, 12, 33, 44))
//                .closeEnrollmentDateTime(LocalDateTime.of(2018, 11, 22, 12, 33, 44))
//                .beginEventDateTime(LocalDateTime.of(2018, 11, 22, 12, 33, 44))
//                .endEventDateTime(LocalDateTime.of(2018, 11, 22, 12, 33, 44))
//                .basePrice(100)
//                .maxPrice(200)
//                .limitOfEnrollment(100)
//                .location("????????? ?????????")
//                .build();
//
//        mockMvc.perform(post("/api/events/")
//                         .contentType(MediaType.APPLICATION_JSON)
//                         .accept(MediaTypes.HAL_JSON_VALUE)
//                         .content(objectMapper.writeValueAsString(event))
//                 )
//                 .andDo(print())
//                 .andExpect(status().isCreated())
//                 .andExpect(jsonPath("id").exists())
//                 .andExpect(header().exists(HttpHeaders.LOCATION))
//                 .andExpect(header().string(HttpHeaders.CONTENT_TYPE,"application/hal+json;charset=utf-8"))
//                 .andExpect(jsonPath("id").value(Matchers.not(100)))
//                 .andExpect(jsonPath("free").value(Matchers.not(true)))
//                //hateoas
//                 .andExpect(jsonPath("_links.query-events").exists())
//                 .andExpect(jsonPath("_links.self").exists())
//                 .andExpect(jsonPath("_links.update-event").exists())
//                 .andDo(document("create-event",
//                         links(
//                                 linkWithRel("self").description("link to self"),
//                                 linkWithRel("query-events").description("link to query-events"),
//                                 linkWithRel("update-event").description("link to update-event")
//                         ),
//                         requestHeaders(
//                                 headerWithName(HttpHeaders.ACCEPT).description("accept header"),
//                                 headerWithName(HttpHeaders.CONTENT_TYPE).description("content type")
//                         ),
//                         requestFields(
//                            fieldWithPath("name").description("??????"),
//                            fieldWithPath("description").description("??????"),
//                            fieldWithPath("beginEnrollmentDateTime").description("new beginEnrollmentDateTime"),
//                            fieldWithPath("closeEnrollmentDateTime").description("new closeEnrollmentDateTime"),
//                            fieldWithPath("beginEventDateTime").description("new beginEventDateTime"),
//                            fieldWithPath("endEventDateTime").description("new endEventDateTime"),
//                            fieldWithPath("location").description("new location"),
//                            fieldWithPath("basePrice").description("?????????"),
//                            fieldWithPath("maxPrice").description("?????????"),
//                            fieldWithPath("limitOfEnrollment").description("new limitOfEnrollment")
//                         ),
//                         responseHeaders(
//                                 headerWithName(HttpHeaders.LOCATION).description("location header"),
//                                 headerWithName(HttpHeaders.CONTENT_TYPE).description("content type")
//                         ),
//                         responseFields(
//                                 fieldWithPath("id").description("?????????"),
//                                 fieldWithPath("name").description("??????"),
//                                 fieldWithPath("description").description("??????"),
//                                 fieldWithPath("beginEnrollmentDateTime").description("new beginEnrollmentDateTime"),
//                                 fieldWithPath("closeEnrollmentDateTime").description("new closeEnrollmentDateTime"),
//                                 fieldWithPath("beginEventDateTime").description("new beginEventDateTime"),
//                                 fieldWithPath("endEventDateTime").description("new endEventDateTime"),
//                                 fieldWithPath("location").description("new location"),
//                                 fieldWithPath("basePrice").description("?????????"),
//                                 fieldWithPath("maxPrice").description("?????????"),
//                                 fieldWithPath("limitOfEnrollment").description("new limitOfEnrollment"),
//                                 fieldWithPath("free").description("?????? ??????"),
//                                 fieldWithPath("offline").description("???????????? ??????"),
//                                 fieldWithPath("eventStatus").description("eventStatus ??????"),
//                                 fieldWithPath("_links.self.href").description("self ??????"),
//                                 fieldWithPath("_links.query-events.href").description("query-events ??????"),
//                                 fieldWithPath("_links.update-event.href").description("update ??????")
//
//                        )
//                 ))
//        ;
//
//    }

    @Test
    @DisplayName("??????????????? ???????????? ???????????? ?????????")
    public void createEvent() throws Exception {
        EventDto event = EventDto.builder()
                .name("Spring")
                .description("REST API Development with Spring")
                .beginEnrollmentDateTime(LocalDateTime.of(2018, 11, 23, 14, 21))
                .closeEnrollmentDateTime(LocalDateTime.of(2018, 11, 24, 14, 21))
                .beginEventDateTime(LocalDateTime.of(2018, 11, 25, 14, 21))
                .endEventDateTime(LocalDateTime.of(2018, 11, 26, 14, 21))
                .basePrice(100)
                .maxPrice(200)
                .limitOfEnrollment(100)
                .location("????????? D2 ????????? ?????????")
                .build();
        mockMvc.perform(post("/api/events/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaTypes.HAL_JSON)
                        .content(objectMapper.writeValueAsString(event)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("id").exists())
                .andExpect(header().exists(HttpHeaders.LOCATION))
                .andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaTypes.HAL_JSON_VALUE+";charset=utf-8"))
                .andExpect(jsonPath("free").value(false))
                .andExpect(jsonPath("offline").value(true))
                .andExpect(jsonPath("eventStatus").value(EventStatus.DRAFT.name()))
                .andDo(document("create-event",
                        links(
                                linkWithRel("self").description("link to self"),
                                linkWithRel("query-events").description("link to query events"),
                                linkWithRel("update-event").description("link to update an existing event")
//                                linkWithRel("profile").description("link to update an existing event")
                        ),
                        requestHeaders(
                                headerWithName(HttpHeaders.ACCEPT).description("accept header"),
                                headerWithName(HttpHeaders.CONTENT_TYPE).description("content type header")
                        ),
                        requestFields(
                                fieldWithPath("name").description("Name of new event"),
                                fieldWithPath("description").description("description of new event"),
                                fieldWithPath("beginEnrollmentDateTime").description("date time of begin of new event"),
                                fieldWithPath("closeEnrollmentDateTime").description("date time of close of new event"),
                                fieldWithPath("beginEventDateTime").description("date time of begin of new event"),
                                fieldWithPath("endEventDateTime").description("date time of end of new event"),
                                fieldWithPath("location").description("location of new event"),
                                fieldWithPath("basePrice").description("base price of new event"),
                                fieldWithPath("maxPrice").description("max price of new event"),
                                fieldWithPath("limitOfEnrollment").description("limit of enrolmment")
                        ),
                        responseHeaders(
                                headerWithName(HttpHeaders.LOCATION).description("Location header"),
                                headerWithName(HttpHeaders.CONTENT_TYPE).description("Content type")
                        ),
                        relaxedResponseFields(
                                fieldWithPath("id").description("identifier of new event"),
                                fieldWithPath("name").description("Name of new event"),
                                fieldWithPath("description").description("description of new event"),
                                fieldWithPath("beginEnrollmentDateTime").description("date time of begin of new event"),
                                fieldWithPath("closeEnrollmentDateTime").description("date time of close of new event"),
                                fieldWithPath("beginEventDateTime").description("date time of begin of new event"),
                                fieldWithPath("endEventDateTime").description("date time of end of new event"),
                                fieldWithPath("location").description("location of new event"),
                                fieldWithPath("basePrice").description("base price of new event"),
                                fieldWithPath("maxPrice").description("max price of new event"),
                                fieldWithPath("limitOfEnrollment").description("limit of enrolmment"),
                                fieldWithPath("free").description("it tells if this event is free or not"),
                                fieldWithPath("offline").description("it tells if this event is offline event or not"),
                                fieldWithPath("eventStatus").description("event status"),
                                fieldWithPath("_links.self.href").description("link to self"),
                                fieldWithPath("_links.query-events.href").description("link to query event list"),
                                fieldWithPath("_links.update-event.href").description("link to update existing event")
//                                fieldWithPath("_links.profile.href").description("link to profile")
                        )
                ))
        ;
    }

    @DisplayName("dto?????? ????????? ?????? ???????????? ????????? ?????? ??????")
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
                .location("????????? ?????????")
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
    @DisplayName("?????? ?????? ???????????? ????????? ????????? ???????????? ?????????")
    @Test
    public void createEvent_Empty() throws Exception {
        EventDto eventDto = EventDto.builder().build();
        //????????? ????????? ????????? ?????? ????????????...
        //????????? 201 ????????? ??????... 400?????? ?????? ??????...
        mockMvc.perform(post("/api/events")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(eventDto))
                )
                .andExpect(status().isBadRequest());
    }

    //??????????????? ?????????????????? ?????? ??????
    //?????? ????????? max???????????? ?????? ??????
    //?????? validation ????????????????????? ????????? ??????... validator??? ???????????????..
    @DisplayName("???????????? ????????? ????????? ???????????? ?????????")
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
                .location("????????? ?????????")
                .build();

        mockMvc.perform(post("/api/events")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(eventDto))
                )
                .andDo(print())
                .andExpect(status().isBadRequest())
                //global??????????????? ?????? ????????? ???????????? ??????
                .andExpect(jsonPath("errors[0].objectName").exists())
                .andExpect(jsonPath("errors[0].defaultMessage").exists())
                .andExpect(jsonPath("errors[0].code").exists())
                .andExpect(jsonPath("_links.index").exists())
//                .andExpect(jsonPath("$[0].field").exists())
//                .andExpect(jsonPath("$[0].rejectedValue").exists())
        ;
    }


    @DisplayName(" Controller base,max price?????? ?????? ?????? ?????? ?????? ??? location ????????? ?????? true")
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
                .location("????????? ?????????")
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

                //2?????? ?????? ?????? ??????????????? ?????????????????? false -> ???????????? ????????? ?????????????????????...
                //???????????? ????????? domain??? ???????????? ??????...

                //??????,max ????????? ???????????? ????????? ??????
                .andExpect(jsonPath("free").value(false))
                //location??? ?????? ???????????? offline??? true???
                .andExpect(jsonPath("offline").value(true))

        ;

    }

    @Test
    @DisplayName("30?????? ???????????? 10?????? ????????? ????????? ????????????")
    public void queryEvents() throws Exception {
        //given
        //event 30??? ??????
        IntStream.range(0,30).forEach(this::generateEvent);

        //when
        mockMvc.perform(get("/api/events")
                        .param("page","1")
                        .param("size","10")
                        .param("sort","name,DESC")
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("page").exists())
                .andExpect(jsonPath("_embedded.eventList[0]._links.self").exists())
                .andExpect(jsonPath("_links.self").exists())
                .andExpect(jsonPath("_links.profile").exists())
                .andDo(document("query-events"))

        ;
    }

    @Test
    @DisplayName("????????? ???????????? ?????? ????????????")
    public void getEvents() throws Exception {
        //given
        Event event = generateEvent(100);

        mockMvc.perform((get("/api/events/{id}", event.getId())))
                .andExpect(status().isOk())
                .andExpect(jsonPath("name").exists())
                .andExpect(jsonPath("id").exists())
                .andExpect(jsonPath("_links.self").exists())
                .andExpect(jsonPath("_links.profile").exists())
                .andDo(document("get-an-event"))
        ;
    }
    @Test
    @DisplayName("?????? ???????????? ???????????? ??? 404 ????????????")
    public void getEvents404() throws Exception {
        //given
        Event event = generateEvent(100);

        mockMvc.perform((get("/api/events/1212", event.getId())))
                .andExpect(status().isNotFound());
        ;
    }
    @Test
    @DisplayName("???????????? ??????????????? ????????????")
    public void updateEvents() throws Exception {
        //given
        Event event = generateEvent(200);
        //entity -> dto
        EventDto eventDto = modelMapper.map(event, EventDto.class);
        String eventName = "Updated Event";
        eventDto.setName(eventName);

        //when & then
        mockMvc.perform(put("/api/events/{id}", event.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(eventDto))
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("name").value(eventName))
                .andExpect(jsonPath("_links.self").exists())
                .andDo(document("update-event"))
        ;
    }

    @Test
    @DisplayName("???????????? ????????? ????????? ????????? ?????? ??????")
    public void updateEvents400() throws Exception {
        //given
        Event event = generateEvent(200);

        EventDto eventDto1 = new EventDto();

        //when & then
        mockMvc.perform(put("/api/events/{id}", event.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(eventDto1))
                )
                .andDo(print())
                .andExpect(status().isBadRequest())
        ;
    }


    @Test
    @DisplayName("???????????? ???????????? ????????? ????????? ?????? ??????")
    public void updateEvents400wrong() throws Exception {
        //given
        Event event = generateEvent(200);

        EventDto eventDto = modelMapper.map(event, EventDto.class);
        eventDto.setBasePrice(20000);
        eventDto.setMaxPrice(1000);

        EventDto eventDto1 = new EventDto();

        //when & then
        mockMvc.perform(put("/api/events/{id}", event.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(eventDto1))
                )
                .andDo(print())
                .andExpect(status().isBadRequest())
        ;
    }

    @Test
    @DisplayName("???????????? ?????? ????????? ?????? ??????")
    public void updateEvents400wrong2() throws Exception {
        //given
        Event event = generateEvent(200);
        EventDto eventDto = modelMapper.map(event, EventDto.class);

        EventDto eventDto1 = new EventDto();

        //when & then
        mockMvc.perform(put("/api/events/123123")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(eventDto1))
                )
                .andDo(print())
                .andExpect(status().isNotFound())
        ;
    }




    private Event generateEvent(int i) {
        Event event = Event.builder()
                .name("event " + i)
                .description("test event")
                .beginEnrollmentDateTime(LocalDateTime.of(2018, 11, 22, 12, 33, 44))
                .closeEnrollmentDateTime(LocalDateTime.of(2018, 11, 22, 12, 33, 44))
                .beginEventDateTime(LocalDateTime.of(2018, 11, 22, 12, 33, 44))
                .endEventDateTime(LocalDateTime.of(2018, 11, 22, 12, 33, 44))
                .basePrice(100)
                .maxPrice(200)
                .limitOfEnrollment(100)
                .location("????????? ?????????")
                .free(false)
                .offline(true)
                .eventStatus(EventStatus.DRAFT)
                .build();

        return eventRepository.save(event);
    }


}











