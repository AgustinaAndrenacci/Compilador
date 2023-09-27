
package Compilador;

import java.io.IOException;

public class AnalizadorSintactico {
    //int base=0,desplazamiento;
    AnalizadorLexico alex;
    IndicadorDeErrores inderr;
    AnalizadorSemantico aSem;
    Terminal t;
        
    public AnalizadorSintactico(AnalizadorLexico alex, IndicadorDeErrores inderr, AnalizadorSemantico aSem) {
        this.alex = alex;
        this.inderr = inderr;
        this.aSem = aSem;
    }
    
    //va a escanear todo el programa. 
    //agarra al lexico, excanea y aca chequea (repetido)
    public void analizar() throws IOException{
        t=alex.escanear();
        programa (); 
        
       if (alex.getSimbolo()==Terminal.EOF){inderr.mostrarError(0);}
       else{ System.out.println("nose");}
           
       //para ver lo que hay en la tabla, despues las siguientes dos lineas se borran
        System.out.println("****muestro la tabla");
        aSem.mostrarTabla();
      // MODIFICAR, EL RENGLON SIGUIENTE ESTA HECHO SOLO PARA PROBAR SI ESCANEA TODO EL LEXICO
//        while(t!=Terminal.EOF){
//            t=alex.escanear();
//           //System.out.println("****Terminal leido en AS: "+t);
//        }
//        System.out.println("El archivo se leyo de manera completa");     
    }

    private void programa() {
        //bloque(0);
        bloque();
        if(alex.getSimbolo()==Terminal.PUNTO) t=alex.escanear(); //si es correcto, lee EOF
        else inderr.mostrarError(2);
 }

    //private void bloque(int base){
    private void bloque(){
        int desplazamiento=0;
        String nombre="";
        Terminal tipo=Terminal.CADENA_LITERAL;
        int valor=0;
                
        if(alex.getSimbolo()== Terminal.CONST)
        {
            t=alex.escanear();
            while(alex.getSimbolo()!=Terminal.PUNTO_Y_COMA)
            {
               if(alex.getSimbolo()== Terminal.IDENTIFICADOR){
                   nombre=alex.getCadena();
                   tipo=Terminal.CONST;
                   t=alex.escanear();
               } 
               else inderr.mostrarError(4);
               
               if(alex.getSimbolo()== Terminal.IGUAL)t=alex.escanear(); 
               else inderr.mostrarError(5);
               
               if(alex.getSimbolo()== Terminal.NUMERO){
                   valor=Integer.parseInt(alex.getCadena());
                   t=alex.escanear();
               } 
               else inderr.mostrarError(6);
               
               if(alex.getSimbolo()== Terminal.COMA) t=alex.escanear(); 
               else 
                   if(alex.getSimbolo()!= Terminal.PUNTO_Y_COMA) inderr.mostrarError(8);
               //crear identificador
               aSem.setIdentificador(nombre, tipo, valor);
            } //cierra el while   
           
            t=alex.escanear(); //escanea una vez que verifica que es un punto y coma
        }
        
        
        if(alex.getSimbolo()== Terminal.VAR)
        {
            t=alex.escanear();
            while( alex.getSimbolo()!=Terminal.PUNTO_Y_COMA){
           
                if(alex.getSimbolo()== Terminal.IDENTIFICADOR){
                   nombre=alex.getCadena();
                   tipo=Terminal.VAR;
                   valor=0;
                   //crear identificador
                    aSem.setIdentificador(nombre, tipo, valor);
                   t=alex.escanear();
               }  
                else inderr.mostrarError(4);
                
                if(alex.getSimbolo()== Terminal.COMA) t=alex.escanear(); 
                else 
                   if(alex.getSimbolo()!= Terminal.PUNTO_Y_COMA) inderr.mostrarError(8); //error en conjunto de , y ;
            } //cierra el while   
            
            t=alex.escanear(); //escanea una vez que verifica que es un punto y coma
        }
        
        while(alex.getSimbolo()== Terminal.PROCEDURE) //puede haber varios procedimientos
        //if(alex.getSimbolo()== Terminal.PROCEDURE)
        {
            t=alex.escanear();
            while( alex.getSimbolo()!=Terminal.PUNTO_Y_COMA){
                
                if(alex.getSimbolo()== Terminal.IDENTIFICADOR){
                   nombre=alex.getCadena();
                   tipo=Terminal.PROCEDURE;
                   valor=0;
                   //crear identificador
                    aSem.setIdentificador(nombre, tipo, valor);
                   t=alex.escanear();
               }  
                else inderr.mostrarError(4);
               
                if(alex.getSimbolo()== Terminal.PUNTO_Y_COMA) t=alex.escanear();
                else inderr.mostrarError(9);
                
                bloque();
                if(alex.getSimbolo()!= Terminal.PUNTO_Y_COMA) inderr.mostrarError(9); 
                
            }           
            t=alex.escanear();
        }
        
        proposicion();
    } 
   
