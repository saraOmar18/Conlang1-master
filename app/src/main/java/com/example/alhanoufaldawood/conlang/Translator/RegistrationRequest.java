package com.example.alhanoufaldawood.conlang.Translator;

public class RegistrationRequest {

   public String name;
   public String email;
   public String password;
   public String type;
   public String id;

    public RegistrationRequest(String name, String email, String password, String type, String id) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.type = type;
        this.id = id;
    }
}
