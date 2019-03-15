package br.com.gustavocosta.projeto_pred_with_gcp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private static final int LOADER_ACCESS_TOKEN = 1;
    private String mAccessToken;

    private EditText idadeEditText;
    private EditText classeTrabEditText;
    private EditText grauInstrucaoEditText;
    private EditText anosEscolaEditText;
    private EditText estadoCivilEditText;
    private EditText ocupacaoEditText;
    private Spinner spinnerRelacionamento;
    private Spinner spinnerRaca;
    private RadioGroup radioSexGroup;
    private RadioButton radioButton;
    private EditText ganhoCapitaleditText;
    private EditText perdaCapitalEditText;
    private EditText horasTrabalhadasEditText;
    private EditText nacionalidadeEditText;
    private Button btnSend;
    private TextView resultadoTextView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViews();
        obterToken();

        spinnerRelacionamento = findViewById(R.id.spinnerrelacionamento);
        spinnerRelacionamento.setOnItemSelectedListener(this);
        List<String> relacionamento = new ArrayList<String>();
        relacionamento.add("Relationship");
        relacionamento.add("Wife");
        relacionamento.add("Own-child");
        relacionamento.add("Husband");
        relacionamento.add("Not-in-family");
        relacionamento.add("Unmarried");

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, relacionamento);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerRelacionamento.setAdapter(dataAdapter);

        spinnerRaca = findViewById(R.id.spinnerraca);
        spinnerRaca.setOnItemSelectedListener(this);
        List<String> racas = new ArrayList<String>();
        racas.add("Race");
        racas.add("White");
        racas.add("Asian-Pac-Islander");
        racas.add("Amer-Indian-Eskimo");
        racas.add("Black");
        racas.add("Other");

        ArrayAdapter<String> dataAdapter2 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, racas);
        dataAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerRaca.setAdapter(dataAdapter2);

        radioSexGroup = findViewById(R.id.radiogroupgenero);

    }

    private void obterToken() {


        LoaderManager.getInstance(this).initLoader(LOADER_ACCESS_TOKEN, null,
                new LoaderManager.LoaderCallbacks<String>() {

                    @NonNull
                    @Override
                    public Loader<String> onCreateLoader(int i, @Nullable Bundle bundle) {
                        return new AccessTokenLoader(MainActivity.this);
                    }

                    @Override
                    public void onLoadFinished(@NonNull Loader<String> loader, String s) {
                        mAccessToken = s;
                        findViewById(R.id.consultar_button).setEnabled(true);
                        Log.i("Dsa", "Token: " + s);
                    }

                    @Override
                    public void onLoaderReset(@NonNull Loader<String> loader) {

                    }
                });
    }

    private void findViews() {
        idadeEditText = findViewById(R.id.age_editText);
        classeTrabEditText = findViewById(R.id.workclass_editText);
        grauInstrucaoEditText = findViewById(R.id.instruction_edittext);
        anosEscolaEditText = findViewById(R.id.yearsschool_edittext);
        estadoCivilEditText = findViewById(R.id.civilstate_editext);
        ocupacaoEditText = findViewById(R.id.ocupacao_edittext);
        ganhoCapitaleditText = findViewById(R.id.capitalgain_textedit);
        perdaCapitalEditText = findViewById(R.id.losscapital_edittext);
        horasTrabalhadasEditText = findViewById(R.id.hoursworked_edittext);
        nacionalidadeEditText = findViewById(R.id.nacionality_edittext);
        resultadoTextView = findViewById(R.id.resultado_textView);

    }


    public void consulta(final View v) {

        if (idadeEditText.getContext().toString().isEmpty()) {
            Toast.makeText(this, "Informe a idade", Toast.LENGTH_SHORT).show();
            return;
        }
        if (classeTrabEditText.getContext().toString().isEmpty()) {
            Toast.makeText(this, "Informe a classe de trabalho", Toast.LENGTH_SHORT).show();
            return;
        }
        if (grauInstrucaoEditText.getContext().toString().isEmpty()) {
            Toast.makeText(this, "Informe o grau de instrução", Toast.LENGTH_SHORT).show();
            return;
        }
        if (anosEscolaEditText.getContext().toString().isEmpty()) {
            Toast.makeText(this, "Informe quantos anos estudos", Toast.LENGTH_SHORT).show();
            return;
        }
        if (estadoCivilEditText.getContext().toString().isEmpty()) {
            Toast.makeText(this, "Informe seu estado civil", Toast.LENGTH_SHORT).show();
            return;
        }
        if (ocupacaoEditText.getContext().toString().isEmpty()) {
            Toast.makeText(this, "Informe sua ocupação", Toast.LENGTH_SHORT).show();
            return;
        }
        if (ganhoCapitaleditText.getContext().toString().isEmpty()) {
            Toast.makeText(this, "Informe seu ganho capital", Toast.LENGTH_SHORT).show();
            return;
        }
        if (perdaCapitalEditText.getContext().toString().isEmpty()) {
            Toast.makeText(this, "Informe sua perda de capital", Toast.LENGTH_SHORT).show();
            return;
        }
        if (horasTrabalhadasEditText.getContext().toString().isEmpty()) {
            Toast.makeText(this, "Informe a quantidade de horas trabalhadas por mês", Toast.LENGTH_SHORT).show();
            return;
        }
        if (nacionalidadeEditText.getContext().toString().isEmpty()) {
            Toast.makeText(this, "Informe sua nacionalidade", Toast.LENGTH_SHORT).show();
            return;
        }

        Instancia instancia = new Instancia();
        instancia.age = Integer.parseInt(idadeEditText.getText().toString());
        instancia.workclass = " " + classeTrabEditText.getText().toString();
        instancia.education = " " + grauInstrucaoEditText.getText().toString();
        instancia.education_num = Integer.parseInt(anosEscolaEditText.getText().toString());
        instancia.marital_status = " " + estadoCivilEditText.getText().toString();
        instancia.occupation = " " + ocupacaoEditText.getText().toString();
        instancia.relationship = spinnerRelacionamento.getSelectedItem().toString();
        instancia.race = spinnerRaca.getSelectedItem().toString();

        int radioButtonID = radioSexGroup.getCheckedRadioButtonId();
        View rb = radioSexGroup.findViewById(radioButtonID);
        int idx = radioSexGroup.indexOfChild(rb);
        RadioButton r = (RadioButton) radioSexGroup.getChildAt(idx);
        String sexo = r.getText().toString();

        instancia.gender = "Male";
        instancia.capital_gain = Integer.parseInt(ganhoCapitaleditText.getText().toString());
        instancia.capital_loss = Integer.parseInt(perdaCapitalEditText.getText().toString());
        instancia.hours_per_week = Integer.parseInt(horasTrabalhadasEditText.getText().toString());
        instancia.native_country = " " + nacionalidadeEditText.getText().toString();

        Predicao predicao = new Predicao();
        predicao.addInstance(instancia);


        String url = "https://ml.googleapis.com/v1/projects/nljava/models/census/versions/v1:predict?access_token=" + mAccessToken;

        Ion.with(this)
                .load("POST", url)
                .setJsonPojoBody(predicao)
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        if (e != null) {
                            Log.e("Teste", "Erro: " + e.getMessage());
                            return;
                        }
                        Gson gson = new Gson();
                        ResultadoPredicao resultadoPredicoes =
                                gson.fromJson(result.toString(), new TypeToken<ResultadoPredicao>() {
                                }.getType());

                        /*Intent intent = new Intent(MainActivity.this, TelaResultado.class);
                        startActivity(intent);*/

                        int resultadoClasse = Integer.parseInt(resultadoPredicoes.predictions[0].classes[0]);

                        if (resultadoClasse == 1){
                        resultadoTextView.setText("Ganha acima de 50 mil");
                        }else if(resultadoClasse == 0){
                            resultadoTextView.setText("Não ganha acima de 50 mil");
                        }


                        Toast.makeText(
                                MainActivity.this,
                                resultadoPredicoes.predictions[0].classes[0],
                                Toast.LENGTH_SHORT).show();


                    }
                });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String item = parent.getItemAtPosition(position).toString();
        if (item.equals("Race") || item.equals("Relationship")) {
            Toast.makeText(parent.getContext(), "Select other item", Toast.LENGTH_LONG).show();
        } else
            Toast.makeText(parent.getContext(), "Selected:" + item, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void onClick(View vi) {
        int selectedId = radioSexGroup.getCheckedRadioButtonId();
        radioButton = (RadioButton) findViewById(selectedId);
        Toast.makeText(MainActivity.this, radioButton.getText(), Toast.LENGTH_SHORT).show();
    }

}
