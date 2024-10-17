package Envio.com.Email.DTO;

import lombok.Data;

import java.util.Map;

@Data
public class EmailRequestDTO {
    private String to;
    private String subject;
    private Map<String, Object> templateModel;
}
