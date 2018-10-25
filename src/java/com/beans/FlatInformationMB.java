/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.beans;

import com.dao.FlatDao;
import com.hibernate.FlatAddress;
import com.hibernate.FlatDescription;
import com.hibernate.FlatImage;
import com.hibernate.FlatInformation;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import javax.servlet.http.Part;

/**
 *
 * @author apcl
 */
@ManagedBean
@SessionScoped
public class FlatInformationMB {
    private FlatInformation flatInformation=new FlatInformation();
    private FlatAddress flatAddress=new FlatAddress();
    private FlatDescription flatDescription=new FlatDescription();
    private Set<FlatImage> flatImages=new HashSet<FlatImage>();
    private List<Integer> flatIdList = new ArrayList<Integer>();
    private String msg="";
    private Part img;
    int descId, addressId;
    
    public void saveFlatInformation(){
        //Set Flat Address
        String imgName="";
        if (img != null) {
            try {
                FacesContext context = FacesContext.getCurrentInstance();
                ServletContext servletcontext = (ServletContext) context.getExternalContext().getContext();
                String dbpath = servletcontext.getRealPath("/");
                String webcut = dbpath.substring(0, dbpath.lastIndexOf("\\"));
                String buildcut = webcut.substring(0, webcut.lastIndexOf("\\"));
                String mainURLPath = buildcut.substring(0, buildcut.lastIndexOf("\\"));
                //InputStream inputstream = file.getInputstream();
                imgName=new Date().getTime() +getFileName(img);
                String path = mainURLPath + "\\SunShineGroup\\web\\resources\\images\\" +imgName;
                img.write(path);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        //Set<FlatImage> flatI=new HashSet<FlatImage>();
        //flatI.add(new FlatImage(imgName));
        //flatDescription.setFlatImages(flatI);
        
        flatAddress.setFlatNo(flatAddress.getFlatNo());
        flatAddress.setHouseNo(flatAddress.getHouseNo());
        flatAddress.setRoadNo(flatAddress.getRoadNo());
        flatAddress.setMoholla(flatAddress.getMoholla());
        flatAddress.setThana(flatAddress.getThana());
        flatAddress.setDistrict(flatAddress.getDistrict());
        flatInformation.setFlatAddress(flatAddress);
        
        //Set Flat Description
        flatDescription.setFlatSize(flatDescription.getFlatSize());
        flatDescription.setTotalRoom(flatDescription.getTotalRoom());
        flatDescription.setBedRoom(flatDescription.getBedRoom());
        flatDescription.setCorridor(flatDescription.getCorridor());
        flatDescription.setWashroom(flatDescription.getWashroom());
        flatDescription.setAttachedWashroom(flatDescription.getAttachedWashroom());
        flatDescription.setParking(flatDescription.getParking());
        flatDescription.setFacing(flatDescription.getFacing());
        flatDescription.setUnit(flatDescription.getUnit());
        flatDescription.setLandArea(flatDescription.getLandArea());
        flatDescription.setFlatStatus(flatDescription.getFlatStatus());
        
        flatInformation.setFlatDescription(flatDescription);        
        flatInformation.setPricePerSqure(flatInformation.getPricePerSqure());

        FlatDao fd=new FlatDao();
        int fId=fd.flatInformationInsert(flatInformation);
        fd.saveImages(fId, imgName);
        msg="Saved";
    }
    public void flatIdChangeListener(){
        int fId=flatInformation.getFlatId();
        flatInformation =new FlatDao().findFlatInfoById(fId);
        flatDescription = flatInformation.getFlatDescription();
        flatAddress =flatInformation.getFlatAddress();
        descId = flatDescription.getFlatDescriptionId();
        addressId = flatAddress.getAddressId();
    }
    
    public void updateFlatInformation(){
        //Set Flat Address
        String imgName="";
        if (img != null) {
            try {
                FacesContext context = FacesContext.getCurrentInstance();
                ServletContext servletcontext = (ServletContext) context.getExternalContext().getContext();
                String dbpath = servletcontext.getRealPath("/");
                String webcut = dbpath.substring(0, dbpath.lastIndexOf("\\"));
                String buildcut = webcut.substring(0, webcut.lastIndexOf("\\"));
                String mainURLPath = buildcut.substring(0, buildcut.lastIndexOf("\\"));
                //InputStream inputstream = file.getInputstream();
                imgName=new Date().getTime() +getFileName(img);
                String path = mainURLPath + "\\SunShineGroup\\web\\resources\\images\\" +imgName;
                img.write(path);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        //Set<FlatImage> flatI=new HashSet<FlatImage>();
        //flatI.add(new FlatImage(imgName));
        //flatDescription.setFlatImages(flatI);
        //flatAddress.setAddressId(11);
        flatAddress.setFlatNo(flatAddress.getFlatNo());
        flatAddress.setHouseNo(flatAddress.getHouseNo());
        flatAddress.setRoadNo(flatAddress.getRoadNo());
        flatAddress.setMoholla(flatAddress.getMoholla());
        flatAddress.setThana(flatAddress.getThana());
        flatAddress.setDistrict(flatAddress.getDistrict());
        flatInformation.setFlatAddress(flatAddress);
        
        //Set Flat Description
        //flatDescription.setFlatDescriptionId(12);
        flatDescription.setFlatSize(flatDescription.getFlatSize());
        flatDescription.setTotalRoom(flatDescription.getTotalRoom());
        flatDescription.setBedRoom(flatDescription.getBedRoom());
        flatDescription.setCorridor(flatDescription.getCorridor());
        flatDescription.setWashroom(flatDescription.getWashroom());
        flatDescription.setAttachedWashroom(flatDescription.getAttachedWashroom());
        flatDescription.setParking(flatDescription.getParking());
        flatDescription.setFacing(flatDescription.getFacing());
        flatDescription.setUnit(flatDescription.getUnit());
        flatDescription.setLandArea(flatDescription.getLandArea());
        flatDescription.setFlatStatus(flatDescription.getFlatStatus());
        
        flatInformation.setFlatDescription(flatDescription);        
        flatInformation.setPricePerSqure(flatInformation.getPricePerSqure());
        flatInformation.setFlatId(flatInformation.getFlatId());
        FlatDao fd=new FlatDao();
        int fId=fd.flatInformationUpdate(flatInformation);
        if(!imgName.equals("")){
            fd.saveImages(fId, imgName);
        }
        
        msg="Updated";
    }
    
    public void deleteFlatInformation(){
        flatInformation.setFlatId(flatInformation.getFlatId());
        new FlatDao().flatInformationDelete(flatInformation);
    }
    
    private static String getFileName(Part part){
        for(String cd : part.getHeader("content-disposition").split(";")){
            if(cd.trim().startsWith("filename")){
                String imgName = cd.substring(cd.indexOf('=')+1).trim().replace("\"","");
                return imgName.substring(imgName.lastIndexOf('/')+1).substring(imgName.lastIndexOf('\\')+1);
            }
        }
        return null;
    }
    
    
    public FlatInformation getFlatInformation() {
        return flatInformation;
    }

    public void setFlatInformation(FlatInformation flatInformation) {
        this.flatInformation = flatInformation;
    }

    public FlatAddress getFlatAddress() {
        return flatAddress;
    }

    public void setFlatAddress(FlatAddress flatAddress) {
        this.flatAddress = flatAddress;
    }

    public FlatDescription getFlatDescription() {
        return flatDescription;
    }

    public void setFlatDescription(FlatDescription flatDescription) {
        this.flatDescription = flatDescription;
    }

    public Set<FlatImage> getFlatImages() {
        return flatImages;
    }

    public void setFlatImages(Set<FlatImage> flatImages) {
        this.flatImages = flatImages;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Part getImg() {
        return img;
    }

    public void setImg(Part img) {
        this.img = img;
    }

    public List<Integer> getFlatIdList() {
        flatIdList =new FlatDao().findAllFlatID();
        return flatIdList;
    }

    public void setFlatIdList(List<Integer> flatIdList) {
        this.flatIdList = flatIdList;
    }
    
}
