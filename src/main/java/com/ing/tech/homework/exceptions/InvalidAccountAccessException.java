package com.ing.tech.homework.exceptions;

import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.FORBIDDEN)
@NoArgsConstructor
public class InvalidAccountAccessException extends RuntimeException {
}
