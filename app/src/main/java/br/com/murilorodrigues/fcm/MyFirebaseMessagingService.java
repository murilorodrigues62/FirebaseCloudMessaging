package br.com.murilorodrigues.fcm;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    private static final String TAG = "logFCM";
    private static final String ID = "id";
    private static final String NAME = "name";
    private static final String TITLE = "title";
    private static final String BODY = "body";

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

        Intent intent = new Intent(this, MainActivity.class);
        String message;
        String title;

        // Verifica se contém message data
        if (remoteMessage.getData().size() > 0) {

            Map<String, String> data = remoteMessage.getData();
            Log.d(TAG, getString(R.string.message_data) + data);

            // Passa os dados para a Activity poder exibir
            intent.putExtra(ID, data.get(ID));
            intent.putExtra(NAME, data.get(NAME));
        }

        // Verifica se contém message notification
        if (remoteMessage.getNotification() != null) {

            message = remoteMessage.getNotification().getBody();
            title = remoteMessage.getNotification().getTitle();

            Log.d(TAG, getString(R.string.message_notification) + message);

            // Passa os dados para a Activity poder exibir
            intent.putExtra(TITLE, title);
            intent.putExtra(BODY, message);
        } else {

            // Defini titulo e mensagem padrão quando é uma notification só de dados
            message = getString(R.string.default_message);
            title = getString(R.string.default_title);
        }

       // envia notificação
        sendNotification(message, title, intent);
    }

    /**
     * Cria e exibe uma notification contendo os dados recebidos pelo FCM.
     *
     * @param messageBody FCM message body received.
     */
    private void sendNotification(String messageBody, String contentTitle, Intent intent) {

        // Configura a notificação

        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);

        // NotificationCompat utiliza classes de compatibildiade entre as notifications
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this) // Notification simples
                .setDefaults(Notification.DEFAULT_ALL) // configura com som padrão, vibração e acende luz
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(contentTitle)
                .setContentText(messageBody)
                .setAutoCancel(true) // cancela a notification ao clicar nela
                .setContentIntent(pendingIntent);  // intent que será chamada ao clicar na notification

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0, notificationBuilder.build());
    }



}
