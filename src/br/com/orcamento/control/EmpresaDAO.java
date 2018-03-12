/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.orcamento.control;

import br.com.orcamento.model.Empresa;
import br.com.orcamento.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author Eu
 */
public class EmpresaDAO extends HibernateUtil{
    private Session s;
    private Transaction t;

    public EmpresaDAO() {
        this.s = getSessionfactory().openSession();
    }
    
    public boolean salvar(Empresa empresa){
        
        t = s.beginTransaction();
        try{
            s.save(empresa);
            t.commit();
            return true;
        }catch(Exception e){
            t.rollback();
            return false;
        }finally{
            s.close();
        }
    }
    
    public boolean atualizar(Empresa empresa){
         t = s.beginTransaction();
        try{
            s.update(empresa);
            t.commit();
            return true;
        }catch(Exception e){
            t.rollback();
            return false;
        }finally{
            s.close();
        }
    }
    
    public Empresa getEmpresa(){
        
        try{
            return (Empresa) s.createCriteria(Empresa.class).uniqueResult();
        }catch(Exception e){
            return null;
        }finally{
            s.close();
        }
    }
}
