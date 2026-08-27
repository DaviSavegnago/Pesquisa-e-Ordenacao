public class Comp_BubbleSort {

    static class Pessoa implements Comparable<Pessoa> {

        String nome;
        String curso;

        Pessoa(String nome, String curso) {
            this.nome = nome;
            this.curso = curso;
        }

        @Override
        public int compareTo(Pessoa outra) {
            return this.nome.compareTo(outra.nome);
        }

        @Override
        public String toString() {
            return nome + " - " + curso;
        }
    }

    public static void bubbleSort(Pessoa[] pessoas) {

        for (int i = 0; i < pessoas.length - 1; i++) {

            for (int j = 0; j < pessoas.length - 1 - i; j++) {

                if (pessoas[j].compareTo(pessoas[j + 1]) > 0) {

                    Pessoa temp = pessoas[j];

                    pessoas[j] = pessoas[j + 1];

                    pessoas[j + 1] = temp;
                }
            }
        }
    }

    public static void main(String[] args) {

        Pessoa[] pessoas = {
            new Pessoa("Carlos", "Computação"),
            new Pessoa("Ana", "Administração"),
            new Pessoa("Bruno", "Engenharia"),
            new Pessoa("Pedro", "Computação")
        };

        System.out.println("ANTES:");

        for (Pessoa pessoa : pessoas) {
            System.out.println(pessoa);
        }

        bubbleSort(pessoas);

        System.out.println("\nDEPOIS:");

        for (Pessoa pessoa : pessoas) {
            System.out.println(pessoa);
        }
    }
}