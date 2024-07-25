package com.rayan.passin.exceptions.custom;

import com.rayan.passin.exceptions.handler.PassinException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;

public class EventFullException extends PassinException {

          private final String detail;

          public EventFullException(String detail) {
                    this.detail = detail;
          }

          @Override
          public ProblemDetail toProblemDetail() {
                    var problemDetail = ProblemDetail.forStatus(HttpStatus.UNPROCESSABLE_ENTITY);
                    problemDetail.setTitle("Event full");
                    problemDetail.setDetail(detail);
                    return problemDetail;
          }

}
