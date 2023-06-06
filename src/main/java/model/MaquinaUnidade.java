/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Leo
 */
public class MaquinaUnidade {

    private Integer idMaquina;
    private Integer nSerie;
    private Integer idConfig;
    private Integer idComponente;
    private String capacidadeComponente;
    private Integer idTipoComponente;
    private String tipoComponente;
    private Integer idUnidadeComponente;
    private Integer idUnidade;
    private String sigla;

    public MaquinaUnidade(Integer idMaquina, Integer nSerie, Integer idConfig, Integer idComponente, String capacidadeComponente, Integer idTipoComponente, String tipoComponente, Integer idUnidadeComponente, Integer idUnidade, String sigla) {
        this.idMaquina = idMaquina;
        this.nSerie = nSerie;
        this.idConfig = idConfig;
        this.idComponente = idComponente;
        this.capacidadeComponente = capacidadeComponente;
        this.idTipoComponente = idTipoComponente;
        this.tipoComponente = tipoComponente;
        this.idUnidadeComponente = idUnidadeComponente;
        this.idUnidade = idUnidade;
        this.sigla = sigla;
    }

    public MaquinaUnidade() {
    }
    
    public Integer getnSerie() {
        return nSerie;
    }

    public void setnSerie(Integer nSerie) {
        this.nSerie = nSerie;
    }

    public Integer getIdConfig() {
        return idConfig;
    }

    public void setIdConfig(Integer idConfig) {
        this.idConfig = idConfig;
    }

    public Integer getIdComponente() {
        return idComponente;
    }

    public void setIdComponente(Integer idComponente) {
        this.idComponente = idComponente;
    }

    public String getCapacidadeComponente() {
        return capacidadeComponente;
    }

    public void setCapacidadeComponente(String capacidadeComponente) {
        this.capacidadeComponente = capacidadeComponente;
    }

    public Integer getIdTipoComponente() {
        return idTipoComponente;
    }

    public void setIdTipoComponente(Integer idTipoComponente) {
        this.idTipoComponente = idTipoComponente;
    }

    public String getTipoComponente() {
        return tipoComponente;
    }

    public void setTipoComponente(String tipoComponente) {
        this.tipoComponente = tipoComponente;
    }

    public Integer getIdUnidadeComponente() {
        return idUnidadeComponente;
    }

    public void setIdUnidadeComponente(Integer idUnidadeComponente) {
        this.idUnidadeComponente = idUnidadeComponente;
    }

    public Integer getIdUnidade() {
        return idUnidade;
    }

    public void setIdUnidade(Integer idUnidade) {
        this.idUnidade = idUnidade;
    }

    public String getSigla() {
        return sigla;
    }

    public void setSigla(String sigla) {
        this.sigla = sigla;
    }

    public Integer getIdMaquina() {
        return idMaquina;
    }

    public void setIdMaquina(Integer idMaquina) {
        this.idMaquina = idMaquina;
    }

    @Override
    public String toString() {
        return "\nMaquinaUnidade{" + "idMaquina=" + idMaquina + ", nSerie=" + nSerie + ", idConfig=" + idConfig + ", idComponente=" + idComponente + ", capacidadeComponente=" + capacidadeComponente + ", idTipoComponente=" + idTipoComponente + ", tipoComponente=" + tipoComponente + ", idUnidadeComponente=" + idUnidadeComponente + ", idUnidade=" + idUnidade + ", sigla=" + sigla + '}';
    }

}
