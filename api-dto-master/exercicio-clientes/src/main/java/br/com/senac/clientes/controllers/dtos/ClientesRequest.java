package br.com.senac.clientes.controllers.dtos;

import java.time.LocalDate;
import java.util.List;

public class ClientesRequest {
    private String documento;
    private String nome;
    private String sobrenome;
    private String email;
    private String sexo;
    private LocalDate dataNascimento;
    private int idade;
    private int ddd;
    private int telefone;
    private List<EnderecosRequest> enderecos; // isso vai direto para requisicao do postman

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSobrenome() {
        return sobrenome;
    }

    public void setSobrenome(String sobrenome) {
        this.sobrenome = sobrenome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getIdade() {
        return idade;
    }

    public void setIdade(int idade) {
        this.idade = idade;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public int getDdd() {
        return ddd;
    }

    public void setDdd(int ddd) {
        this.ddd = ddd;
    }

    public int getTelefone() {
        return telefone;
    }

    public void setTelefone(int telefone) {
        this.telefone = telefone;
    }

    public List<EnderecosRequest> getEnderecos() {
        return enderecos;
    }

    public void setEnderecos(List<EnderecosRequest> enderecos) {
        this.enderecos = enderecos;
    }
}
