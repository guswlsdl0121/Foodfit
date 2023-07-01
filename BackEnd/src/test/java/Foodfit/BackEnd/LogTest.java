package Foodfit.BackEnd;

import Foodfit.BackEnd.Domain.Log;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.File;

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
}