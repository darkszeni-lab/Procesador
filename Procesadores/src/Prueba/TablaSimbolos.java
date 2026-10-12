package Prueba;

public class TablaSimbolos {
	
	    private String nombre;
	    private String tipo;
	    private Object valor;
	    private int alcance;

	    public TablaSimbolos(String nombre, String tipo, Object valor, int alcance) {
	        this.nombre = nombre;
	        this.tipo = tipo;
	        this.valor = valor;
	        this.alcance = alcance;
	    }

	    // Getters y setters
	    public String getNombre() { return nombre; }
	    public void setNombre(String nombre) { this.nombre = nombre; }

	    public String getTipo() { return tipo; }
	    public void setTipo(String tipo) { this.tipo = tipo; }

	    public Object getValor() { return valor; }
	    public void setValor(Object valor) { this.valor = valor; }

	    public int getAlcance() { return alcance; }
	    public void setAlcance(int alcance) { this.alcance = alcance; }

	    @Override
	    public String toString() {
	        return "Simbolo{" +
	                "nombre='" + nombre + '\'' +
	                ", tipo='" + tipo + '\'' +
	                ", valor=" + valor +
	                ", alcance=" + alcance +
	                '}';
	    }
	


}

