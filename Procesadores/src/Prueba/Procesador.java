package Prueba;

import java.io.FileInputStream;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PushbackInputStream;

import es.upm.aedlib.Pair;
import es.upm.aedlib.map.HashTableMap;
import es.upm.aedlib.map.Map;


public class Procesador {
    
    TablaSimbolos[] tabla_global;
    int contador_tablaS;
    
    int contador;
    FileWriter fichero_ts;
    FileWriter fichero_tokens;
    PushbackInputStream fichero_entrada;

    final int SUMA = 0;
    final int RESTA = 1;
    final int RESTO = 2;
    final int PAR_AB = 3;
    final int PAR_CER = 4;
    final int PYC = 5;
    final int COMA = 6;
    final int ENTRE = 7;
    final int EOL = 8;
    final int IGUAL = 9;
    final int EXCL = 10;
    final int COMILLA_SIMPLE = 11;
    final int DIGITO = 12;
    final int LETRA = 13;
    final int PUNTO = 14;
    final int BARRA_BAJA = 15;
    final int DOS_PTOS = 16;
    final int CORCH_AB = 17;
    final int CORCH_CER = 18;
    final int EOF = 19;
    final int DEL = 20;

    private Map<String, Integer> palabra_reservada = new HashTableMap<String, Integer>(); 

  /*  private Integer[][] matriz_estado = {
        {26, 10, 12, 13, 19, 15, 16, 1, 0, 3, 20, 4, 6, 9, null, null, 31, 29, 30, 25, 0},
        {17, 17, 17, 17, 17, 17, 17, 2, 17, 17, 17, 17, 17, 17, 17, 17, 17, 17, 17, 17, 17},
        {2, 2, 2, 2, 2, 2, 2, 2, 0, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2},
        {19, 19, 19, 19, 19, 19, 19, 19, 19, 18, 19, 19, 19, 19, 19, 19, 19, 19, 19, 19, 19},
        {21, 21, 21, 21, 21, 21, 21, 5, null, 21, 21, 21, 21, 21, 21, 21, 21, 21, 21, null, 21},
        {null, null, null, null, null, null, null, 4, null, null, null, 4, null, null, null, null, null, null, null, null, null},
        {null, null, null, null, null, 22, 22, null, 22, null, null, null, 6, null, 7, null, null, null, null, null, 22, 22},
        {null, null, null, null, null, null, null, null, null, null, null, null, 8, null, null, null, null, null, null, null, null},
        {null, null, null, null, null, 23, 23, null, 23, null, null, null, 8, null, null, null, null, null, null, 23, 23}, 
        {24, 24, 24, 24, 24, 24, 24, 24, 24, 24, 24, 24, 9, 9, 24, 9, 24, 24, 24, 24, 24, 24}, 
        {27, 27, 27, 27, 27, 27, 27, 27, 27, 28, 27, 27, 27, 27, 27, 27, 27, 27, 27, 27, 27}
    };*/
    private Integer[][] matriz_estado = {
//cambio todos los estados finales a 0 para que no me de error 
            /* 0 */ { 0, 10,  0,  0,  0,  0,  0,  1,  0,  3,  0,  4,  6,  9,null,  9,  0,  0,  0,  0,  0},
            /* 1 */ {  0, 0,  0,  0,  0,  0,  0,  2,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0},
            /* 2 */ {  2, 2,  2,  2,  2,  2,  2,  2,  0,  2,  2,  2,  2,  2,  2,  2,  2,  2,  2,  2,  2},
            /* 3 */ {  0, 0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0},
            /* 4 */ {  4, 4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  5,  4,  4,  4,  4,  4,  4,  4,null,  4},
            /* 5 */ {  0, 0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0},
            /* 6 */ {  0, 0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  6,  0,  7,  0,  0,  0,  0,  0,  0},
            /* 7 */ {null,null,null,null,null,null,null,null,null,null,null,null, 8,null,null,null,null,null,null,null,null},
            /* 8 */ {  0, 0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  8,  0,  0,  0,  0,  0,  0,  0,  0},
            /* 9 */ {  0, 0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  9,  9,  0,  9,  0,  0,  0,  0,  0},
            /*10 */ {  0, 0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0}
        };

