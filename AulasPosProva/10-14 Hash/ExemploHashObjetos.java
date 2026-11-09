class Aluno{

    int matricula;
    String nome;

    
    @Override
    public String toString() {
        return "Aluno [matricula=" + matricula + ", nome=" + nome + "]";
    }


    public Aluno(int matricula, String nome) {
        this.matricula = matricula;
        this.nome = nome;
    }

}

public class ExemploHashObjetos {

    public static void main (String[] args){
        HashSet<Aluno> tabelaHashAluno = new HashSet<>();

        tabelaHashString.add("Alexandre");
        tabelaHashString.add("Hermes");
        tabelaHashString.add("Davi");
        tabelaHashString.add("Alan");
        tabelaHashString.add("Alexandre");
        tabelaHashString.add("Davi");

        for (Aluno a : tabelaHashAluno) {
            System.out.println(a);
        }

    }

}//incompleto