package com.ucb.parcial;

import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.networknt.schema.JsonSchemaFactory;
import com.networknt.schema.SpecVersion.VersionFlag;
import com.networknt.schema.ValidationMessage;
import com.ucb.parcial.dto.*;

import io.sentry.Sentry;
import io.swagger.v3.oas.annotations.parameters.RequestBody;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.lang.Exception;
import java.util.Set;

import com.networknt.schema.JsonSchema;
import com.networknt.schema.JsonSchemaFactory;
import com.networknt.schema.SpecVersion.VersionFlag;
import com.networknt.schema.ValidationMessage;

@RestController
public class AppController implements IAppApi {
    @GetMapping("/")
    public String index(){
        try {
            throw new Exception("This is a test.");
        } catch (Exception e) {
            Sentry.captureException(e);
        }
        return "Hola from asd";
    }
    @PostMapping( value = "/login", produces = "application/json")
    public ResponseEntity requestLogin(@RequestBody LoginDto login){
        ObjectMapper mapper = new ObjectMapper();
    String json;
    
    try {
      json = mapper.writeValueAsString(login);
      JsonSchemaFactory factory = JsonSchemaFactory.getInstance(VersionFlag.V7);
      JsonSchema jsonsSchema = factory.getSchema(AppController.class.getClassLoader().getResourceAsStream("schemas/login.json"));
      JsonNode jsonNode = mapper.readTree(json);
      Set<ValidationMessage> errors = jsonsSchema.validate(jsonNode);

      String errorsCombined = "";
      for( ValidationMessage error: errors) {
        errorsCombined += error.toString() + "\n";
      }

      if(errors.size() > 0) {
        ErrorResponse errorResponse = new ErrorResponse(errorsCombined.toString());
        return ResponseEntity.badRequest().body(errorResponse);
      }

      ResponseLogin responseLogin = new ResponseLogin("tokensito", 0);
      return ResponseEntity.ok(responseLogin);
    } catch (Exception e) {
      e.printStackTrace();
      ResponseLogin responseLogin = new ResponseLogin("tokensito", 0);
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseLogin);
    }
    }
    @PostMapping( value = "/payment", produces = "application/json")
    public ResponseEntity requestPayment(@RequestBody CardDto card){
        ObjectMapper mapper = new ObjectMapper();
    
    try {
      String json = mapper.writeValueAsString(card);
      JsonSchemaFactory factory = JsonSchemaFactory.getInstance(VersionFlag.V7);
      JsonSchema jsonsSchema = factory.getSchema(AppController.class.getClassLoader().getResourceAsStream("schemas/payment.json"));
      JsonNode jsonNode = mapper.readTree(json);
      Set<ValidationMessage> errors = jsonsSchema.validate(jsonNode);

      String errorsCombined = "";
      for( ValidationMessage error: errors) {
        errorsCombined += error.toString() + "\n";
      }

      if(errors.size() > 0) {
        ErrorResponse errorResponse = new ErrorResponse(errorsCombined.toString());
        return ResponseEntity.badRequest().body(errorResponse);
      }

      ResponseCard responseCard = new ResponseCard(0, "successful");
      return ResponseEntity.ok(responseCard);
    } catch (Exception e) {
      e.printStackTrace();
      ResponseCard responseCard = new ResponseCard(1, "error");
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseCard);
    }
  }
}
