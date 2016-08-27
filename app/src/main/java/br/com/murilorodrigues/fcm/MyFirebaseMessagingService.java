package br.com.murilorodrigues.fcm;

import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    private static final String TAG = "FCM";

    /**
     * Metodo executado quando uma message é recebida
     *
     * @param remoteMessage Objeto que representa a mensagem recebida do Firebase Cloud Messaging.
     */
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        /*
        Basicamente, existem 2 tipos de mensagens: data messages (pares de chave=valor) e notification messages.
        - As DATA MESSAGES são sempre recebidas neste metodo, independente se o app está aberto ou em background.
        - As NOTIFICATION MESSAGES são recebidas neste metodo somente quando o app está aberto,
          caso o app esteja em background o Firebase vai mostrar uma notificação automaticamente.
        - Mensagens que contém notification e data são tratados como notifications messagens.
         */


        // Verifica se contém message data
        if (remoteMessage.getData().size() > 0) {
            Log.d(TAG, "Message data payload: " + remoteMessage.getData());
        }

        // Verifica se conteém message notification
        if (remoteMessage.getNotification() != null) {
            Log.d(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());
        }
    }


}
