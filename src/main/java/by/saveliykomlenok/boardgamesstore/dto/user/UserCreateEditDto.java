package by.saveliykomlenok.boardgamesstore.dto.user;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.Date;

@Data
public class UserCreateEditDto {
    private String username;
    private String firstname;
    private String lastname;
    private String password;
}
