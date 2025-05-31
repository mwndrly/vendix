package models;

import java.io.Serializable;

public class Client implements Serializable {
    private String name;
    private String email;
    private String cpf;

    public Client(String name, String email, String cpf) {
        this.name = name;
        this.email = email;
        this.cpf = cpf;
    }

    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getCpf() { return cpf; }

    public void setName(String name) { this.name = name; }
    public void setEmail(String email) { this.email = email; }
    public void setCpf(String cpf) { this.cpf = cpf; }

    @Override
    public String toString() {
        return "Nome: " + name + ", Email: " + email + ", CPF: " + cpf;
    }
}
