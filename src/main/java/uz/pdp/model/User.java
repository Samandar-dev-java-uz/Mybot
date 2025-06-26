package uz.pdp.model;

import java.util.HashMap;
import java.util.Map;

public interface User {
    String START = "START";
    String ENTER_FIRSTNAME = "ENTER_FIRSTNAME";
    String ENTER_LASTNAME = "ENTER_LASTNAME";
    String ENTER_PHONE_NUMBER = "ENTER_PHONE_NUMBER";
    String ENTER_PASSWORD ="ENTER_PASSWORD";
    String ENTER_AGE ="ENTER_AGE";
    String REGISTERED = "REGISTERED";
    Map<Long,UserInformation> listUser = new HashMap<>();

}
