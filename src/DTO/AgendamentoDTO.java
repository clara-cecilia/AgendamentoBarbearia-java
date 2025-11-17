package DTO;

import java.sql.Timestamp;

public class AgendamentoDTO {

    private int id;
    private String nome_cliente;
    private String servico;
    private Timestamp data_hora;
    private String status;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome_cliente() {
        return nome_cliente;
    }

    public void setNome_cliente(String nome_cliente) {
        this.nome_cliente = nome_cliente;
    }

    public String getServico() {
        return servico;
    }

    public void setServico(String servico) {
        this.servico = servico;
    }

    public Timestamp getData_hora() {
        return data_hora;
    }

    public void setData_hora(Timestamp data_hora) {
        this.data_hora = data_hora;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}