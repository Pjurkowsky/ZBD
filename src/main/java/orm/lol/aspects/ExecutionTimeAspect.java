package orm.lol.aspects;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

@Aspect
@Component
public class ExecutionTimeAspect {

    private static final Logger logger = LoggerFactory.getLogger(ExecutionTimeAspect.class);
    private static final String LOG_DIR = "execution-time-logs";

    @Around("execution(* orm.lol.repos.*Repository.*(..)) || execution(* orm.lol.jdbc.services.*.*(..))")
    public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.nanoTime();

        Object proceed = joinPoint.proceed();

        long executionTimeNs = System.nanoTime() - start;
        long executionTimeUs = executionTimeNs / 1000;
        String methodSignature = joinPoint.getSignature().toShortString();
        String sanitizedSignature = sanitizeFilename(methodSignature);
        String logEntry = executionTimeUs + "\n";

        appendLogToFile(sanitizedSignature + ".log", logEntry);

        logger.info("{} executed in {} us", methodSignature, executionTimeUs);

        return proceed;
    }

    private void appendLogToFile(String filename, String content) {
        try {
            Path logDirPath = Paths.get(LOG_DIR);
            if (!Files.exists(logDirPath)) {
                Files.createDirectories(logDirPath);
            }

            Path filePath = logDirPath.resolve(filename);
            Files.write(filePath, content.getBytes(), StandardOpenOption.CREATE, StandardOpenOption.APPEND);
        } catch (IOException e) {
            logger.error("Failed to write log entry to file: {}", filename, e);
        }
    }

    private String sanitizeFilename(String methodSignature) {
        return methodSignature
                .replace("(", "_")
                .replace(")", "")
                .replace(".", "_")
                .replace(",", "_")
                .replace(" ", "")
                .replace("*", "")
                .replace("..", "_");
    }
}
