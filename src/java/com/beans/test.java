/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.beans;

import com.dao.FlatDao;
import com.hibernate.FlatInformation;
import java.util.List;

/**
 *
 * @author apcl
 */
public class test {
    public static void main(String[] args) {
        List<FlatInformation> list=new FlatDao().findAllFlat();
        for(FlatInformation f:list){
            //
            System.out.println(f.getFlatId());
            System.out.println(f.getFlatDescription().getTotalRoom());
            System.out.println(f.getFlatAddress().getMoholla());
        }
    }
}
