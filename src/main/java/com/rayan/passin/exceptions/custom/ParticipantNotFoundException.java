package com.rayan.passin.exceptions.custom;

import com.rayan.passin.exceptions.handler.PassinException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;

public class ParticipantNotFoundException extends PassinException {

          private final String detail;

          public ParticipantNotFoundException(String detail) {
                    this.detail = detail;
          }

          @Override
          public ProblemDetail toProblemDetail() {
                    var problemDetail = ProblemDetail.forStatus(HttpStatus.NOT_FOUND);
                    problemDetail.setTitle("Participant not found");
                    problemDetail.setDetail(detail);
                    return problemDetail;
          }

}
