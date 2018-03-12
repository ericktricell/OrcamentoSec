/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.orcamento.control;

import br.com.orcamento.model.ItensOrc;
import br.com.orcamento.model.Orcamento;
import br.com.orcamento.util.HibernateUtil;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;

/**
 *
 * @author Eu
 */
public class OrcamentoDAO extends HibernateUtil{
    private Session s;
    private Transaction t;

    public OrcamentoDAO() {
        s = getSessionfactory().openSession();
    }
    
    public Long getIdOrcamento(){
        try{
            Query q =s.getNamedQuery("orcamento.pegaid"); 
            String st = String.valueOf(q.uniqueResult().hashCode());
            Long id = Long.parseLong(st);
            return id + 1;
        }catch(Exception e){
            return new Long("1");
        }
    }
    
    public boolean salvar(Orcamento o){
        t = s.beginTransaction();
        try{
            s.save(o);
            t.commit();
            return true;
        }catch(Exception e){
            e.printStackTrace();
            t.rollback();
            return  false;
        }finally{
            s.close();
        }
    }
    
    public List<Orcamento> listOrc(){
        try{
            return s.createCriteria(Orcamento.class).addOrder(Order.desc("idOrcamento")).list();
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }finally{
            s.close();
        }
    }
    
    public List<ItensOrc> listItens(String idOrcamento){
        List<ItensOrc> list = new ArrayList<>();
        try{
            Query q = s.getNamedQuery("itensOrc.itens");
            q.setParameter("idOrcamento", idOrcamento);
            list = q.list();
        }catch(Exception e){
            e.printStackTrace();
            list = null;
        }finally{
            s.close();
        }
        return list;
    }
}
