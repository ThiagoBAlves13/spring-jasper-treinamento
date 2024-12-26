package com.treinamentoJasper.spring_jasper_treinamento.controller.error;

import java.util.Map;

import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.error.ErrorAttributeOptions.Include;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

@RestController
public class SpringErrorController implements ErrorController {

  private final ErrorAttributes errorAttributes;

  /**
   * 
   * Construtor que recebe {@link ErrorAttributes} para tratamento.
   * 
   * @param errorAttributes a fonte do erro.
   */
  
  public SpringErrorController(ErrorAttributes errorAttributes) {
    Assert.notNull(errorAttributes, "ErrorAttributes must not be null");
    this.errorAttributes = errorAttributes;
  }

//  @Override
//  public String getErrorPath() {
//    return "/error";
//  }

  /**
   * 
   * Método para mapear {@link WebRequest}
   * 
   * @param webRequest fonte de solicitação
   * @return um mapa de erros de atributos
   */
  
  @CrossOrigin
  @RequestMapping("/error")
  public Map<String, Object> error(WebRequest webRequest){
     Map<String, Object> body = getErrorAttributes(webRequest);
     return body;
  }

  private Map<String, Object> getErrorAttributes(WebRequest webRequest) {
     ErrorAttributeOptions options = ErrorAttributeOptions.defaults();
     options = options.including(Include.EXCEPTION);
     options = options.including(Include.MESSAGE);
     options = options.including(Include.BINDING_ERRORS);
     
     return errorAttributes.getErrorAttributes(webRequest, options);
  }
}