package Foodfit.BackEnd;

import Foodfit.BackEnd.Domain.Log;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.assertj.core.api.Assertions.*;

class LogTest {


    @Test
    @DisplayName("로그 생성 테스트 : cause가 있는 Exception")
    void t1(){
        String exceptionMessage = "테스트용 Exception입니다.";

        try{
            throwException(exceptionMessage);
        }catch (Exception e){
            Log log = Log.of(Log.LoggingLevel.ERROR, e);

            assertThat(log.getLoggingLevel()).isEqualTo(Log.LoggingLevel.ERROR);
            assertThat(log.getMessage()).isEqualTo(exceptionMessage);
            assertThat(log.getDetail()).isNotEmpty();


            return;
        }
        fail("예외가 발생해야 합니다.");
    }



    private void throwException(String exceptionMessage){
        try{
            new File((String) null);
        }catch (Exception e){
           throw new RuntimeException(exceptionMessage, e);
        }

    }
}