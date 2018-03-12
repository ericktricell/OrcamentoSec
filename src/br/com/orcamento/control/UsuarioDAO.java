/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.orcamento.control;

import br.com.orcamento.model.Usuario;
import br.com.orcamento.util.HibernateUtil;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author Eu
 */
public class UsuarioDAO extends HibernateUtil{
    
    private Session s;
    private Transaction t;

    public UsuarioDAO() {
        this.s = getSessionfactory().openSession();
    }
    
    public boolean salvar(Usuario u){
        t = s.beginTransaction();
        try{
            s.save(u);
            t.commit();
            return true;
        }catch(Exception e){
            t.rollback();
            return false;
        }finally{
            s.close();
        }
    }
   
        
    public boolean atualizar(Usuario u){
        
        Transaction t = s.beginTransaction();
        try{
            s.update(u);
            t.commit();
            return true;
        }catch(Exception e){
            t.rollback();
            return false;
        }finally{
            s.close();
        }
    }
    
    public List<Usuario> listUser(){
        
        try{
            return s.createCriteria(Usuario.class).list();
        }catch(Exception e){
            return null;
        }finally{
            s.close();
        }
    }
    
    public List<Usuario> listUser(String t){
        
        try{
            return s.createCriteria(Usuario.class).add(Restrictions.like("nome", t + "%")).list();
        }catch(Exception e){
            return null;
        }finally{
            s.close();
        }
    }
}
