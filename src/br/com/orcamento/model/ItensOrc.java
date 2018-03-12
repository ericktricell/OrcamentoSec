/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.orcamento.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author Eu
 */
@Entity
@Table(name = "ItensOrc")
@NamedQuery(name = "itensOrc.itens", query = "SELECT io FROM ItensOrc io WHERE io.idOrcamento = :idOrcamento")
public class ItensOrc implements Serializable {
    
    @Id
    private String idOrcamento;
    
    @Id
    private Long idItem;
    
    @Column(length = 50, nullable = false)
    private Double num;
    
    @JoinColumn(name = "idItem", referencedColumnName = "idItem", insertable = false, updatable = false)
    @ManyToOne
    private Item item;
    
    public Double getNum() {
        return num;
    }

    public void setNum(Double num) {
        this.num = num;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public String getIdOrcamento() {
        return idOrcamento;
    }

    public void setIdOrcamento(String idOrcamento) {
        this.idOrcamento = idOrcamento;
    }

    public Long getIdItem() {
        return idItem;
    }

    public void setIdItem(Long idItem) {
        this.idItem = idItem;
    }
    
}
