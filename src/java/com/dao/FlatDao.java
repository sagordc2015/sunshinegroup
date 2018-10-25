/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dao;

import com.hibernate.Admin;
import com.hibernate.FlatAddress;
import com.hibernate.FlatDescription;
import com.hibernate.FlatImage;
import com.hibernate.FlatInformation;
import com.hibernate.NewHibernateUtil;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

public class FlatDao {

    public List<FlatInformation> findAllFlat() {
        List<FlatInformation> flats = new ArrayList<FlatInformation>();
        Session s = NewHibernateUtil.getSessionFactory().openSession();
        s.beginTransaction();
        Query q = s.createQuery("From FlatInformation");
        flats = q.list();
        s.getTransaction().commit();
        s.close();
        return flats;
    }

    public FlatInformation findFlatInfoById(int id) {
        FlatInformation fi = new FlatInformation();
        Session s = NewHibernateUtil.getSessionFactory().openSession();
        s.beginTransaction();
        Query q = s.createQuery("From FlatInformation where flatId=" + id);
        List<FlatInformation> lst = q.list();
        if (lst.size() > 0) {
            fi = lst.get(0);
        }
        s.getTransaction().commit();
        s.close();
        return fi;
    }

    /// find flat_description_id from FlatDescription Table
    public List<Integer> findAllFlatID() {
        List<Integer> lst = new ArrayList<Integer>();
        Session s = NewHibernateUtil.getSessionFactory().openSession();
        s.beginTransaction();
        Query q = s.createQuery("select flatId from FlatInformation order by flatId");
        lst = q.list();
        s.getTransaction().commit();
        s.close();
        return lst;
    }

    /// find address_id from FlatAddress Table
    public List<FlatInformation> findAllFlatAddress() {
        List<FlatInformation> lst = new ArrayList<FlatInformation>();
        Session s = NewHibernateUtil.getSessionFactory().openSession();
        s.beginTransaction();
        Query q = s.createQuery("select addressId from FlatAddress");
        lst = q.list();
        s.getTransaction().commit();
        s.close();
        return lst;

    }

    ///  insert option for flat desecription
    public void doInsert(FlatDescription flatDesc) {
        Session s = NewHibernateUtil.getSessionFactory().openSession();
        s.beginTransaction();
        s.save(flatDesc);
        s.getTransaction().commit();
        s.close();
    }

    ///  insert option for flat information
    public int flatInformationInsert(FlatInformation flatInfo) {
        Session s = NewHibernateUtil.getSessionFactory().openSession();
        s.beginTransaction();
        Serializable ss = s.save(flatInfo);
        s.getTransaction().commit();
        s.close();
        return Integer.parseInt(ss.toString());
    }

    public int flatInformationUpdate(FlatInformation flatInfo) {
        Session s = NewHibernateUtil.getSessionFactory().openSession();
        s.beginTransaction();
        s.update(flatInfo);
//        s.delete(flatInfo.getFlatDescription());
//        s.delete(flatInfo.getFlatAddress());
        s.getTransaction().commit();
        s.close();
        return flatInfo.getFlatId();
    }

    public int flatInformationDelete(FlatInformation flatInfo) {
        Session s = NewHibernateUtil.getSessionFactory().openSession();
        s.beginTransaction();
//        s.delete(flatInfo.getFlatDescription());
//        s.delete(flatInfo.getFlatAddress());
        s.delete(flatInfo);
//        for(Object f:flatInfo.getFlatDescription().getFlatImages()){
//            s.delete((FlatImage)f);
//        }
        
        
        s.getTransaction().commit();
        s.close();
        return flatInfo.getFlatId();
    }

    ///  insert option for flat Adddress 

    public void flatAddressInsert(FlatAddress flatAddress) {
        Session s = NewHibernateUtil.getSessionFactory().openSession();
        s.beginTransaction();
        s.save(flatAddress);
        s.getTransaction().commit();
        s.close();
    }

    public void saveImages(int flatId, String imageName) {
        Session s = NewHibernateUtil.getSessionFactory().openSession();
        s.beginTransaction();
        FlatInformation fd = (FlatInformation) (s.createQuery("FROM FlatInformation where flatId=" + flatId).list().get(0));
        FlatDescription desc = fd.getFlatDescription();
        s.save(new FlatImage(desc, imageName));
        s.getTransaction().commit();
        s.close();
    }

    public int saveAdmin(Admin admin) {
        Session s = NewHibernateUtil.getSessionFactory().openSession();
        s.beginTransaction();
        Serializable ss = s.save(admin);
        s.getTransaction().commit();
        s.close();
        return Integer.parseInt(ss.toString());
    }

    public boolean findAdmin(String email, String pass) {
        Session s = NewHibernateUtil.getSessionFactory().openSession();
        s.beginTransaction();
        Query q = s.createQuery("from Admin where adminEmail =:e and adminPassword =:p");
        q.setParameter("e", email);
        q.setParameter("p", pass);
        List<Admin> ll = new ArrayList<>();
        ll = q.list();
        if (ll.size() > 0) {
            return true;
        } else {
            return false;
        }
    }
}
