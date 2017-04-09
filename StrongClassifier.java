/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Practica2SI;

import java.util.ArrayList;

/**
 *
 * @author aja10
 */
public class ClasificadorFuerte {
    public ArrayList<Double> voto;
    public ArrayList<ClasificadorDebil> clasi;
            
    public ClasificadorFuerte(){
        voto = new ArrayList<Double>();
        clasi = new ArrayList<ClasificadorDebil>();
    }
    
    public void AnyadirClasi(double pesodelvoto, ClasificadorDebil mejor) {
        voto.add(pesodelvoto);
        clasi.add(mejor);        
    }
    
    public int Clasificar(Cara ca) {
        double result = 0;
        for(int i = 0; i < voto.size(); i++) {
            result += clasi.get(i).Clasifica(ca) * voto.get(i);
        }
        if(result >= 0) {
            return 1;
        } else {
            return -1;
        }
    }
    
}