    private String[][] matriz_accion = {
        {"g3", "l", "g5", "g1", "g2", "g9", "g6", "l", "e", "l", "g8", "l", "a", "c", null, null, "g20", "g18", "g19", "g7", "l"},
        {"g10", "g10", "g10", "g10", "g10", "g10", "g10", "l", "g10", "g10", "g10", "g10", "g10", "g10", "g10", "g10", "g10", "g10", "g10", "g10", "g10"},
        {"l", "l", "l", "l", "l", "l", "l", "l", "e", "l", "l", "l", "l", "l", "l", "l", "l", "l", "l", "l", "l"},
        {"g15", "g15", "g15", "g15", "g15", "g15", "g15", "g15", "g15", "g16", "g15", "g15", "g15", "g15", "g15", "g15", "g15", "g15", "g15", "g15", "g15"},
        {"c", "c", "c", "c", "c", "c", "c", "l", null, "c", "c", "g17", "c", "c", "c", "c", "c", "c", "c", null, "c"},
        {null, null, null, null, null, null, null, "l", null, null, null, "l", null, null, null, null, null, null, null, null, null},
        {null, null, null, null, null, "g12", "g12", null, "g12", null, null, null, "b", null, "l", null, null, null, null, "g12", "g12"},
        {null, null, null, null, null, null, null, null, null, null, null, null, "d", null, null, null, null, null, null, null, null},
        {null, null, null, null, null, "g13", "g13", null, "g13", null, null, null, "d", null, null, null, null, null, null, "g13", "g13"},
        {"g14", "g14", "g14", "g14", "g14", "g14", "g14", "g14", "g14", "g14", "g14", "g14", "c", "c", "g14", "c", "g14", "g14", "g14", "g14", "g14"},
        {"g4", "g4", "g4", "g4", "g4", "g4", "g4", "g4", "g4", "g11", "g4", "g4", "g4", "g4", "g4", "g4", "g4", "g4", "g4", "g4", "g4"}
    };

    public Procesador() throws IOException {
        palabra_reservada.put("boolean", 0);
        palabra_reservada.put("break", 1);
        palabra_reservada.put("case", 2);
        palabra_reservada.put("echo", 3);
        palabra_reservada.put("float", 4);
        palabra_reservada.put("function", 5);
        palabra_reservada.put("if", 6);
        palabra_reservada.put("int", 7);
        palabra_reservada.put("let", 8);
        palabra_reservada.put("prompt", 9);
        palabra_reservada.put("return", 10);
        palabra_reservada.put("string", 11);
        palabra_reservada.put("switch", 12);
        palabra_reservada.put("void", 13);
        palabra_reservada.put("false", 14);
        palabra_reservada.put("true", 15);

        tabla_global = new TablaSimbolos[1000];
        contador = 0;
        contador_tablaS = 0;
        fichero_ts = new FileWriter("ficheroTS.txt");
        fichero_tokens = new FileWriter("ficheroTokens.txt");
     // Encabezado de la Tabla de Símbolos Principal
        fichero_ts.write("TABLA # 1:\n");
        fichero_ts.flush();
    }

    public Pair<String, Object> analizador_lexico() throws IOException {
        int r = fichero_entrada.read();
        if (r == -1) return gentoken("EOF", null);

        char c = (char) r;
        double num = 0;
        int dec = 0;
        Integer estado = 0;
        String lex = "";

        while (estado != null && estado <= 10) {
            int clase = claseCaracter(c);
            if (clase == -1) {
            	gestionarError(contador + 1, "léxico", 
                        "El carácter '" + c + "' no es válido en el lenguaje.");
                    return null; // O continuar recuperándose según tu estrategia de pánico
            }

            String accion = matriz_accion[estado][clase];
            estado = matriz_estado[estado][clase];

            if (estado == null && accion == null) {
                throw new IllegalArgumentException("Transición o acción nula no válida para el carácter: '" + c + "'");
            }

            Pair<String, Object> token = null;

            switch (accion) {
                case "a":
                    num = Character.getNumericValue(c);
                    r = fichero_entrada.read();
                    if (r == -1) return gentoken("EOF", null);
                    c = (char) r;
                    break;
                case "b":
                    num = num * 10 + Character.getNumericValue(c);
                    r = fichero_entrada.read();
                    if (r == -1) return gentoken("EOF", null);
                    c = (char) r;
                    break;
                case "c":
                    lex = lex + c;
                    r = fichero_entrada.read();
                    if (r == -1) return gentoken("EOF", null);
                    c = (char) r;
                    break;
                case "d":
                    dec++;
                    num = num * 10 + Character.getNumericValue(c);
                    r = fichero_entrada.read();
                    if (r == -1) return gentoken("EOF", null);
                    c = (char) r;
                    break;
                case "e":
                    contador++;
                    r = fichero_entrada.read();
                    if (r == -1) return gentoken("EOF", null);
                    c = (char) r;
                    break;
                case "g1": token = gentoken("PARABRIR", null); break;
                case "g2": token = gentoken("PARCERRAR", null); break;
                case "g3": token = gentoken("SUMA", null); break;
                case "g4":
                    fichero_entrada.unread(c);
                    token = gentoken("RESTA", null);
                    break;
                case "g5": token = gentoken("MODULO", null); break;
                case "g6": token = gentoken("COMA", null); break;
                case "g7": token = gentoken("EOF", null); break;
                case "g8": token = gentoken("DESIGUAL", null); break;
                case "g9": token = gentoken("PYC", null); break;
                case "g10":
                    fichero_entrada.unread(c);
                    token = gentoken("DIV", null);
                    break;
                case "g11": token = gentoken("ASIGDEC", null); break;
                case "g12":
                    fichero_entrada.unread(c);
                    token = gentoken("ENT", "" + (int) num);
                    break;
                case "g13":
                    fichero_entrada.unread(c);
                    num = num * Math.pow(10, -dec);
                    token = gentoken("REAL", "" + num);
                    break;
                case "g14":
                    fichero_entrada.unread(c);
                    Integer p = buscaPR(lex);
                    if (p != null) {
                        token = gentoken("PALRES", "" + p);
                    } else {
                        Integer pos = buscaTS(lex);
                        if (pos == null) {
                            pos = insertarTS(lex);
                        }
                        token = gentoken("ID", ""+pos);
                    }
                    break;
                case "g15":
                    fichero_entrada.unread(c);
                    token = gentoken("ASIG", null);
                    break;
                case "g16": token = gentoken("IG", null); break;
                case "g17": token = gentoken("CAD", lex); break;
                case "g18": token = gentoken("CORCHABRIR", null); break;
                case "g19": token = gentoken("CORCHCERRAR", null); break;
                case "g20": token = gentoken("DOSPTOS", null); break;
                case "l":
                    r = fichero_entrada.read();
                    if (r == -1) return gentoken("EOF", null);
                    c = (char) r;
                    break;
                default:
                    throw new IllegalArgumentException("Acción no válida: " + accion);
            }

            // Si la acción ha generado un token, lo retornamos inmediatamente
            if (token != null) {
                return token;
            }
        }
        return null;
    }

