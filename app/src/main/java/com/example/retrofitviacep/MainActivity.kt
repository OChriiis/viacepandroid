package com.example.retrofitviacep

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    lateinit var buttonBuscar : Button
    lateinit var editTextCep : EditText
    lateinit var textViewEndereco : TextView

    lateinit var nomeRua : EditText
    lateinit var nomeCidade : EditText
    lateinit var siglaEstado : EditText
    lateinit var btnBuscarCep: Button
    lateinit var tv_rua_cidade_estado : TextView

    lateinit var rvCeps: RecyclerView
    lateinit var cepsAdapter: CepsAdapter



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        buttonBuscar = findViewById(R.id.buttonBuscar)
        textViewEndereco = findViewById(R.id.textViewEndereco)
        editTextCep = findViewById(R.id.editTextCep)

        nomeRua = findViewById(R.id.nomeRua)
        nomeCidade = findViewById(R.id.nomeCidade)
        siglaEstado = findViewById(R.id.siglaEstado)
        btnBuscarCep = findViewById(R.id.btnBuscarCep)

        //Configuração da RecyclerView
        rvCeps = findViewById(R.id.rv_ceps)
        cepsAdapter = CepsAdapter(this)


//        //Determinar o Layout da RV
//        rvCeps.layoutManager = LinearLayoutManager(this,LinearLayoutManager.VERTICAL, false )

        //Determinar o Layout da RV
        rvCeps.layoutManager = GridLayoutManager(this, 2)

        //Definir o adapter da RV
        rvCeps.adapter = cepsAdapter

        buttonBuscar.setOnClickListener{

            //Obter uma instância da conexão com o Backend(criando o cliente http)
            val remote = RetrofitFactory().retrofitService()

            //Criar uma chamada para o endpoint /cep/json
            val call: Call<Cep> =  remote.getCep(editTextCep.text.toString())

            //Executar a chamada para a api
            call.enqueue(object : Callback<Cep> {
                override fun onResponse(call: Call<Cep>, response: Response<Cep>) {
                    val cep = response.body()
                textViewEndereco.text = cep.toString()
                }

                override fun onFailure(call: Call<Cep>, t: Throwable) {
                    Log.i("cep", t.message.toString())
                }
            })

        }

        btnBuscarCep.setOnClickListener{

            val remote = RetrofitFactory().retrofitService()

            val call: Call<List<Cep>> = remote.getCEPByLogradouro(siglaEstado.text.toString(), nomeCidade.text.toString(), nomeRua.text.toString())

            call.enqueue(object : Callback<List<Cep>> {
                override fun onResponse(call: Call<List<Cep>>, response: Response<List<Cep>>) {

                    val ceps = response.body()

                    cepsAdapter.updateListaCep(ceps!!)

                }

                override fun onFailure(call: Call<List<Cep>>, t: Throwable) {
                    Log.i("cepList", t.message.toString())
                }
            })
        }
    }
}