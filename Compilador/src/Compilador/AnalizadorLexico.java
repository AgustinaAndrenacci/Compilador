
package Compilador;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class AnalizadorLexico {
        //declarar de manera glbal para que se pueda leer el archivo y todos los metodos puedan
        //acceder
        boolean flag_cadena=false;
        Terminal t;
        File archivo;
        FileReader  fr;
        BufferedReader br;
        String cadena; //cadena obtenida
        String linea; //linea leida
        char c=' '; //Caracter leido de la linea
        int contCaracter=0; //contador de caracteres
        int contLinea=0;
        int n=0; //digitos de la linea

    public AnalizadorLexico(String nomArch) throws FileNotFoundException {
      
        this.archivo = new File (nomArch);
        this.fr = new FileReader (archivo);
        this.br = new BufferedReader(fr);
    }

    public Terminal escanear () //lee la linea y devuelve lo que da getsimbolo
    {  
        try {
          //  System.out.println("voy a otro if");
           // if(t!=t.EOF){
                if(contLinea==0 && contCaracter==0)//primer linea y primer caracter
                { 
                    linea = br.readLine(); //lee la linea
                    n=linea.length(); 

                    if(n>0) //chequeo que no sea una linea vacia
                    { 
                      //  System.out.println("es linea vacia");
                        mensajeNumeroDeLinea(linea,contLinea); //muestra el numero de linea y la linea
                        contLinea++;
                        c=linea.charAt(contCaracter);  //obtengo siguiente char
                        identificarCadena();
                       // if(t.ESPACIO==t) pasarElEspacio(); //continua leyendo
                    }
                }
                else
                if(n>0 && !(contCaracter+1> n)) //si no es vacia la cadena, tengo que seguir analizando cada char
                {//NUEVOO !(contCaracter+1> n
                  //  System.out.println("opc1, linea: "+linea);
                   // System.out.println("contCaracter+1> n: "+(contCaracter+1== n));
                   
                    c=linea.charAt(contCaracter);  //obtengo siguiente char
                   // System.out.println("continuo leyendo la linea porque c_ "+c);
                    identificarCadena();
                    
                   // if(t.ESPACIO==t) pasarElEspacio();//continua leyendo
                }    
                else

                    if(contCaracter+1> n) // | finalizo la linea 
                    { //System.out.println("opc2, *linea: "+linea);
                      //  System.out.println("vine aqui eN EL BEGIN, C_"+cadena);
                       
                       contCaracter=0;
                       linea = br.readLine(); //lee la linea
                        if(linea!=null)
                        {
                            n=linea.length();
                            if(n>0)//chequeo que no sea una linea vacia
                            {
                                
                                mensajeNumeroDeLinea(linea,contLinea); //muestra el numero de linea y la linea
                                contLinea++;
                                c=linea.charAt(contCaracter);  //obtengo siguiente char
                                identificarCadena();

                                    
                               // if(t.ESPACIO==t) pasarElEspacio(); //continua leyendo
                            } 
                            else //es  nulo, paso a la siguiente linea
                            {
                                pasarLineaVacia();
                            }
                        }
                        else
                            
                        {
                           getFinArchivo();
                        }
                        //System.out.println("sali de la palabra y tengo c: "+c);
                    }
                
            }
        catch(IOException e){}
        //System.out.println("cadena obtenida: "+cadena+" y la terminal: "+t);
        return t;
    }
    
    
    private void identificarCadena() throws IOException {
     //System.out.println("IDENTIFICO-cadena recibida: "+cadena+ " terminal: "+t);
     //if(t.BEGIN==t){//System.out.println("LEI BEGINNN");System.out.println("getCadenas(): "+getCadenas());}
        //System.out.println("getCadenas(): "+(getCadenas()));
        if (getEntero()!="")
        {
            t=Terminal.NUMERO;
            System.out.println(toString());
        }
        else 
            if(getCadenas()!="" )  
            {
                System.out.println(toString());
   
            }
            
            else
            {
                cadena=getSimbolos();
              //  System.out.println("encontre a vee: cadena:"+cadena+" terminal: "+t);
                
                //no muestra el tostring del espacio
                if(t.ESPACIO==t) 
                { 
                    pasarElEspacio();//continua leyendo                 
                }
                else
                {
                    System.out.println(toString());                    
                }
                     
                                    
                if(t==t.LINEA_VACIA){
                    escanear();
                }

               
            }
    }   

    
    public Terminal getSimbolo(){ //TE DEVUELVE LA TERMINAL
        return t;
    }
    
    public String getCadena(){ //ES EL QUE TE TRAE EL SIMBOLO/CADENA
        return cadena;
    }
   
    
    public String getEntero(){ //INGRESO UNA CONDICION 
        cadena="";
        int flag=0;
        if(Character.isDigit(c))
        {
            cadena=cadena+c;
            
            while(Character.isDigit(c) && n!=cadena.length() && contCaracter<n)
            {
                contCaracter++;
                if(n-contCaracter >0)
                {
                    c=linea.charAt(contCaracter); //agarro el digito
                    if (!Character.isDigit(linea.charAt(contCaracter))) //contemplo 123;
                    {
                        flag=1;
                    }
                    else //modo normal 123
                    {
                        cadena=cadena+c;
                    }  
                }  
            }
        }
        return cadena;
    }
    
    public String getCadenas() throws IOException{ //INGRESO UNA CONDICION
        cadena="";
       // System.out.println("****c: "+c+ "n vale");
        if(Character.isLetter(c))
        {
            cadena=cadena+c;
            while((Character.isDigit(c)||Character.isLetter(c))&& contCaracter+1<= n && n!=(cadena.length()) && c!=' ') //contCaracter+1<= n  //n!=(cadena.length()+1)
            {
                contCaracter++;
             if(( n-(contCaracter+1)>=0 )) //n-(contCaracter+1)>=0
                {
                    c=linea.charAt(contCaracter);
                    if(this.tieneSimbolo(c)) cadena=cadena+c;
                    
                }       
            }
             this.devuelveCadena(cadena);   
           
             if(cadena.length()==n){ //Se soluciona el BEGIN N
                 contCaracter++;
             }
        }
        return cadena;
    }
    
    public String getSimbolos() throws IOException{ //IF //VERIFICACION DE SOLO SIMBOLOS
        char comilla;
        cadena="";
        String comilla_simple="'";
        comilla = comilla_simple.charAt(0);
        if(linea ==null || linea.isEmpty())//es una linea vacia. NULL: porque a veces el isEMpty no lo toma
        {
            System.out.println("Es una linea vacia");
        }
         switch(c)
        {
            case ':':
                contCaracter++;
                cadena= cadena+c;
                if(contCaracter!=n)c=linea.charAt(contCaracter);
                if(c=='=')
                {
                    contCaracter++; //agregue
                    t=t.ASIGNACION;
                    cadena= cadena+c;
                }
                else t=t.NULO;
                break;
            case '<':
                contCaracter++;
                cadena= cadena+c;
                if(contCaracter!=n)c=linea.charAt(contCaracter);
                if(c=='='){
                    contCaracter++; //agregue
                    t=t.MENOR_IGUAL;
                     cadena= cadena+c;
                }
                else 
                    if(c=='>')
                    {
                        contCaracter++; //agregue
                        t=t.DISTINTO;
                        cadena= cadena+c;
                    }
                    else 
                    {
                        t=t.MENOR;
                        cadena= cadena+c;
                    }
                break;
            case '>':
                contCaracter++;
                 cadena= cadena+c;
                if(contCaracter!=n) c=linea.charAt(contCaracter);
                if(c=='=') {
                    contCaracter++; //agregue
                    t=t.MAYOR_IGUAL;
                    cadena= cadena+c;
                }
                else t=t.MAYOR;
                break;
            case ';':
                contCaracter++;
                cadena= cadena+c;
                if(contCaracter!=n) c=linea.charAt(contCaracter);
                t=t.PUNTO_Y_COMA;
                break;
            case '(':
                contCaracter++;
                cadena= cadena+c;
                if(contCaracter!=n) c=linea.charAt(contCaracter);
                t=t.ABRE_PARENTESIS;
                break;
            case ')':
                contCaracter++;
                cadena= cadena+c;
                if(contCaracter!=n) c=linea.charAt(contCaracter);
                t=t.CIERRA_PARENTESIS;
                break;
            case ',':
                contCaracter++;
                cadena= cadena+c;
                if(contCaracter!=n) c=linea.charAt(contCaracter);
                t=t.COMA;
                break;
            case '=':
                //System.out.println("entre en el igual y cadena vale: "+cadena+" y c vale: "+c);
                contCaracter++;
                cadena= cadena+c;
               // System.out.println("entre en el igual y cadena vale: "+cadena+" y c vale: "+c);
                if(contCaracter!=n) c=linea.charAt(contCaracter);
                t=t.IGUAL;
                break;
            case '+':
                contCaracter++;
                cadena= cadena+c;
                if(contCaracter!=n) c=linea.charAt(contCaracter);
                t=t.MAS;
                break;
            case '-':
                contCaracter++;
                cadena= cadena+c;
                if(contCaracter!=n) c=linea.charAt(contCaracter);
                t=t.MENOS;
                break;
            case '*':
                contCaracter++;
                cadena= cadena+c;
                if(contCaracter!=n) c=linea.charAt(contCaracter);
                t=t.POR;
                break;
            case '/':
                contCaracter++;
                cadena= cadena+c;
                if(contCaracter!=n) c=linea.charAt(contCaracter);
                t=t.DIVIDIDO;
                break;
            case '.':
                contCaracter++;
                cadena= cadena+c;
                if(contCaracter!=n) c=linea.charAt(contCaracter);
                t=t.PUNTO;
                break;
            case ' ':
                contCaracter++;
                cadena=cadena+c;
                c=linea.charAt(contCaracter);
                t=t.ESPACIO;
                break;
            default:  
                if(c==comilla)
                {
                    //es una cadena literal
                    generarCadenaLiteral();
                    
                }  
                else //para caracteres que no utiliza pl0
                {
                    contCaracter++;
                    cadena= cadena+c;
                    if(contCaracter!=n) c=linea.charAt(contCaracter);
                    t=t.CARACTER_ERRONEO;
                    break;
                    
                }
                break;
        }
        // System.out.println("caddena obtenida: "+cadena);
         return cadena;
    }
    
    
    
    public void devuelveCadena(String c){
  
        if(c.compareToIgnoreCase("IF")==0) t=t.IF;  
        else if(c.compareToIgnoreCase("CALL")==0) t=t.CALL; 
        else if(c.compareToIgnoreCase("BEGIN")==0) t=t.BEGIN;  
        else if(c.compareToIgnoreCase("END")==0) t=t.END;  
        else if(c.compareToIgnoreCase("THEN")==0) t=t.THEN;  
        else if(c.compareToIgnoreCase("WHILE")==0) t=t.WHILE;  
        else if(c.compareToIgnoreCase("DO")==0) t=t.DO;  
        else if(c.compareToIgnoreCase("ODD")==0) t=t.ODD;  
        else if(c.compareToIgnoreCase("READLN")==0) t=t.READLN;  
        else if(c.compareToIgnoreCase("WRITE")==0) t=t.WRITE;      
        else if(c.compareToIgnoreCase("WRITELN")==0) t=t.WRITELN;  
        else if(c.compareToIgnoreCase("PROCEDURE")==0) t=t.PROCEDURE;  
        else if(c.compareToIgnoreCase("CONST")==0) t=t.CONST; 
        else if(c.compareToIgnoreCase("VAR")==0) t=t.VAR;  
        else t=t.IDENTIFICADOR;
        //System.out.println("");
        
    }

    private void pasarElEspacio() throws IOException{
        c=linea.charAt(contCaracter);  //obtengo siguiente char
        identificarCadena(); 
        if(t.ESPACIO==t) pasarElEspacio();
    }
    
    private void pasarLineaVacia() throws IOException{
        
       contCaracter=0;
       linea = br.readLine(); //lee la linea

        if(linea!=null)
        {
            n=linea.length();
            if(n>0)//chequeo que no sea una linea vacia
            { 
                mensajeNumeroDeLinea(linea,contLinea); //muestra el numero de linea y la linea
                contLinea++;
                c=linea.charAt(contCaracter);  //obtengo siguiente char
                identificarCadena();
            }  
            else pasarLineaVacia();
                
         }

    }
    
                            
    public void mensajeNumeroDeLinea(String linea, int n){
        System.out.println("------------------------------------");
        System.out.println("Linea "+n+": "+linea);
    }
    
    public boolean tieneSimbolo(char c) { 
        return c!=' ' && c!='(' && c!=')' && c!=';' && c!=',' && c!='.' && c!='*' && c!='+' && c!='-'
                 && c!='*' && c!='/' && c!=':' && c!='<' && c!='>'&& c!='=';
    }
       
    public String generarCadenaLiteral() throws IOException{
        
        char comilla;
        String comilla_simple="'";
        comilla = comilla_simple.charAt(0);
        String cadena_literal="";
        c=linea.charAt(contCaracter); 
       
        contCaracter++;
        cadena= cadena+c;
        if(contCaracter!=n) c=linea.charAt(contCaracter);
        while(c!=comilla && contCaracter!=n && ( c!=')'|| contCaracter+1!=n))
        {
            contCaracter++;
            cadena= cadena+c;
            if(contCaracter!=n) c=linea.charAt(contCaracter);
            else{ lineaVacia() ; }
        }
        
        contCaracter++;
        cadena=cadena+c;
       
        //if(contCaracter!=n){
           // c=linea.charAt(contCaracter);
            t=t.CADENA_LITERAL;
        //}
        return cadena;
    }
    
    private void lineaVacia() throws IOException{
        
       contCaracter=0;
       //System.out.println("aa");
       linea = br.readLine(); //lee la linea
       

        if(linea!=null)
        {
            n=linea.length();
            if(n>0)//chequeo que no sea una linea vacia
            { 
                mensajeNumeroDeLinea(linea,contLinea); //muestra el numero de linea y la linea
                contLinea++;
                c=linea.charAt(contCaracter);  //obtengo siguiente char
               // System.out.println("ola");
            }  
            else{ lineaVacia(); }      
         }
       
    }
    
    
    public void getFinArchivo(){
        t=t.EOF;
        cadena= "eof";       
    }
    
    @Override
    public String toString() {
        return "-"+t+": " + cadena;
    }

}
