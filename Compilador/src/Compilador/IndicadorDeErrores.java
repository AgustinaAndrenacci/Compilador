
package Compilador;


public class IndicadorDeErrores {

    private boolean error=false;

    public IndicadorDeErrores() {
        this.error = error;
    }

   
    public void mostrarError(int cod) {
        if(cod==0)System.out.println ("**PROGRAMA OK**");
        else{
        switch(cod)
        {
        case 1: System.out.println ("**ERRORR** Sobran simbolos"); break;
        case 2: System.out.println ("**ERRORR** Se esperaba un punto"); break;
        case 3: System.out.println ("**ERRORR** Se esperaba una constante"); break;
        case 4: System.out.println ("**ERRORR** Se esperaba un identificador"); break;
        case 5: System.out.println ("**ERRORR** Se esperaba el simbolo igual"); break;
        case 6: System.out.println ("**ERRORR** Se esperaba un numero"); break;
        case 7: System.out.println ("**ERRORR** Se esperaba una coma"); break;
        case 8: System.out.println ("**ERRORR** Se esperaba una coma o un punto y coma"); break;
        case 9: System.out.println ("**ERRORR** Se esperaba un punto y coma"); break;
        case 10: System.out.println ("**ERRORR** Se esperaba una asignacion (:=)"); break;
        case 11: System.out.println ("**ERRORR** Se esperaba un CA"); break;
        case 12: System.out.println ("**ERRORR** Se esperaba un THEN"); break;
        case 13: System.out.println ("**ERRORR** Se esperaba un DO"); break;
        case 14: System.out.println ("**ERRORR** Se esperaba un END"); break;
        case 15: System.out.println ("**ERRORR** Se esperaba un =, <>, <, <=, > o >="); break;
        case 16: System.out.println ("**ERRORR** Se esperaba un signo de multiplicar o de division"); break;
        case 17: System.out.println ("**ERRORR** Se esperaba un numero"); break; 
        case 18: System.out.println ("**ERRORR** Se esperaba un parentesis abriendo, un numero o un identificador"); break;
        case 19: System.out.println ("**ERRORR** Se esperaba un parentesis cerrando"); break;
        case 20: System.out.println ("**ERRORR** Se esperaba un signo de suma o resta"); break;
        case 21: System.out.println ("**ERRORR** Se esperaba un identificador o alguna palabra reservada (CALL, BEGIN, IF, WRITE, WRITELN, READLN o WHILE)"); break;
        case 22: System.out.println ("**ERRORR** Se esperaba un parentesis abriendo"); break;
        case 23: System.out.println ("**ERRORR** Se esperaba una cadena empezando con comillas simples (')"); break;
        case 24: System.out.println ("**ERRORR** Se esperaba un cierre de parenteis o una coma"); break;
        case 25: System.out.println ("**ERRORR** Se esperaba una comilla simple"); break;
        case 26: System.out.println ("**ERRORR** Se esperaba una cadena literal"); break; 
        case 27: System.out.println ("**ERRORR** Se esperaba un ; o un END"); break;
        case 28: System.out.println ("**ERRORR** Se esperaba un parentesis cerrando y/o comillas simple"); break;
        }
        System.exit(0);}
    }
    
    public boolean getValorError()
    {
        //this.error=true;
        return this.error;
    }

     public void getValorErrors()
    {
        //this.error=true;
        System.out.println("entre a la funcion");
        System.out.println("error vale: "+error);
        //return this.error;
    }
    
}
