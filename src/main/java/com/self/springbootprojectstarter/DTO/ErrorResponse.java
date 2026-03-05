package com.self.springbootprojectstarter.DTO;

import java.time.LocalDateTime;


// a dto is data transfer object a normal class needed for reformatting date as a json file
// to parse it later
public class ErrorResponse {
    private LocalDateTime timestamp;
    private int status;
    private String message;

    public ErrorResponse(int status, String message){
        this.timestamp = LocalDateTime.now();
        this.status = status;
        this.message = message;
    }

    //getters
    public int getStatus() {
        return status;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public String getMessage() {
        return message;
    }
}
