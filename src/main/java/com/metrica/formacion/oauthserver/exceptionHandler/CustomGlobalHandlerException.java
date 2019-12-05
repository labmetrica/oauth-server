package com.metrica.formacion.oauthserver.exceptionHandler;

import lombok.extern.log4j.Log4j2;
import org.codehaus.jackson.map.exc.UnrecognizedPropertyException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.security.web.firewall.RequestRejectedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Log4j2
@ControllerAdvice
public class CustomGlobalHandlerException extends ResponseEntityExceptionHandler {

	@ExceptionHandler(CustomErrorResponse.class)
	public ResponseEntity<Object> customError(CustomErrorResponse ex) {

		ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST);
		apiError.setMensaje(ex.getMessage());
		apiError.setExceptionMessage(ex.getExMessage());

		return buildResponseEntity(apiError);
	}

	@ExceptionHandler(com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException.class)
	public ResponseEntity<Object> handleUnrecongnizedParametersJackson (com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException ex){

		ApiError apiError = new ApiError(HttpStatus.INTERNAL_SERVER_ERROR);
		apiError.setMensaje(ex.getMessage());

		return buildResponseEntity(apiError);
	}

	@ExceptionHandler(UnrecognizedPropertyException.class)
	public ResponseEntity<Object> handleUnrecongnizedParameters (){

		return null;
	}

	@ExceptionHandler(HttpClientErrorException.class)
	public ResponseEntity<Object> globalError(HttpClientErrorException ex){

		ApiError apiError = new ApiError(HttpStatus.FORBIDDEN);
		apiError.setMensaje(ex.getMessage());
		apiError.setExceptionMessage(ex.getResponseBodyAsString());

		log.error(ex.getMessage());
		log.error(ex.getResponseBodyAsString());

		return buildResponseEntity(apiError);
	}

	@ExceptionHandler(RequestRejectedException.class)
	public ResponseEntity<Object> handle (){

		return null;
	}

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
		return super.handleMethodArgumentNotValid(ex, headers, status, request);
	}

	@Override
	protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers, HttpStatus status, WebRequest request) {
		return super.handleExceptionInternal(ex, body, headers, status, request);
	}

	@Override
    protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

		ApiError apiError = new ApiError(status);
		apiError.setMensaje("Metodo " + ex.getMethod() + " no sortado, metodos soportados: " + ex.getSupportedHttpMethods());
		apiError.setExceptionMessage(ex.getMessage());

		log.error(ex.getMessage());
		log.error(ex.getMethod());
		log.error(ex.getSupportedHttpMethods());

		return buildResponseEntity(apiError);
    }

    @Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

	    ApiError apiError = new ApiError(status);
	    apiError.setMensaje("JSON parse error, " + request.getContextPath());
	    apiError.setExceptionMessage(ex.getMessage());

	    return buildResponseEntity(apiError);
	}

	@Override
	protected ResponseEntity<Object> handleHttpMessageNotWritable(HttpMessageNotWritableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

		ApiError apiError = new ApiError(status);
		apiError.setMensaje("apiError");

		return buildResponseEntity(apiError);
	}

	private ResponseEntity<Object> buildResponseEntity(ApiError apiError) {
		return new ResponseEntity<>(apiError, apiError.getStatus());
	}
}
