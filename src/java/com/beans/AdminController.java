/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.beans;

import com.dao.FlatDao;
import com.hibernate.Admin;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean
@SessionScoped
public class AdminController {
    private Admin ad = new Admin();
    
    public void saveAdmin(){
        ad.setAdminId(ad.getAdminId());
        ad.setAdminName(ad.getAdminName());
        ad.setAdminEmail(ad.getAdminEmail());
        ad.setAdminPassword(ad.getAdminPassword());
        
        FlatDao aa = new FlatDao();
        int aaa = aa.saveAdmin(ad);
    }

    public Admin getAd() {
        return ad;
    }

    public void setAd(Admin ad) {
        this.ad = ad;
    }
    
    public String doCheck(){
        ad.setAdminEmail(ad.getAdminEmail());
        ad.setAdminPassword(ad.getAdminPassword());
        FlatDao fl = new FlatDao();
        if(fl.findAdmin(ad.getAdminEmail(), ad.getAdminPassword())){
            return "Registration.xhtml";
        }else{
            return "login.xhtml";
        }
    }
            
}
