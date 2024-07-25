package com.rayan.passin.exceptions.custom;

import com.rayan.passin.exceptions.handler.PassinException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;

public class EventAlreadyExistsException extends PassinException {

          private final String detail;

          public EventAlreadyExistsException(String detail) {
                    this.detail = detail;
          }

          @Override
          public ProblemDetail toProblemDetail() {
                    var problemDetail = ProblemDetail.forStatus(HttpStatus.CONFLICT);
                    problemDetail.setTitle("Event already exists");
                    problemDetail.setDetail(detail);
                    return problemDetail;
          }

}
