package model;

public class ResultadoOrdenacao {

    private long comparacoes;
    private long trocas;
    private long tempo;

    public ResultadoOrdenacao(long comparacoes, long trocas, long tempo) {
        this.comparacoes = comparacoes;
        this.trocas = trocas;
        this.tempo = tempo;
    }

    public long getComparacoes() {
        return comparacoes;
    }

    public long getTrocas() {
        return trocas;
    }

    public long getTempo() {
        return tempo;
    }
}