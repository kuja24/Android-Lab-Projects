package com.example.experiment_5;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.math.BigInteger;
import java.util.Base64;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private Button btnEncrypt;
    private Button btnDecrypt;
    private EditText output;
    private EditText input;

    private String publicKey = "";
    private String privateKey = "";
    private byte[] encodeData = null;

    public String getPublicKey() {
        return publicKey;
    }

    public String getPrivateKey() {
        return privateKey;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnEncrypt = (Button)findViewById(R.id.btn_encrypt);
        btnDecrypt = (Button)findViewById(R.id.btn_decrypt);
        output = (EditText)findViewById(R.id.output);
        input = (EditText)findViewById(R.id.input);

        try {
            Map<String,Object> keyMap = rsa.initKey();
            publicKey = rsa.getPublicKey(keyMap);
            privateKey = rsa.getPrivateKey(keyMap);
        } catch (Exception e) {
            e.printStackTrace();
        }
        btnEncrypt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String publicKey = getPublicKey();
                byte[] rsaData = input.getText().toString().getBytes();
                try {
                    encodeData = rsa.encryptByPublicKey(rsaData,publicKey);
                    //String encodeStr = new BigInteger(1,encodeData).toString();
                    String encodedStringchange = null;
                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                        encodedStringchange = Base64.getEncoder().encodeToString(encodeData);
                    }
                    output.setText(encodedStringchange);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        btnDecrypt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    String textToDecipher = input.getText().toString();
                    byte[] decodedBytes = null;
                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                        decodedBytes = Base64.getDecoder().decode(textToDecipher);
                    }
                    byte[] decodeData = rsa.encryptByPrivateKey(decodedBytes,getPrivateKey());
                    String decodeStr = new String(decodeData);
                    output.setText(decodeStr);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}