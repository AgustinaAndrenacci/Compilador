package Compilador;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Main {

    public static void main(String[] args) throws FileNotFoundException, IOException {
        String cadena= "C:\\BIEN-00.PL0";
        IndicadorDeErrores indErr = new IndicadorDeErrores();
        AnalizadorLexico alex = new AnalizadorLexico (cadena);
        AnalizadorSemantico aSem = new AnalizadorSemantico();
        AnalizadorSintactico asint= new AnalizadorSintactico(alex, indErr, aSem);
        
        asint.analizar();
        System.out.println("Lei el archivo: "+cadena);
    }
}    
