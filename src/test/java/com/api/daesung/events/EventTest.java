package com.api.daesung.events;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.*;
@SpringBootTest
class EventTest {

    @Test
    public void builder() throws Exception {
        //given
        Event event = Event.builder()
                .name("Inflearn spring rest api")
                .description("rest api development with spring")
                .build();
        assertThat(event).isNotNull();


    }

    @Test
    public void javaBean() throws Exception {
        //given
        Event event = new Event();
        String name = "Event";

        //when
        event.setName("Event");
        String description = "spring";
        event.setDescription(description);

        //then
        assertThat(event.getName()).isEqualTo(name);
        assertThat(event.getDescription()).isEqualTo(description);

    }



    @DisplayName("domain에 무료 유료 비지니스 로직 테스트")
    @ParameterizedTest
//    @CsvSource({
//            "0,0,true",
//            "100,0,false",
//            "0,100,false"
//    })
    @MethodSource("paramsForTestFree")
    public void testFree(int basePrice, int maxPrice, boolean isFree) {


        System.out.println("basePrice = " + basePrice);
        //given
        Event event = Event.builder()
                 .basePrice(basePrice)
                 .maxPrice(maxPrice)
                 .build();

        //when
        event.update();

        //then
        Assertions.assertThat(event.isFree()).isEqualTo(isFree);

    }

    private static Stream<Arguments> paramsForTestFree() { // argument source method
        return Stream.of(
                Arguments.of(0,0, true),
                Arguments.of(100, 0, false),
                Arguments.of(0, 100, false),
                Arguments.of(100, 200, false)
        );
    }


    @DisplayName("domain에 on, offline 비지니스 로직 테스트")
    @ParameterizedTest
//    @CsvSource({
//            "0,0,true",
//            "100,0,false",
//            "0,100,false"
//    })
    @MethodSource("paramsForTestOffline")
    public void onlineOffline() {
        //given
        Event event = Event.builder()
                .location("강남역")
                .build();

        //when
        event.update();

        //then
        Assertions.assertThat(event.isOffline()).isTrue();

        //given
        event = Event.builder()
                .build();

        //when
        event.update();

        //then
        Assertions.assertThat(event.isOffline()).isFalse();

    }
    private static Stream<Arguments> paramsForTestOffline() { // argument source method
        return Stream.of(
                Arguments.of("강남", true),
                Arguments.of(null, false),
                Arguments.of("        ", false)
        );
    }


}