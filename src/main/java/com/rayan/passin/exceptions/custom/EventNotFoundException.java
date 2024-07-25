package com.rayan.passin.exceptions.custom;

import com.rayan.passin.exceptions.handler.PassinException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;

public class EventNotFoundException extends PassinException {

          private final String detail;

          public EventNotFoundException(String detail) {
                    this.detail = detail;
          }

          @Override
          public ProblemDetail toProblemDetail() {
                    var problemDetail = ProblemDetail.forStatus(HttpStatus.NOT_FOUND);
                    problemDetail.setTitle("Event not found");
                    problemDetail.setDetail(detail);
                    return problemDetail;
          }

}
