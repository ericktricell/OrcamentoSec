/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.orcamento.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Eu
 */
@Entity
@Table(name = "orcamento")
@NamedQueries({
    @NamedQuery(name = "orcamento.pegaid", query = "select count(idOrcamento) FROM Orcamento")
})
public class Orcamento implements Serializable {
    
    @Id
    private String idOrcamento;
    
    @Column(length = 30, nullable = true)
    private String numDoc;
    
    @Column(length = 200, nullable = true)
    private String obs;
    
    @Column(length = 30, nullable = true)
    private String prazoEnt;
    
    @Column(length = 8, precision = 2, nullable = true)
    private double vlrTotal;
    
    @Column(length = 50, nullable = true)
    private String condPag;
    
    @Column()
    @Temporal(TemporalType.DATE)
    private Date data;
    
    @JoinColumn(name = "idCliente", referencedColumnName = "idCliente", insertable = true, updatable = true)
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Cliente cliente;
    
    @JoinColumn(name = "idEmpresa", referencedColumnName = "idEmpresa", insertable = true, updatable = true)
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Empresa empresa;
    
    @JoinColumn(name = "idUsuario", referencedColumnName = "idUsuario", insertable = true, updatable = true)
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Usuario usuario;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idOrcamento", fetch = FetchType.LAZY)
    private List<ItensOrc> itensOrcs;

    public double getVlrTotal() {
        return vlrTotal;
    }

    public void setVlrTotal(double vlrTotal) {
        this.vlrTotal = vlrTotal;
    }

    public String getNumDoc() {
        return numDoc;
    }

    public void setNumDoc(String numDoc) {
        this.numDoc = numDoc;
    }

    public String getIdOrcamento() {
        return idOrcamento;
    }

    public void setIdOrcamento(String idOrcamento) {
        this.idOrcamento = idOrcamento;
    }

    public String getObs() {
        return obs;
    }

    public void setObs(String obs) {
        this.obs = obs;
    }

    public String getPrazoEnt() {
        return prazoEnt;
    }

    public void setPrazoEnt(String prazoEnt) {
        this.prazoEnt = prazoEnt;
    }

    public String getCondPag() {
        return condPag;
    }

    public void setCondPag(String condPag) {
        this.condPag = condPag;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Empresa getEmpresa() {
        return empresa;
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public List<ItensOrc> getItensOrcs() {
        return itensOrcs;
    }

    public void setItensOrcs(List<ItensOrc> itensOrcs) {
        this.itensOrcs = itensOrcs;
    }
    
    
}
