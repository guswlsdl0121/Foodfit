package Foodfit.BackEnd;

import Foodfit.BackEnd.Domain.Log;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.assertj.core.api.Assertions.*;

class LogTest {


    @Test
    @DisplayName("로그 생성 테스트")
    void t1(){
        try{
            new File("test");
        }catch (Exception e){
            Log of = Log.of(Log.LoggingLevel.ERROR, e);
            System.out.println();
        }

    }

}