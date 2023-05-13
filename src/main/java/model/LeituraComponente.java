/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author BELLA
 */
public class LeituraComponente {

    private Integer componenteId;
    private String nomeComponente;
    private Double capacidadeComponente;
    private Integer fkTipoComponente;

    public LeituraComponente(Integer componenteId, String nomeComponente,
            Double capacidadeComponente, Integer fkTipoComponente) {
        this.componenteId = componenteId;
        this.nomeComponente = nomeComponente;
        this.capacidadeComponente = capacidadeComponente;
        this.fkTipoComponente = fkTipoComponente;
    }

    public LeituraComponente() {
    }

    public Integer getComponenteId() {
        return componenteId;
    }

    public void setComponenteId(Integer componenteId) {
        this.componenteId = componenteId;
    }

    public String getNomeComponente() {
        return nomeComponente;
    }

    public void setNomeComponente(String nomeComponente) {
        this.nomeComponente = nomeComponente;
    }

    public Double getCapacidadeComponente() {
        return capacidadeComponente;
    }

    public void setCapacidadeComponente(Double capacidadeComponente) {
        this.capacidadeComponente = capacidadeComponente;
    }

    public Integer getfkTipoComponente() {
        return fkTipoComponente;
    }

    public void setfkTipoComponente(Integer fkTipoComponente) {
        this.fkTipoComponente = fkTipoComponente;
    }

    @Override
    public String toString() {
        return String.format(
                """
         ComponenteModel
         componenteId: %d | nomeComponente: %s
         capacidadeComponente: %.1f | fkTipoComponente: %d
         """
                ,componenteId, nomeComponente,
                capacidadeComponente, fkTipoComponente);
    }

}
