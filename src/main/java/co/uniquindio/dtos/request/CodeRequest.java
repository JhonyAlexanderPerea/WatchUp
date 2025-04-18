package co.uniquindio.dtos.request;

import lombok.*;

public record CodeRequest (
        String id,
        String destinyEmail,
        String ownerName
){

}
