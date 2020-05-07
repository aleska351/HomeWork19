import okhttp3.*;
import okhttp3.logging.HttpLoggingInterceptor;

import java.io.IOException;

public class AsyncDemo {
    public static void main(String[] args) {
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(createLoggingInterceptor()).build();
        Request request = new Request.Builder()
                .url("https://www.google.com/").build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.getMessage();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                long completeResponse = System.nanoTime();
                try (ResponseBody body = response.body()) {
                    System.out.println(body.string());
                }
                long completeOut = System.nanoTime();
            }
        });
    }

    private static HttpLoggingInterceptor createLoggingInterceptor() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor
                (message -> System.out.println(String.format("[%d]: %s", Thread.currentThread().getId(), message)));
        logging.setLevel(HttpLoggingInterceptor.Level.BASIC);
        return logging;
    }
}