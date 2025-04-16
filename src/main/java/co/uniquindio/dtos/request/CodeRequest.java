package co.uniquindio.dtos.request;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CodeRequest {
    private String id;
    private String destinyEmail;
    private String ownerName;
}
