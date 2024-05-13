package com.ucb.parcial.dto;

public class ResponseCard {
  private int statusCode;
  private String message;
  public ResponseCard(int statusCode, String message){
    this.statusCode = statusCode;
    this.message = message;
  }
}
