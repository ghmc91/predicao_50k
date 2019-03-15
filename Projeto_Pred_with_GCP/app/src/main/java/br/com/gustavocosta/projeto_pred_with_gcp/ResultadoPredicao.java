package br.com.gustavocosta.projeto_pred_with_gcp;

public class ResultadoPredicao {

    public Resultado[] predictions;
}

class Resultado {
    float[] probabilities;
    int[] class_ids;
    String[] classes;
    float[] logits;
    float[] logistic;
}

