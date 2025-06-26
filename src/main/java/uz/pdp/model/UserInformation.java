package uz.pdp.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor

public class UserInformation {
    private  String name;
    private  String lastname;
    private  Long chadId;
    private  String password;
    private  String phoneNumber;
    private  Integer age;
    private  String state;
}
