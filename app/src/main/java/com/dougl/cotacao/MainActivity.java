package com.dougl.cotacao;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

public class MainActivity extends Activity implements View.OnClickListener {
    Button btDolar;
    Button btEuro;
    Button btPeso;
    Button btBitcoin;

    String url;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btDolar = findViewById(R.id.dolarBtn);
        btDolar.setOnClickListener(this);
        btEuro = findViewById(R.id.euroBt);
        btEuro.setOnClickListener(this);
        btPeso = findViewById(R.id.pesoBt);
        btPeso.setOnClickListener(this);
        btBitcoin = findViewById(R.id.bitcoinBt);
        btBitcoin.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Cotacao cotacao = new Cotacao();
        TextView textView = findViewById(R.id.cotacao);
        TextView textView1 = findViewById(R.id.tipo);
        JSONObject  jsonObject = new JSONObject();
        String jsonString;
        switch (view.getId()){
            case R.id.dolarBtn:
                url = "http://api.promasters.net.br/cotacao/v1/valores?moedas=USD&alt=json";
                textView.setTextColor(getResources().getColor(R.color.colorDolar));
                textView1.setText(R.string.dolar);
                break;
            case R.id.bitcoinBt:
                url = "http://api.promasters.net.br/cotacao/v1/valores?moedas=BTC&alt=json";
                textView.setTextColor(getResources().getColor(R.color.colorBitcoin));
                textView1.setText(R.string.bitcoin);
                break;
            case R.id.euroBt:
                url ="http://api.promasters.net.br/cotacao/v1/valores?moedas=EUR&alt=json";
                textView.setTextColor(getResources().getColor(R.color.colorEuro));
                textView1.setText(R.string.euro);
                break;
            case R.id.pesoBt:
                url = "http://api.promasters.net.br/cotacao/v1/valores?moedas=ARS&alt=json";
                textView.setTextColor(getResources().getColor(R.color.colorPeso));
                textView1.setText(R.string.peso_argentino);
                break;
        }
        String [] urls = {url};
        cotacao.execute(urls);
    }
    public class Cotacao extends AsyncTask<String,Void,String>{

        ProgressDialog progressDialog;
        @Override
        protected void onPreExecute() {
            progressDialog = ProgressDialog.show(MainActivity.this,"Aguarde","Buscando dados");
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... strings) {
                return Network.getCotacao(strings[0]);
        }
        @Override
        protected void onPostExecute(String s) {
            TextView textView = findViewById(R.id.cotacao);
            try {
                JSONObject jsonObject = new JSONObject(s);
                JSONObject object = jsonObject.getJSONObject("valores");
                String tipoMoeda = object.keys().next();
                JSONObject jsonObject1 = object.getJSONObject(tipoMoeda);
                String cotacao = String.valueOf(jsonObject1.getDouble("valor"));
                textView.setText("R$ "+ cotacao);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            progressDialog.dismiss();
        }
    }
}
