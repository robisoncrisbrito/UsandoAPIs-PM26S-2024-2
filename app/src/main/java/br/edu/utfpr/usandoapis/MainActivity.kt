package br.edu.utfpr.usandoapis

import android.os.Bundle
import android.view.View
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.URL

class MainActivity : AppCompatActivity() {

    private lateinit var etLatitude : EditText
    private lateinit var etLongitude : EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        etLatitude = findViewById( R.id.etLatitude )
        etLongitude = findViewById( R.id.etLongitude )
    }

    fun btVerEnderecoOnClick(view: View) {

        Thread (
            Runnable {
                val endereco =
                    "https://maps.googleapis.com/maps/api/geocode/xml?latlng=${etLatitude.text.toString()},${etLongitude.text.toString()}&key=AIzaSyABWyLQa1HmxEQuzy6K1_hRv_zarJFExYk"

                val url = URL(endereco)
                val urlConnection = url.openConnection()//estabelece a conexao com o servidor http

                val inputStream = urlConnection.getInputStream()
                val entrada = BufferedReader(InputStreamReader(inputStream))

                val dados = StringBuilder()

                var linha = entrada.readLine()

                while (linha != null) {
                    dados.append(linha)
                    linha = entrada.readLine()
                }

                val local = dados.substring(
                    dados.indexOf( "<formatted_address>" ) + 19,
                    dados.indexOf( "</formatted_address>")
                )

                runOnUiThread {
                    val dialog = AlertDialog.Builder(this)
                    dialog.setTitle("Endereço: ")
                    dialog.setMessage(local)
                    dialog.setNeutralButton("OK", null)
                    dialog.setCancelable(false)
                    dialog.show()
                }
            } //fecha o Runnable
        ).start()
    }
    fun btVerMapaOnClick(view: View) {

    }
}