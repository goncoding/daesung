package com.api.daesung.events;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
//@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
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

        ;


        //when


        //then
    }

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


        //when


        //then
    }


}
