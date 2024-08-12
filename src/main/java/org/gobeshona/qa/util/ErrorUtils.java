package org.gobeshona.qa.util;

import org.springframework.validation.ObjectError;
import org.springframework.validation.FieldError;

import java.util.List;
import java.util.stream.Collectors;

public class ErrorUtils {

    /**
     * Extracts default error messages from a list of errors.
     *
     * @param errors the list of errors
     * @return a list of default error messages
     */
    public static List<String> getDefaultMessages(List<ObjectError> errors) {
        return errors.stream()
                .map(error -> {
                    if (error instanceof FieldError) {
                        FieldError fieldError = (FieldError) error;
                        return fieldError.getDefaultMessage();
                    }
                    return error.getDefaultMessage();
                })
                .collect(Collectors.toList());
    }
}
