package Foodfit.BackEnd.Domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;

import java.util.Arrays;

@Entity
@Getter
public class Log {

    public static Log of(LoggingLevel loggingLevel, Exception e){
        return Log.builder()
                .loggingLevel(loggingLevel)
                .message(e.getMessage())
                .detail(Arrays.toString(e.getStackTrace()))
                .build();
    }

    public static Log of(LoggingLevel loggingLevel, String message){
        return Log.builder()
                .loggingLevel(loggingLevel)
                .message(message)
                .build();

    }

    @Builder
    private Log(LoggingLevel loggingLevel, String message, String detail) {
        this.loggingLevel = loggingLevel;
        this.message = message;
        this.detail = detail;
    }

    protected Log() {
    }

    @Id @GeneratedValue
    private Long id;

    @Enumerated(value = EnumType.STRING)
    private LoggingLevel loggingLevel;

    private String message;

    @Lob
    private String detail;


    public enum LoggingLevel{
        TRACE, DEBUG, INFO, WARN, ERROR
    }
}
