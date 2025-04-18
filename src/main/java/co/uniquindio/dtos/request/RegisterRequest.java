package co.uniquindio.dtos.request;

import lombok.*;

public record RegisterRequest (
        String fullName,
        String city,
        String phoneNumber,
        String address,
        String email,
        String password
){

}