    public int claseCaracter(char c) {
        if (Character.isDigit(c)) return DIGITO;
        if (c == '.') return PUNTO;
        if (c == '+') return SUMA;
        if (c == '-') return RESTA;
        if (c == '%') return RESTO;
        if (c == '(') return PAR_AB;
        if (c == ')') return PAR_CER;
        if (c == ';') return PYC;
        if (c == ',') return COMA;
        if (c == '/') return ENTRE;
        if (c == '=') return IGUAL;
        if (c == '!') return EXCL;
        if (c == '\'') return COMILLA_SIMPLE;
        if (c == ':') return DOS_PTOS;
        if (c == '_') return BARRA_BAJA;
        if (c == '\n' || c == '\r') return EOL;
        if (Character.isLetter(c)) return LETRA;
        if (Character.isWhitespace(c)) return DEL;
        if (c == ':') return DOS_PTOS;
        if (c == '{') return CORCH_AB;
        if (c == '}') return CORCH_CER;

        return -1;
    }

    public Pair<String, Object> gentoken(String s, String valor) throws IOException {
        Pair<String, Object> token = new Pair<String, Object>(s, valor);
        fichero_tokens.write("< " + s + ", " + valor + " >\n");
        return token;
    }

    public Integer buscaPR(String lex) {
        if (palabra_reservada.containsKey(lex)) {
            return palabra_reservada.get(lex);
        }
        return null;
    }

    public Integer buscaTS(String lex) {
        for (int i = 0; i < contador_tablaS; i++) {
            if (tabla_global[i].getNombre().equals(lex)) {
                return i;
            }
        }
        return null;
    }

    public Integer insertarTS(String lex) {
        TablaSimbolos nuevo = new TablaSimbolos(lex, null, null, 0);
        Integer result = contador_tablaS;
        tabla_global[contador_tablaS] = nuevo;
        contador_tablaS++;
     // Guardar directamente la nueva entrada en ficheroTS.txt con el formato solicitado
        try {
            fichero_ts.write("* Lexema : '" + lex + "'\n");
            fichero_ts.flush();
        } catch (IOException e) {
            System.err.println("Error al escribir en ficheroTS: " + e.getMessage());
        }
        return result;
    }
    
    
    public void gestionarError(int linea, String tipo, String descripcion) {
        String mensaje = String.format("[Línea %d] Error %s: %s", linea, tipo, descripcion);
        System.err.println(mensaje);
        // Opcional: escribir también en un archivo de errores
    }
    
    

    public static void main(String[] args) throws IOException {


        Procesador nuevo = new Procesador();
        nuevo.fichero_entrada = new PushbackInputStream(new FileInputStream(args[0]), 1);

       

        Pair<String, Object> token;

        while (true) {
            token = nuevo.analizador_lexico();

        //    if (token == null) {
        //        System.out.println("ERROR: analizador_lexico() devuelve null");
        //        break;
        //    }

            System.out.println(token);

            if (token.getLeft().equals("EOF")) {
                break;
           }
        }

        nuevo.fichero_entrada.close();
        nuevo.fichero_tokens.close();
        nuevo.fichero_ts.flush();
        nuevo.fichero_ts.close();

        System.out.println("terminado");
   }
}

