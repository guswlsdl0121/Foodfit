package Foodfit.BackEnd.Service;

import Foodfit.BackEnd.Domain.Log;
import Foodfit.BackEnd.Repository.LogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class DatabaseLogger {

    private final LogRepository logRepository;

    public void saveLog(Exception e){
        Log log = Log.of(Log.LoggingLevel.ERROR, e);

        logRepository.save(log);

    }


    public void saveLog(Log.LoggingLevel loggingLevel, String message){
        Log log = Log.of(loggingLevel, message);

        logRepository.save(log);

    }


}
