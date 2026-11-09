import java.util.HashSet;

public class ExemploHashSimples {

    public static void main (String[] args){
        HashSet<String> tabelaHashString = new HashSet<>();

        tabelaHashString.add("Alexandre");
        tabelaHashString.add("Hermes");
        tabelaHashString.add("Davi");
        tabelaHashString.add("Alan");
        tabelaHashString.add("Alexandre");
        tabelaHashString.add("Davi");

        for (String item : tabelaHashString) {
            System.out.println(item + "->" + item.hashCode());
        }

        System.out.println("\n");

        System.out.println(tabelaHashString.contains("Alan") + "->" + "Alan".hashCode());

    }

}