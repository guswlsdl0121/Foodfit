package Foodfit.BackEnd.Domain;

import jakarta.persistence.*;
import lombok.Builder;

import java.util.Arrays;

@Entity
public class Log {

    public static Log of(LoggingLevel loggingLevel, Exception e){
        return Log.builder()
                .loggingLevel(loggingLevel)
                .message(e.getMessage())
                .detail(Arrays.toString(e.getStackTrace()))
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