    private void proposicion() {
      //  System.out.println("entre en proposicion y soy: "+t);
        if(alex.getSimbolo()==t.IDENTIFICADOR){
            t=alex.escanear();
           // System.out.println("lei: "+t);
            if(alex.getSimbolo()==t.ASIGNACION){
                t=alex.escanear();
                expresion();
            }else inderr.mostrarError(10);   
        } 
        else
        if(alex.getSimbolo()==t.CALL){
            t=alex.escanear();
            if(alex.getSimbolo()==t.IDENTIFICADOR) t=alex.escanear();
            else inderr.mostrarError(4); 
        }
        else
        if(alex.getSimbolo()==t.BEGIN){
             t=alex.escanear();
            // System.out.println("ss");
             proposicion();
             //System.out.println("oli");
            // t=alex.escanear();
             //System.out.println("terminal: "+t);
             if(alex.getSimbolo()!=Terminal.PUNTO_Y_COMA && alex.getSimbolo()!=Terminal.END)
             {
                // System.out.println("aqui");
                 inderr.mostrarError(27);
             }
             
             while(alex.getSimbolo()==Terminal.PUNTO_Y_COMA) //modificar
             {
                 t=alex.escanear();
                 if(alex.getSimbolo()!=Terminal.END) proposicion(); //debido a que sino toma end como proposicion
                // System.out.println("ola");
             }
             //System.out.println("ola");
             if(alex.getSimbolo()==t.END)t=alex.escanear();
                 else inderr.mostrarError(27);
        }
        else
        if(alex.getSimbolo()==t.IF)
        {
            t=alex.escanear();
            condicion();
            if(alex.getSimbolo()==t.THEN)
            {
                t=alex.escanear();
                proposicion();
            }else{ inderr.mostrarError(12);}
        }
        else
        if(alex.getSimbolo()==t.WHILE)
        {
            t=alex.escanear();
            condicion();

            if(alex.getSimbolo()==t.DO)
            {
                t=alex.escanear();
                proposicion();
            }else inderr.mostrarError(13);
        }
        else
        if(alex.getSimbolo()==t.READLN)
        {
            t=alex.escanear();
            if(alex.getSimbolo()==t.ABRE_PARENTESIS) 
            {
                t=alex.escanear();
                
                if(alex.getSimbolo()==t.IDENTIFICADOR)
                {
                    t=alex.escanear();
                    
                    while(alex.getSimbolo()==Terminal.COMA)
                     {
                         t=alex.escanear();
                         
                         if(alex.getSimbolo()==t.IDENTIFICADOR) t=alex.escanear();
                         else{inderr.mostrarError(4);}
                     }
                    
                    if(alex.getSimbolo()==t.CIERRA_PARENTESIS)t=alex.escanear();
                    else{inderr.mostrarError(24);}
                }
                else{inderr.mostrarError(4);}
                    
                
            }else{inderr.mostrarError(22);}
        }
        else
        if(alex.getSimbolo()==t.WRITE){ 
            t=alex.escanear();
            if(alex.getSimbolo()==t.ABRE_PARENTESIS){ //para el write
                t=alex.escanear();
                //cadena o expresion
                if(alex.getSimbolo()==Terminal.CADENA_LITERAL)//cadena
                {
                    t=alex.escanear();
                    
                }
                else
                {
                    expresion();
                }
                while(alex.getSimbolo()==Terminal.COMA)
                {
                         t=alex.escanear();
                        if(alex.getSimbolo()==Terminal.CADENA_LITERAL)//cadena
                        {
                            t=alex.escanear();

                        }
                        else
                        {
                            expresion();
                        }
                }
                //System.out.println("aevevveve");
                if(alex.getSimbolo()==t.CIERRA_PARENTESIS)t=alex.escanear();
                else{inderr.mostrarError(28);}
               
               // System.out.println("toy por aqui");
            } 
            else{inderr.mostrarError(22);}
        }
        else
        if(alex.getSimbolo()==t.WRITELN){ 
            t=alex.escanear();
            if(alex.getSimbolo()==t.ABRE_PARENTESIS){ //para el write
                t=alex.escanear();
                //System.out.println("chegue");
                //cadena o expresion
                if(alex.getSimbolo()==Terminal.CADENA_LITERAL)//cadena
                {
                    t=alex.escanear();
                    
                }
                else if(alex.getSimbolo()==Terminal.CARACTER_ERRONEO){ //AGREGADO
                    inderr.mostrarError(23);
                }
                else
                {
                    expresion();
                }
                
                while(alex.getSimbolo()==Terminal.COMA)
                {
                         t=alex.escanear();
                        if(alex.getSimbolo()==Terminal.CADENA_LITERAL)//cadena
                        {
                            t=alex.escanear();

                        }
                        else if(alex.getSimbolo()==Terminal.CARACTER_ERRONEO){ //AGREGADO
                            inderr.mostrarError(23);
                        }
                        else
                        {
                            expresion();
                        }
                }
                
                if(alex.getSimbolo()==t.CIERRA_PARENTESIS) t=alex.escanear();
                else{inderr.mostrarError(28);}
               
            }
        }
            
   }

   
    
