package com.rimas.explorenepal.mlkit;

import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.ml.common.modeldownload.FirebaseModelDownloadConditions;
import com.google.firebase.ml.naturallanguage.FirebaseNaturalLanguage;
import com.google.firebase.ml.naturallanguage.languageid.FirebaseLanguageIdentification;
import com.google.firebase.ml.naturallanguage.translate.FirebaseTranslateLanguage;
import com.google.firebase.ml.naturallanguage.translate.FirebaseTranslator;
import com.google.firebase.ml.naturallanguage.translate.FirebaseTranslatorOptions;
import com.rimas.explorenepal.R;

import java.util.Arrays;
import java.util.List;

public class TextTranslation extends AppCompatActivity {

    TextView txtDetection, txtLang, txtResult;
    EditText txtWord;
    Button btnTranslate;
    String sourceText, languageSelected;
    Toolbar toolbar;
    Spinner spinner;
    int code;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_translation);
        toolbar= findViewById(R.id.toolbar);
        toolbar.setTitle("Text Translation");
        setSupportActionBar(toolbar);

        txtDetection=findViewById(R.id.txtDetection);
        spinner=findViewById(R.id.txtLang);
        txtResult= findViewById(R.id.txtResult);

        txtWord=findViewById(R.id.txtWord);

        btnTranslate=findViewById(R.id.btnTranslate);

        List<String> list = Arrays.asList("Select language...","English","Hindi", "Arabic","French","German","Urdu","Swedish","Bengali","Chinese","Russian", "Portuguese"
        ,"Spanish","Korean","Malay","Italian","Thai","Telugu","Tamil","Japanese");

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String >(this,android.R.layout.simple_spinner_item, list);

        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(arrayAdapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                spinner.setSelection(position);

                languageSelected=spinner.getSelectedItem().toString();

                languageSelection(languageSelected);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        btnTranslate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtResult.setText("");
                txtWord.onEditorAction(EditorInfo.IME_ACTION_DONE);
//
                if (txtWord.getText().length()<=0){
                    txtWord.onEditorAction(EditorInfo.IME_ACTION_DONE);

                    Toast.makeText(TextTranslation.this, "Please write something to translate.", Toast.LENGTH_LONG).show();
                }
                else {
                    txtWord.onEditorAction(EditorInfo.IME_ACTION_DONE);
                    if (languageSelected.equals("Select language...")) {
                        Toast.makeText(TextTranslation.this, "Please select some language and try again.", Toast.LENGTH_SHORT).show();
                    } else {
                        txtWord.onEditorAction(EditorInfo.IME_ACTION_DONE);
                        recognizeText();
                    }
                }
            }
        });
    }

    private void languageSelection(String languageSelected) {

        switch (languageSelected){

            case "Hindi":
                code= FirebaseTranslateLanguage.HI;
                break;
            case "Arabic":
                code= FirebaseTranslateLanguage.AR;
                break;
            case "Bengali":
                code= FirebaseTranslateLanguage.BN;
                break;
            case "German":
                code= FirebaseTranslateLanguage.DE;
                break;
            case "English":
                code= FirebaseTranslateLanguage.EN;
                break;
            case "Spanish":
                code= FirebaseTranslateLanguage.ES;
                break;
            case "French":
                code= FirebaseTranslateLanguage.FR;
                break;
            case "Italian":
                code= FirebaseTranslateLanguage.IT;
                break;
            case "Japanese":
                code= FirebaseTranslateLanguage.JA;
                break;
            case "Korean":
                code= FirebaseTranslateLanguage.KO;
                break;
            case "Malay":
                code= FirebaseTranslateLanguage.MS;
                break;
            case "Portuguese":
                code= FirebaseTranslateLanguage.PT;
                break;
            case "Russian":
                code= FirebaseTranslateLanguage.RU;
                break;
            case "Swedish":
                code= FirebaseTranslateLanguage.SV;
                break;
            case "Tamil":
                code= FirebaseTranslateLanguage.TA;
                break;
            case "Telugu":
                code= FirebaseTranslateLanguage.TE;
                break;
            case "Thai":
                code= FirebaseTranslateLanguage.TH;
                break;
            case "Urdu":
                code= FirebaseTranslateLanguage.UR;
                break;
            case "Chinese":
                code= FirebaseTranslateLanguage.ZH;
                break;
            default:
                code=0;

        }
    }

    private void recognizeText() {

        sourceText= txtWord.getText().toString();

        FirebaseLanguageIdentification identification= FirebaseNaturalLanguage.getInstance().getLanguageIdentification();

        txtDetection.setText("Detecting...");

        identification.identifyLanguage(sourceText).addOnSuccessListener(new OnSuccessListener<String>() {
            @Override
            public void onSuccess(String s) {
                if (s.equals("und")){
                    txtDetection.setText("");
                    Toast.makeText(TextTranslation.this, "Sorry, Unable to detect the language for given words.", Toast.LENGTH_SHORT).show();
                }

                else{

                    getLanguageCode(s);
                }
            }
        });

    }

    private void getLanguageCode(String language) {

        int languageCode;

        switch (language){

            case "hi":
                languageCode= FirebaseTranslateLanguage.HI;
                txtDetection.setText("Hindi");
                break;
            case "ar":
                languageCode= FirebaseTranslateLanguage.AR;
                txtDetection.setText("Arabic");
                break;
            case "bn":
                languageCode= FirebaseTranslateLanguage.BN;
                txtDetection.setText("Bengali");
                break;
            case "de":
                languageCode= FirebaseTranslateLanguage.DE;
                txtDetection.setText("German");
                break;
            case "en":
                languageCode= FirebaseTranslateLanguage.EN;
                txtDetection.setText("English");
                break;
            case "es":
                languageCode= FirebaseTranslateLanguage.ES;
                txtDetection.setText("Spanish");
                break;
            case "fr":
                languageCode= FirebaseTranslateLanguage.FR;
                txtDetection.setText("French");
                break;
            case "it":
                languageCode= FirebaseTranslateLanguage.IT;
                txtDetection.setText("Italian");
                break;
            case "ja":
                languageCode= FirebaseTranslateLanguage.JA;
                txtDetection.setText("Japanese");
                break;
            case "ko":
                languageCode= FirebaseTranslateLanguage.KO;
                txtDetection.setText("Korean");
                break;
            case "ms":
                languageCode= FirebaseTranslateLanguage.MS;
                txtDetection.setText("Malay");
                break;
            case "pt":
                languageCode= FirebaseTranslateLanguage.PT;
                txtDetection.setText("Portuguese");
                break;
            case "ru":
                languageCode= FirebaseTranslateLanguage.RU;
                txtDetection.setText("Russian");
                break;
            case "sv":
                languageCode= FirebaseTranslateLanguage.SV;
                txtDetection.setText("Swedish");
                break;
            case "ta":
                languageCode= FirebaseTranslateLanguage.TA;
                txtDetection.setText("Tamil");
                break;
            case "te":
                languageCode= FirebaseTranslateLanguage.TE;
                txtDetection.setText("Telugu");
                break;
            case "th":
                languageCode= FirebaseTranslateLanguage.TH;
                txtDetection.setText("Thai");
                break;
            case "ur":
                languageCode= FirebaseTranslateLanguage.UR;
                txtDetection.setText("Urdu");
                break;
            case "zh":
                languageCode= FirebaseTranslateLanguage.ZH;
                txtDetection.setText("Chinese");
                break;
                default:
                    languageCode=0;
                    txtDetection.setText("Not Detected");

        }

        translateLanguage(languageCode);
    }

    private void translateLanguage(int languageCode) {

        txtResult.setText("Translating...");

        FirebaseTranslatorOptions options= new FirebaseTranslatorOptions.Builder()
                .setSourceLanguage(languageCode)
                .setTargetLanguage(code)
                .build();

        FirebaseTranslator translator= FirebaseNaturalLanguage.getInstance().getTranslator(options);

        FirebaseModelDownloadConditions conditions= new FirebaseModelDownloadConditions.Builder().build();


        translator.downloadModelIfNeeded(conditions).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                translator.translate(sourceText).addOnSuccessListener(new OnSuccessListener<String>() {
                    @Override
                    public void onSuccess(String s) {
                        txtResult.setText(s);
                    }
                });
            }
        });

    }
}
