package pl.kurs.java.exception;


import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@RequiredArgsConstructor
@ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
public class StrokeSizeIsToBigException extends RuntimeException {

    @Override
    public String getMessage() {
        return "Max stroke size is 2px.";
    }
}
