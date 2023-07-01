package Foodfit.BackEnd.Service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import Foodfit.BackEnd.Domain.Log;
import Foodfit.BackEnd.Repository.LogRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;



@ExtendWith(MockitoExtension.class)
public class DatabaseLoggerTest {

    @Mock
    private LogRepository logRepository;
    @InjectMocks
    private DatabaseLogger logger;


    @Test
    @DisplayName("initilize objects")
    void t1(){
        Object actual = ReflectionTestUtils.getField(logger, "logRepository");

        assertThat(logRepository).isNotNull();
        assertThat(logger).isNotNull();
        assertThat(actual).isEqualTo(logRepository);
    }

    @Test
    @DisplayName("Error Log save test")
    void t2(){
        //given
        Exception e = new RuntimeException("Test exception");

        //when
        logger.saveLog(e);

        //then
        ArgumentCaptor<Log> captor = ArgumentCaptor.forClass(Log.class);
        verify(logRepository).save(captor.capture());

        Log savedLog = captor.getValue();
        assertThat(savedLog.getLoggingLevel()).isEqualTo(Log.LoggingLevel.ERROR);
        assertThat(savedLog.getMessage()).isEqualTo(e.getMessage());
    }

    @Test
    @DisplayName("normal Log Save Test")
    void t3(){
        Log.LoggingLevel loggingLevel = Log.LoggingLevel.INFO;
        String message = "INFO message";
        //when
        logger.saveLog(loggingLevel, message);

        //then
        ArgumentCaptor<Log> captor = ArgumentCaptor.forClass(Log.class);
        verify(logRepository).save(captor.capture());
        Log savedLog = captor.getValue();
        assertThat(savedLog.getLoggingLevel()).isEqualTo(Log.LoggingLevel.INFO);
        assertThat(savedLog.getMessage()).isEqualTo(message);

    }

}
