package Foodfit.BackEnd;

import Foodfit.BackEnd.Domain.Log;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


import static org.assertj.core.api.Assertions.*;

class LogTest {


    @Test
    @DisplayName("에러 로그 생성 테스트")
    void t1(){
        String exceptionMessage = "테스트용 Exception입니다.";
        String causeMessage = "테스트용 Cause입니다";

        RuntimeException cause = new RuntimeException(causeMessage);
        RuntimeException e = new RuntimeException(exceptionMessage, cause);

        Log log = Log.of(Log.LoggingLevel.ERROR, e);

        assertThat(log.getLoggingLevel()).isEqualTo(Log.LoggingLevel.ERROR);
        assertThat(log.getMessage()).isEqualTo(exceptionMessage);
        assertThat(log.getDetail()).isNotEmpty();

    }

    @Test
    @DisplayName("일반 로그 생성 테스트")
    void t2(){
        String message = "test";

        Log log = Log.of(Log.LoggingLevel.INFO, message);

        assertThat(log.getLoggingLevel()).isEqualTo(Log.LoggingLevel.INFO);
        assertThat(log.getMessage()).isEqualTo(message);
        assertThat(log.getDetail()).isNull();
    }
}