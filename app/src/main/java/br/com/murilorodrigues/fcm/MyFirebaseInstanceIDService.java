package br.com.murilorodrigues.fcm;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService {
    private static final String TAG = "logFCM";

    @Override
    public void onTokenRefresh() {

        /* O método onTokenRefresh() é chamado quando o aplicativo receber um token (registration id) que
           representa o identificador do dispositivo e precisa ser enviado ao seu servidor,
           pois com ele é possível enviar uma mensagem para este dispositivo.
         */

        // Pega o InstanceID token.
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.d(TAG, getString(R.string.token) + refreshedToken);

        // Envia o token para o server.
        sendRegistrationToServer(refreshedToken);
    }


    private void sendRegistrationToServer(String token) {
        // TODO: Implement this method to send token to your app server.
    }
}
