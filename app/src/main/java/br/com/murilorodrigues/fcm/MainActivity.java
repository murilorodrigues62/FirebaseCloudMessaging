package br.com.murilorodrigues.fcm;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.google.firebase.iid.FirebaseInstanceId;

public class MainActivity extends AppCompatActivity {
    private TextView textId;
    private TextView textName;
    private TextView textTitle;
    private TextView textBody;
    private TextView textToken;
    private static final String TAG = "logFCM" ;
    private static final String ID = "id";
    private static final String NAME = "name";
    private static final String TITLE = "title";
    private static final String BODY = "body";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        textId = (TextView) findViewById(R.id.textId);
        textName = (TextView) findViewById(R.id.textName);
        textTitle = (TextView) findViewById(R.id.textTitle);
        textBody = (TextView) findViewById(R.id.textBody);
        textToken = (TextView) findViewById(R.id.textToken);

        // Verifica se tem dados salvos ou extras e exibe na view
        if (savedInstanceState != null){

            readData(savedInstanceState);

        }  else if (getIntent().getExtras() != null) {

            readData(getIntent().getExtras());
        }

        textToken.setText(FirebaseInstanceId.getInstance().getToken());
        Log.d(TAG,R.string.token + String.valueOf(textToken.getText()));
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        // Salva os dados
        outState.putString(ID, String.valueOf(textId.getText()));
        outState.putString(NAME, String.valueOf(textName.getText()));
        outState.putString(TITLE, String.valueOf(textTitle.getText()));
        outState.putString(BODY, String.valueOf(textBody.getText()));
    }

    /**
     * Passa os dados da notification para os componentes view
     *
     * @param bundle contém os dados da notification
     */
    private void readData(Bundle bundle) {

        if (bundle.getString(ID) != null){
            textId.setText(bundle.getString(ID));
        } else {
            textId.setText("");
        }

        if (bundle.getString(NAME) != null){
            textName.setText(bundle.getString(NAME));
        } else {
            textName.setText("");
        }

        if (bundle.getString(TITLE) != null){
            textTitle.setText(bundle.getString(TITLE));
        } else {
            textTitle.setText("");
        }

        if (bundle.getString(BODY) != null){
            textBody.setText(bundle.getString(BODY));
        } else {
            textBody.setText("");
        }
    }

    @Override
    protected void onNewIntent(final Intent intent) {
        super.onNewIntent(intent);

        // Evento acionado ao clicar na notification se a activity já estava aberta
        // Isso se a activity estiver configurada como singleTop

        // Criamos uma thread para conseguir alterar as informações UI da activity
        AsyncTask<Void, Void, Void> task = new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                return null;
            }
            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                readData(intent.getExtras());
            }
        };
        task.execute();
    }

    // TODO: 31/08/16 Verificar como tratar Notifications geradas automaticamente pelo FCM
}
