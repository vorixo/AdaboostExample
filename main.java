	aciertos = 0;
	        
    // Fallos de las caras y de las cosas que no son caras 
    int falloscaras = 0, falloscosas = 0;
	for(Cara c: listaTest)
	{
		clase = cf.Clasificar(c);  //TODO Cambiar -1 por la llamada a clasificar utilizando el clasificador fuerte
                            //de Adaboost para el ejemplo c
		if(clase == c.getTipo())
			aciertos++;
        else if(c.getTipo() == -1){
            falloscosas++;
        } else {
            falloscaras++;
        }
	}
	
    
    private ClasificadorDebil mejor(ArrayList<Double> pesos){
        ClasificadorDebil mejor = new ClasificadorDebil(listaAprendizaje);
        float aciertos = mejor.Entrenar(listaAprendizaje, pesos);
        
        for(int i = 0; i < NUM_CLASIFICADORES; i++) {
            ClasificadorDebil pretendientealtrono = new ClasificadorDebil(listaAprendizaje);
            float aciertosdelpreten = pretendientealtrono.Entrenar(listaAprendizaje, pesos);
            if(aciertosdelpreten > aciertos) {
                mejor = pretendientealtrono;
                aciertos = aciertosdelpreten;
            }
        }
        
        return mejor;
    }
    
    private ClasificadorFuerte adaboost(){
        
        // Inicializacion de la distribucion de pesos con 1/N
        ArrayList<Double> Di = new ArrayList<Double>();
        for(int i = 0; i < listaAprendizaje.size(); i++) {
            Di.add((double) 1 / listaAprendizaje.size());
        }
        
        ClasificadorFuerte cf = new ClasificadorFuerte();
        
        // Variables locales de uso
        // ArrayList<Double> h_loc = cd.hiperplano; DESCOMENTAR SI HACE FALTA
        // double c_loc = cd.hc; DESCOMENTAR SI HACE FALTA

        // Lo calificamos
        // Numero de hiperplanos que generamos (comienzo del entrenamiento)        
        
        
        for (int i = 0; i< NUM_ITERACIONES; i++) { 
           double epsilon = 0, alfa = 0, suma = 0, diaux = 0;
           // Genera NUM_CLASIFICADORES debiles y se queda con el mejor alocado en mejor 
            ClasificadorDebil mejor = mejor(Di);

           // Una vez hecho este bucle, mejor tendra stored el mejor de  clasificadores debiles
           // generados
           
           // Calculamos epsilon, es la suma de todos los fallados
           for(int j = 0; j < mejor.posfallos.size(); j++) {
               epsilon += Di.get(mejor.posfallos.get(j)); // bien
           }
           
           // Con epsilon podemos calcular alfa
           alfa = (double) 0.5 * Math.log(((double)1 - epsilon)/epsilon);
           // una vez tenemos alfa y el mejor hiperplano debil
           // lo anyadimos a un par de arraylist voto/hiperplano conjugado
           cf.AnyadirClasi(alfa, mejor);
           
           int w = 0; //o lo que sea

           // Revisar length + calculo de nuevos pesos
           for(int j = 0; j < listaAprendizaje.size(); j++) {
               if(mejor.posfallos.contains(j)) {
                   // FALLA + nuevo peso
                   diaux = (Di.get(j) * Math.exp(alfa));
               } else {
                   // ACIERTA - nuevo peso
                   diaux = (Di.get(j) * Math.exp(-alfa));
               }
               Di.set(j, diaux);
               suma += diaux;
           }
           
           for(int j = 0; j < listaAprendizaje.size(); j++) {
              Di.set(j, Di.get(j)/suma);
           }
           
           int h = 0; //o lo que sea
           // Al final de todo el entrenamiento tendremos almacenado en
           // h_loc el mejor hiperplano
           // c_loc la c del mismo   
        }
        return cf;
    }
    

