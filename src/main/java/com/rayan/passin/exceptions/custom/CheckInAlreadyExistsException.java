package com.rayan.passin.exceptions.custom;

import com.rayan.passin.exceptions.handler.PassinException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;

public class CheckInAlreadyExistsException extends PassinException {

          private final String detail;

          public CheckInAlreadyExistsException(String detail) {
                    this.detail = detail;
          }

          @Override
          public ProblemDetail toProblemDetail() {
                    var problemDetail = ProblemDetail.forStatus(HttpStatus.CONFLICT);
                    problemDetail.setTitle("Check-in already exists");
                    problemDetail.setDetail(detail);
                    return problemDetail;
          }

}