     private void condicion() {
       if(alex.getSimbolo()==t.ODD)
       {
           t=alex.escanear();
           expresion();
       }else 
       {
           expresion();
           if(alex.getSimbolo()==t.IGUAL){t=alex.escanear();}
           else if(alex.getSimbolo()==t.DISTINTO){t=alex.escanear();}
           else if(alex.getSimbolo()==t.MENOR){t=alex.escanear();;}
           else if(alex.getSimbolo()==t.MENOR_IGUAL){t=alex.escanear();}
           else if(alex.getSimbolo()==t.MAYOR){t=alex.escanear();}
           else if(alex.getSimbolo()==t.MAYOR_IGUAL){t=alex.escanear();}
           else inderr.mostrarError(15);
           expresion();
           
       }         
    }


    private void termino() {
        factor();
           while(alex.getSimbolo()==Terminal.POR || alex.getSimbolo()==Terminal.DIVIDIDO)
           {
               if(alex.getSimbolo()==t.POR){t=alex.escanear(); }
               else if(alex.getSimbolo()==t.DIVIDIDO){t=alex.escanear(); }
               else inderr.mostrarError(16);
               factor();
           }
    }

    private void factor() {
         if(alex.getSimbolo()==t.IDENTIFICADOR) t=alex.escanear(); 
 
         else
         if(alex.getSimbolo()==t.NUMERO) t=alex.escanear(); 
         else
         if(alex.getSimbolo()==t.ABRE_PARENTESIS)
         {
             t=alex.escanear();
             expresion();
             if(alex.getSimbolo()==t.CIERRA_PARENTESIS) t=alex.escanear(); 
             else inderr.mostrarError(19);
         }
         else inderr.mostrarError(18);
                  
    }


    private void expresion() 
    {
       // System.out.println("es una expresion");
        if(alex.getSimbolo()==t.MAS){t=alex.escanear();}
        else if(alex.getSimbolo()==t.MENOS){t=alex.escanear();}
        termino();
        
        while( alex.getSimbolo()==Terminal.MAS || alex.getSimbolo()==Terminal.MENOS)
             {
                 if(alex.getSimbolo()==t.MAS){t=alex.escanear(); }
                 else if(alex.getSimbolo()==t.MENOS){t=alex.escanear(); }
                 else inderr.mostrarError(20);
                 termino();
             }
        
    }

}
