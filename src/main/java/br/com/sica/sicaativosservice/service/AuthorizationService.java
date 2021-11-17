package br.com.sica.sicaativosservice.service;

import br.com.sica.sicaativosservice.dtos.AuthorizationResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(value="authorization", url="http://localhost:8081/auth")
public interface AuthorizationService {

    @RequestMapping(method = RequestMethod.GET,
        value = "/checktoken/{token}")
    AuthorizationResponse checkToken(@PathVariable("token") String token);
}
