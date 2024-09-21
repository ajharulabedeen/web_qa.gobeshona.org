package org.gobeshona.qa.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PasswordChangeResponse {
    private boolean success;
    private String message;
}
