package jp.stage.stagelovemaker.network;

/**
 * Created by congn on 8/7/2017.
 */

public interface IHttpResponse {
    public void onHttpComplete(String response, int idRequest);

    public void onHttpError(String response, int idRequest, int errorCode);
}
