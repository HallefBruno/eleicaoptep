
package com.eleicao.ptep.entidade.dto;

/**
 *
 * @author hallef
 */

public class FiltroCargo {
    
    private String nomeDoCargo;

    public String getNomeDoCargo() {
        return this.nomeDoCargo == null ? "": this.nomeDoCargo;
    }

    public void setNomeDoCargo(String nomeDoCargo) {
        this.nomeDoCargo = nomeDoCargo;
    }
    
    
}
