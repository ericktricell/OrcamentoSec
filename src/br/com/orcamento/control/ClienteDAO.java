/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.orcamento.control;

import br.com.orcamento.model.Cliente;
import br.com.orcamento.util.HibernateUtil;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author Eu
 */
public class ClienteDAO extends HibernateUtil{
    private Session s;
    private Transaction t;

    public ClienteDAO() {
        this.s = getSessionfactory().openSession();
    }
    
    public boolean salvar(Cliente c){
        
        t = s.beginTransaction();
        try{
            s.save(c);
            t.commit();
            return true;
        } catch(Exception e){
            e.printStackTrace();
            t.rollback();
            return false;
        }finally{
            s.close();
        }
    }
    
    public boolean atualizar(Cliente c){
        
        t = s.beginTransaction();
        try{
            s.update(c);
            t.commit();
            return true;
        } catch(Exception e){
            e.printStackTrace();
            t.rollback();
            return false;
        }finally{
            s.close();
        }
    }
    
    public boolean excluir(Cliente c){
        t = s.beginTransaction();
        try{
            s.delete(c);
            t.commit();
            return true;
        } catch(Exception e){
            e.printStackTrace();
            t.rollback();
            return false;
        }finally{
            s.close();
        }
    }
    
    public List<Cliente> lista(){
        
        try{
            return s.createCriteria(Cliente.class).list();
        } catch(Exception e){
            return null;
        }finally{
            s.close();
        }
    }
    
    public List<Cliente> lista(String txt){
        
        try{
            return s.createCriteria(Cliente.class).add(Restrictions.like("nome", txt + "%")).list();
        } catch(Exception e){
            return null;
        }finally{
            s.close();
        }
    }
    
    public Cliente getCliente(String txt){
        
        try{
            return (Cliente) s.createCriteria(Cliente.class).add(Restrictions.eq("nome", txt)).uniqueResult();
        } catch(Exception e){
            return null;
        }finally{
            s.close();
        }
    }
}
