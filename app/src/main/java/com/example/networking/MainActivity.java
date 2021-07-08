package com.example.networking;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public class MainActivity extends AppCompatActivity {


    Button button;
    TextView tv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button= findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateTextView();
            }

            private void updateTextView() {
                //Make the network call here
               // tv=findViewById(R.id.tv);
                NetworkTask networkTask= new NetworkTask();
                networkTask.execute("https://www.cowin.gov.in/home","https://www.google.com","https://www.github.com");
            }
            class NetworkTask extends AsyncTask<String,Void,String>{

                @Override
                protected String doInBackground(String... strings) {
                    String stringurl=strings[0];
                    try {
                        URL url = new URL(stringurl);
                    HttpURLConnection httpURLConnection=(HttpURLConnection) url.openConnection();
                        InputStream inputStream=httpURLConnection.getInputStream();
                          Scanner scanner=new Scanner(inputStream);
//scanner.next(); or we can use delinitar then call next which allow us to entire read stream in one go
                        scanner.useDelimiter("//A");
                        if(scanner.hasNext()){
                            String s= scanner.next();
                            return s;
                        }

                    }
                    catch (MalformedURLException e){
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    return "Failed to load";
                }

                @Override
                protected void onPostExecute(String s) {
                    super.onPostExecute(s);
                    tv=findViewById(R.id.tv);
                    tv.setText(s);

                }
            }
        });
    }
}