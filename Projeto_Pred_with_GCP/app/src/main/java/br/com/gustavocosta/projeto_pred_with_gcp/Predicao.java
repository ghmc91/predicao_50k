package br.com.gustavocosta.projeto_pred_with_gcp;

import java.util.ArrayList;
import java.util.List;

public class Predicao {

    private List<Instancia> instances;
    public Predicao() {
        this.instances = new ArrayList<>();
    }

    public void addInstance(Instancia instancia) {
        this.instances.add(instancia);
    }

}

class Instancia {
    public int age;
    public String workclass;
    public String education;
    public int education_num;
    public String marital_status;
    public String occupation;
    public String relationship;
    public String race;
    public String gender;
    public int capital_gain;
    public int capital_loss;
    public int hours_per_week;
    public String native_country;

}
