package jp.stage.stagelovemaker.base;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;

import jp.stage.stagelovemaker.model.NotificationModel;

/**
 * Created by congnguyen on 8/28/17.
 */

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        if (remoteMessage.getData().size() > 0) {
            createNotification(remoteMessage.getData());
        }
    }

    private void createNotification(Map<String, String> messageBody) {
        NotificationModel model = new NotificationModel();
        model.setBody(messageBody.get("body"));
        model.setSenderId(Integer.parseInt(messageBody.get("sender_id")));
        model.setTitle(messageBody.get("title"));
        model.setType(messageBody.get("type"));

        int id = UserPreferences.getCurrentUserId();
        if(model.getSenderId() != id)
    }
}
