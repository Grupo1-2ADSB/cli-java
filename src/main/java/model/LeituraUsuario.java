/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author BELLA
 */
public class LeituraUsuario {
    
    private Integer idLeitura;
    private Integer fkConfig;
    private Integer fkAlertaComponente;
    private Integer fkMaquina;
    private Integer fkComponente;
    private Integer nSerie;
    private String nomeUsuario;

    public LeituraUsuario(Integer fkConfig, Integer fkAlertaComponente, Integer fkMaquina,
            Integer fkComponente, Integer nSerie, String nomeUsuario) {
        this.fkConfig = fkConfig;
        this.fkAlertaComponente = fkAlertaComponente;
        this.fkMaquina = fkMaquina;
        this.fkComponente = fkComponente;
        this.nSerie = nSerie;
        this.nomeUsuario = nomeUsuario;
    }
    
    public LeituraUsuario() {
    }

    public Integer getIdLeitura() {
        return idLeitura;
    }

    public void setIdLeitura(Integer idLeitura) {
        this.idLeitura = idLeitura;
    }

    public Integer getFkConfig() {
        return fkConfig;
    }

    public void setFkConfig(Integer fkConfig) {
        this.fkConfig = fkConfig;
    }

    public Integer getFkAlertaComponente() {
        return fkAlertaComponente;
    }

    public void setFkAlertaComponente(Integer fkAlertaComponente) {
        this.fkAlertaComponente = fkAlertaComponente;
    }

    public Integer getFkMaquina() {
        return fkMaquina;
    }

    public void setFkMaquina(Integer fkMaquina) {
        this.fkMaquina = fkMaquina;
    }

    public Integer getFkComponente() {
        return fkComponente;
    }

    public void setFkComponente(Integer fkComponente) {
        this.fkComponente = fkComponente;
    }

    public Integer getnSerie() {
        return nSerie;
    }

    public void setnSerie(Integer nSerie) {
        this.nSerie = nSerie;
    }

    public String getNomeUsuario() {
        return nomeUsuario;
    }

    public void setNomeUsuario(String nomeUsuario) {
        this.nomeUsuario = nomeUsuario;
    }

    @Override
    public String toString() {
        return "LeituraUsuario{" + 
                "\nidLeitura: " + idLeitura + 
                "\nfkConfig: " + fkConfig + ", fkAlertaComponente: " + fkAlertaComponente
                + "\n, fkMaquina: " + fkMaquina + ", fkComponente: " + fkComponente + 
                "\n, nSerie: " + nSerie + ", nomeUsuario: " + nomeUsuario + "\n";
    }
}