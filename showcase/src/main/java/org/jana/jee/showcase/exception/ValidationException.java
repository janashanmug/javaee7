/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jana.jee.showcase.exception;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import org.jana.jee.showcase.entity.Message;

/**
 *
 * @author gita
 */
@Provider
public class ValidationException implements ExceptionMapper<ConstraintViolationException>{

    /**
     * 
     * @param exception
     * @return 
     */
    @Override
    public Response toResponse(ConstraintViolationException exception) {
        List<Message> collect = getConstraintViolation(exception);
        GenericEntity<List<Message>> payload = new GenericEntity<List<Message>>(collect){};
        return Response.status(Response.Status.NOT_ACCEPTABLE)
                   .entity(payload)
                   .build();
    }
    
    /**
     * 
     * @param e
     * @return 
     */
    private List<Message> getConstraintViolation(ConstraintViolationException exception) {
        Set<ConstraintViolation<?>> violations = exception.getConstraintViolations();
        List<Message> collect = violations.stream()
                .map(viola-> {
                    String propertyPath = viola.getPropertyPath().toString();
                    return new Message(Message.Code.CONSTRAINT_VIOLATION,
                            propertyPath.substring(propertyPath.lastIndexOf(".")+1)+": "+viola.getMessage());
                })
                .collect(Collectors.toList());
        return collect;
    }

}
