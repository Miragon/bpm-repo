package io.miragon.bpmrepo.core.shared.exception;

import io.miragon.bpmrepo.core.artifact.domain.exception.HistoricalDataAccessException;
import io.miragon.bpmrepo.core.artifact.domain.exception.LockedException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class GlobalControllerAdvice {

    @ExceptionHandler({NameConflictException.class})
    public ResponseEntity<String> handleOperationNotAllowed(final NameConflictException exception) {
        log.error("Client error - ", exception);
        return ResponseEntity.status(HttpStatus.CONFLICT).body(exception.getMessage());
    }

    @ExceptionHandler({AccessRightException.class})
    public ResponseEntity<String> handleAccessRightException(final AccessRightException exception) {
        log.error("Client error - ", exception);
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(exception.getMessage());
    }

    @ExceptionHandler({ObjectNotFoundException.class})
    public ResponseEntity<String> handleObjectNotFoundException(final ObjectNotFoundException exception) {
        log.error("Error - ", exception.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
    }

    @ExceptionHandler({LockedException.class})
    public ResponseEntity<String> handleLockedException(final LockedException exception) {
        log.error("Locked - ", exception.getMessage());
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(exception.getMessage());
    }

    @ExceptionHandler({HistoricalDataAccessException.class})
    public ResponseEntity<String> handleHistoricalDataAccessException(final HistoricalDataAccessException exception) {
        log.error("Historical Data access - ", exception.getMessage());
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(exception.getMessage());
    }


}