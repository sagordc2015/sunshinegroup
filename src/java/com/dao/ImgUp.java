/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dao;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import javax.servlet.http.Part;

/**
 *
 * @author Razu
 */
@ManagedBean
@SessionScoped
public class ImgUp {
    private Part img;
    
    public String upload() throws IOException{
        //img.write("C:\\data\\" + getFileName(img));
        if (img != null) {
            try {
                FacesContext context = FacesContext.getCurrentInstance();
                ServletContext servletcontext = (ServletContext) context.getExternalContext().getContext();
                String dbpath = servletcontext.getRealPath("/");
                String webcut = dbpath.substring(0, dbpath.lastIndexOf("\\"));
                String buildcut = webcut.substring(0, webcut.lastIndexOf("\\"));
                String mainURLPath = buildcut.substring(0, buildcut.lastIndexOf("\\"));
                //InputStream inputstream = file.getInputstream();
                String imgName=new Date().getTime() +getFileName(img);
                String path = mainURLPath + "\\SunShineGroup\\web\\resources\\images\\" +imgName;
                img.write(path);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        return "success";
    }

    public Part getImg() {
        return img;
    }

    public void setImg(Part img) {
        this.img = img;
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
    
}
