
package Compilador;

public class IdentificadorBean {
    String nombre;
    Terminal tipo;
    int valor;

    public IdentificadorBean(String nombre, Terminal tipo, int valor) {
        this.nombre = nombre;
        this.tipo = tipo;
        this.valor = valor;
    }

    public String getNombre(){
        return nombre;
    }

    public Terminal getTipo(){
        return tipo;
    }

    public int getValor(){
        return valor;
    }
    
    public void setValor(int valor){
        this.valor=valor;
    }

    @Override
    public String toString() {
        return "IdentificadorBean{" + "nombre=" + nombre + ", tipo=" + tipo + ", valor=" + valor + '}';
    }
    
    
    
    
}
