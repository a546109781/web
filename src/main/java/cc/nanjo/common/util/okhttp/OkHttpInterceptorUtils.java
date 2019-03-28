package cc.nanjo.common.util.okhttp;

import lombok.extern.slf4j.Slf4j;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;

import java.io.InterruptedIOException;

@Slf4j
class OkHttpInterceptorUtils {

    /**
     * 延迟时间
     */
    private static final int RETRY_INTERVAL = 3000;
    /**
     * 重试三次
     */
    static final int RETRY_COUNT = 3;

    /**
     * 重试
     *
     * @param retryCount 重试次数
     * @return Interceptor
     */
    static Interceptor retryInterceptor(int retryCount) {
        return chain -> {
            Request requestNew = chain.request();
            Response response = chain.proceed(requestNew);
            int retry = 0;
            while (!response.isSuccessful() && retry < retryCount) {
                try {
                    Thread.sleep(RETRY_INTERVAL);
                } catch (final InterruptedException e) {
                    Thread.currentThread().interrupt();
                    throw new InterruptedIOException();
                } finally {
                    response.close();
                }
                retry++;
                log.warn("重试{}次", retry);
                response = chain.proceed(requestNew);
            }
            return response;
        };
    }

    /**
     * 日志拦截器
     *
     * @return Interceptor
     */
    static Interceptor loggingInterceptor() {
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(log::warn);
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        return loggingInterceptor;
    }
}
