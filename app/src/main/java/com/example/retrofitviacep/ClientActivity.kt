package com.example.retrofitviacep

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

lateinit var edNome : EditText
lateinit var edEmail : EditText
lateinit var edTelefone : EditText
lateinit var btnSalvar : Button

class ClientActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_client)

        edNome = findViewById(R.id.edNome)
        edEmail = findViewById(R.id.edEmail)
        edTelefone = findViewById(R.id.edTelefone)
        btnSalvar = findViewById(R.id.btnSalvar)

        btnSalvar.setOnClickListener{

            val cliente = Cliente(0, edNome.text.toString(), edEmail.text.toString(), edTelefone.text.toString())

            //Obter uma instância da conexão com o Backend(criando o cliente http)
            val remote = RetrofitFactory().retrofitService()

            //Criar uma chamadan POST para o endpoint /cep/json
            val call: Call<Cliente> = remote.gravarCliente(cliente)

            call.enqueue(object : Callback<Cliente>{
                override fun onFailure(call: Call<Cliente>, t: Throwable) {
                    Toast.makeText(applicationContext, t.message, Toast.LENGTH_LONG).show()
                }

                override fun onResponse(call: Call<Cliente>, response: Response<Cliente>) {
                    Toast.makeText(applicationContext, "Sucesso", Toast.LENGTH_LONG).show()}
            })

        }
    }
}