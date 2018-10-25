/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dao;

import com.hibernate.FlatDescription;
import com.hibernate.FlatImage;
import com.hibernate.FlatInformation;
import com.hibernate.NewHibernateUtil;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.persistence.Temporal;
import javax.transaction.Transactional;
import org.hibernate.Query;
import org.hibernate.Session;

@ManagedBean
@SessionScoped
public class ImageDao {

    public ImageDao() {
        flatList=allFlat();
        
    }
    
    private List<FlatImage> imageList = new ArrayList<>();
    private List<FlatInformation> flatList = new ArrayList<FlatInformation>();
    private FlatInformation flatInformation = new FlatInformation();
    private List<String> mahallaList = new ArrayList<>();
    private String selectedMahallaName="All";
    private String selectedFlatSize="a";
    private String selectedFlatPrice="a";
    
    public List<FlatImage> getImageList() {
        return imageList;
    }

    public void setImageList(List<FlatImage> imageList) {
        this.imageList = imageList;
    }
    public List<String> findAllMahalla(){
        List<String> list = new ArrayList<>();
        Session s = NewHibernateUtil.getSessionFactory().openSession();
        s.beginTransaction();
        Query q = s.createQuery("SELECT DISTINCT(moholla) FROM FlatAddress");
        list = q.list();
        s.getTransaction().commit();
        s.close();
        return list;
    }
    public void findLatLon(){ 
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con=DriverManager.getConnection("jdbc:mysql://localhost/sunshine_group", "root", "apcl123456");
            PreparedStatement pst=con.prepareStatement("select lat, lon from location where flat_Info_id="+flatInformation.getFlatId());
            ResultSet rs=pst.executeQuery();
            while(rs.next()){
                flatInformation.setLat(rs.getDouble(1));
                flatInformation.setLon(rs.getDouble(2));
            }
            rs.close();
            pst.close();
            con.close();
        } catch (Exception e) {
        }
    
    }
    public void findFlatInformationByQuary(){
        String query="";
        String and="";
        if(!selectedMahallaName.equalsIgnoreCase("All")){
            query += " fi.flatAddress.moholla= '"+selectedMahallaName+"' ";
            and = " and ";
        }
        
        if(selectedFlatSize.equalsIgnoreCase("a")){
            query += "";
        }else if(selectedFlatSize.equalsIgnoreCase("s")){
            query += and +" fi.flatDescription.flatSize<=1000 ";
            and =" and ";
        }else if(selectedFlatSize.equalsIgnoreCase("m")){
            query += and +" fi.flatDescription.flatSize>1000 and fi.flatDescription.flatSize<=1200  ";
            and =" and ";
        }else if(selectedFlatSize.equalsIgnoreCase("b")){
            query += and +" fi.flatDescription.flatSize>1200 and fi.flatDescription.flatSize<=1500 ";
            and =" and ";
        }else if(selectedFlatSize.equalsIgnoreCase("e")){
            query += and +" fi.flatDescription.flatSize>1500 ";
            and =" and ";
        }
        
        if(selectedFlatPrice.equalsIgnoreCase("a")){
            query += "";
        }else if(selectedFlatPrice.equalsIgnoreCase("s")){
            query += and +" fi.pricePerSqure<=2000 ";
            and =" and ";
        }else if(selectedFlatPrice.equalsIgnoreCase("m")){
            query += and +" fi.pricePerSqure>2000 and fi.pricePerSqure<=3000  ";
            and =" and ";
        }else if(selectedFlatPrice.equalsIgnoreCase("b")){
            query += and +" fi.pricePerSqure>3000 and fi.pricePerSqure<=4000 ";
            and =" and ";
        }else if(selectedFlatPrice.equalsIgnoreCase("e")){
            query += and +" fi.pricePerSqure>4000 ";
            and =" and ";
        }
        if(!query.equalsIgnoreCase("")){
            query = "where "+query; 
        }
        List<FlatInformation> list = new ArrayList<FlatInformation>();
        Session s = NewHibernateUtil.getSessionFactory().openSession();
        s.beginTransaction();
        Query q = s.createQuery("FROM FlatInformation as fi "+query);
        list = q.list();
        for(int i=0; i<list.size(); i++){
            list.get(i).setFlatImage((FlatImage)list.get(i).getFlatDescription().getFlatImages().toArray()[0]);
        }
        s.getTransaction().commit();
        s.close();
        flatList =list;
    }
    
    public List<FlatImage> allList(){
        List<FlatImage> list = new ArrayList<>();
        Session s = NewHibernateUtil.getSessionFactory().openSession();
        s.beginTransaction();
        Query q = s.createQuery("FROM FlatImage");
        list = q.list();
        s.getTransaction().commit();
        s.close();
        return list;
    }
    
    @Transactional
    public List<FlatInformation> allFlat(){
        List<FlatInformation> list = new ArrayList<FlatInformation>();
        Session s = NewHibernateUtil.getSessionFactory().openSession();
        s.beginTransaction();
        Query q = s.createQuery("FROM FlatInformation");
        list = q.list();
        for(int i=0; i<list.size(); i++){
            try{
            list.get(i).setFlatImage((FlatImage)list.get(i).getFlatDescription().getFlatImages().toArray()[0]);
            }catch(Exception e){}
        }
        s.getTransaction().commit();
        s.close();
        return list;
    }
    public static void main(String[] args) {
       List<FlatInformation> ll = ( new ImageDao()).allFlat();
        System.out.println(ll.size());
        for (FlatInformation ll1 : ll) {
            System.out.println(ll1.getFlatId());
            System.out.println(ll1.getFlatImage().getImage());
            //System.out.println(((FlatImage)ll1.getFlatDescription().getFlatImages().toArray()[0]).getImage());
        }
    }
    public List<FlatInformation> getFlatList() {
        //flatList=allFlat();
        return flatList;
    }

    public void setFlatList(List<FlatInformation> flatList) {
        this.flatList = flatList;
    }

    public FlatInformation getFlatInformation() {
        flatInformation.setFlatDescription(flatInformation.getFlatDescription());
        flatInformation.setFlatAddress(flatInformation.getFlatAddress());
        
        return flatInformation;
    }

    public void setFlatInformation(FlatInformation flatInformation) {
        this.flatInformation = flatInformation;
    }

    public List<String> getMahallaList() {
        mahallaList=findAllMahalla();
        return mahallaList;
    }

    public void setMahallaList(List<String> mahallaList) {
        this.mahallaList = mahallaList;
    }

    public String getSelectedMahallaName() {
        return selectedMahallaName;
    }

    public void setSelectedMahallaName(String selectedMahallaName) {
        this.selectedMahallaName = selectedMahallaName;
    }

    public String getSelectedFlatSize() {
        return selectedFlatSize;
    }

    public void setSelectedFlatSize(String selectedFlatSize) {
        this.selectedFlatSize = selectedFlatSize;
    }

    public String getSelectedFlatPrice() {
        return selectedFlatPrice;
    }

    public void setSelectedFlatPrice(String selectedFlatPrice) {
        this.selectedFlatPrice = selectedFlatPrice;
    }
}
