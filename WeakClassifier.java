/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Practica2SI;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

/**
 *
 * @author aja10
 */
public class ClasificadorDebil {
    private static final int DIMENSION = 576;
    public ArrayList<Double> x;
    public ArrayList<Integer> p;
    private final int[] max = new int[DIMENSION];
    private final int[] min = new int[DIMENSION];
    public double hc;
    public ArrayList<Integer> posfallos;  
    
    // Crear un hiperplano aleatorio
    public ClasificadorDebil(ArrayList<Cara> lista){
        x = new ArrayList<Double>();
        p = new ArrayList<Integer>();

        
        // Sacamos el min y el max
        int j = 0;
        for(Cara c: lista)
        {
            for(int i = 0; i < DIMENSION; i++){
               if(j == 0){
                   max[i] = c.getData()[i];
                   min[i] = c.getData()[i];
               } else {
                   if(c.getData()[i] > max[i]){
                       max[i] = c.getData()[i];
                   }
                   if(c.getData()[i] < min[i]) {
                       min[i] = c.getData()[i];
                   }
               }
            }
            j++;
        }
        
        
        // Una vez tenemos el min y el max creamos el hiperplano
        // Lo unico que nos queda interesante de sacar es la c, la despejamos directamente
        for(int i = 0; i < DIMENSION; i++) {
            int a = ThreadLocalRandom.current().nextInt(min[i], max[i] + 1);
            p.add(a);
            double zx = ThreadLocalRandom.current().nextDouble(-1, 1);
            x.add(zx);
            hc += (((double) a)*zx);
        }
        
    }
    
    // Recibe una lista de caras y puntua los aciertos
    public float Entrenar(ArrayList<Cara> lista, ArrayList<Double> pesos){
        int clase;
        int pos = 0;
        float resultado = 0;
        posfallos = new ArrayList<Integer>();
        
        for(Cara c: lista)
        {
            
            clase = Clasifica(c);
            if(clase == c.getTipo())
                resultado += pesos.get(pos);
            else{
                posfallos.add(pos); // Posicion de las fotos que han fallado 
            }
            pos++;  
        }
        // Devuelve el error
        // A menor error mejor
        return resultado;
    }
    
    // Clasificador sencillo
    public int Clasifica(Cara ca){
        // Dada una cara la clasifica segun sea cara o no
        double xp = 0;
        
        for(int i = 0; i < DIMENSION; i++){
            //    punto del plano     pixel de la cara
            xp += x.get(i) * ca.getData()[i];
        }
        
        // D : xp - hc = 0
        // Formula para sub i en un espacio de dimensiones n
        
        double reshiperplano = xp - hc;

        if(reshiperplano >= 0) {
            return 1;
        } else {
            return -1;
        }
    }
    
  
}
