package aspects;


import org.apache.logging.log4j.*;
import org.aspectj.lang.*;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.*;

import java.time.*;
import java.util.*;

@Deprecated
@Aspect
@Component
public class LoggingAspect {

    private final Logger logger = LogManager.getLogger();

    //pointcut is a predicate that points to a particular package so you can advice particular things
    @Before("within(java..*)")
    public void logMethodStart(JoinPoint jp){
        String methodSig = jp.getTarget().getClass().toString() + "." + jp.getSignature().getName();
        String argStr = Arrays.toString(jp.getArgs());
        //log4j wants you to use {} as placeholders for Strings and stuff to log
        logger.info("{} invoked at {}", methodSig, LocalDateTime.now());
        logger.info("Input arguments: {}", argStr);
    }
}
