package com.ucb.parcial;

import org.springframework.http.ResponseEntity;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import com.ucb.parcial.dto.*;

@Tag(name = "Pay application", description = "API endpoints for payment processing")
public interface IAppApi {
    @Operation(summary = "Index endpoint", description = "Lorem ipsum dolor sit amet.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Request successful"),
        @ApiResponse(responseCode = "400", description = "Bad request", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))
        }),
        @ApiResponse(responseCode = "500", description = "Internal server error", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))
        })
    })
    public String index();
    @Operation(summary = "Login endpoint", description = "Authenticate user with provided credentials")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Login successful", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseLogin.class))
        }),
        @ApiResponse(responseCode = "401", description = "Unauthorized", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))
        }),
        @ApiResponse(responseCode = "500", description = "Internal server error", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))
        })
    })
    public ResponseEntity<ResponseLogin> requestLogin(@RequestBody LoginDto login);
    @Operation(summary = "Payment endpoint", description = "Process payment with provided card details")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Payment successful", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseCard.class))
        }),
        @ApiResponse(responseCode = "400", description = "Bad request", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))
        }),
        @ApiResponse(responseCode = "500", description = "Internal server error", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))
        })
    })
    public ResponseEntity<ResponseCard> requestPayment(@RequestBody CardDto card);
}
