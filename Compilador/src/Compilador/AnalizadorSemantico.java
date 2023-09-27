package Compilador;

import java.util.ArrayList;

public class AnalizadorSemantico {
    private IndicadorDeErrores inderr;
    private ArrayList <IdentificadorBean> tablaBean;

    public AnalizadorSemantico() {
        this.inderr = inderr;
        this.tablaBean = new ArrayList<>();
    }

    //creo el identificador y lo inserto en la tabla
    public void setIdentificador(String nombre, Terminal tipo, int valor){ 
       //ACA COLOCAR EL METODO DE VALIDACION
       IdentificadorBean iB= new IdentificadorBean(nombre, tipo,valor);
       tablaBean.add(iB);
    }

   public void mostrarTabla(){
       
       for(IdentificadorBean iB: tablaBean){
           System.out.println("-"+iB);
           iB.toString();
       }
   }
}

