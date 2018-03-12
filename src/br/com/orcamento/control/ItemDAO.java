/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.orcamento.control;

import br.com.orcamento.model.Item;
import br.com.orcamento.util.HibernateUtil;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author Eu
 */
public class ItemDAO extends HibernateUtil{
    
    private Session s;
    private Transaction t;
    
    public ItemDAO() {
        s = getSessionfactory().openSession();
    }
    
    public boolean salvar(Item i){
        t = s.beginTransaction();
        try{
            s.save(i);
            t.commit();
            return true;
        }catch(Exception e){
            t.rollback();
            return false;
        }finally{
            s.close();
        }
    }
    
    public boolean atualizar(Item i){
        t = s.beginTransaction();
        try{
            s.update(i);
            t.commit();
            return true;
        }catch(Exception e){
            t.rollback();
            return false;
        }finally{
            s.close();
        }
    }
    
    public List<Item> listaItens(){
        
        try{
            return s.createCriteria(Item.class).list();
        }catch(Exception e){
            return null;
        }finally{
            s.close();
        }
    }
    
    public List<Item> listaItens(String t){
        
        try{
            return s.createCriteria(Item.class).add(Restrictions.like("discriminacao", t + "%")).list();
        }catch(Exception e){
            return null;
        }finally{
            s.close();
        }
    }
}
