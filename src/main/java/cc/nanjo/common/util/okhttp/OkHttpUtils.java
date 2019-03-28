package cc.nanjo.common.util.okhttp;

import lombok.extern.slf4j.Slf4j;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

@Slf4j
public class OkHttpUtils {

    private static OkHttpClient okHttpClient;

    /**
     * 延迟时间
     */
    // private static final int retryInterval = 3000;
    /**
     * 请求方式，常用四种方式，推荐遵循RESTful风格，使用合适的请求方式
     */
    private static final String GET = "GET";
    private static final String POST = "POST";
    private static final String PUT = "PUT";
    private static final String DELETE = "DELETE";

    /**
     * Content-Type类型
     */
    private static final MediaType FORM_URLENCODED = MediaType.parse("application/x-www-form-urlencoded; charset=utf-8");
    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    private OkHttpUtils() {
    }

    /**
     * 构建
     *
     * @param debug 是否调试模式
     * @return 当前类对象
     */
    public static OkHttpUtils builder(boolean debug) {
        builderOkHttp(debug, 0);
        return new OkHttpUtils();
    }

    public static OkHttpUtils builder() {
        builderOkHttp(false, 0);
        return new OkHttpUtils();
    }

    /**
     * 重试请求
     *
     * @param debug      是否调试
     * @param retryCount 重试次数
     * @return OkHttpUtils
     */
    public static OkHttpUtils builder(boolean debug, int retryCount) {
        builderOkHttp(debug, retryCount);
        return new OkHttpUtils();
    }

    /**
     * 构建OkHttp，忽略所有https（不推荐），正确的做法是需要使用证书、HandshakeCertificates等做验证。
     */
    private static void builderOkHttp(boolean debug, int retryCount) {
        if (okHttpClient != null) {
            return;
        }
        OkHttpClient.Builder builder = new OkHttpClient().newBuilder().sslSocketFactory(SslSocketClient.getSSLSocketFactory())
                // 对所有的主机通过，不推荐
                .hostnameVerifier((s, sslSession) -> true);
        if (retryCount > 0) {
            builder.addInterceptor(OkHttpInterceptorUtils.retryInterceptor(retryCount));
        }
        if (debug) {
            builder.addInterceptor(OkHttpInterceptorUtils.loggingInterceptor());
        }
        okHttpClient = builder.build();
    }

    /**
     * 发送的参数是JSON，即ContentType为application/json
     *
     * @param url  地址
     * @param json 请求参数
     * @return 响应结果
     */
    public String post(String url, String json) {
        return execute(OkHttpUtils.POST, url, OkHttpUtils.JSON, null, json);
    }

    /**
     * 自定义header
     *
     * @param url     地址
     * @param headers 请求头
     * @param json    请求体
     * @return 响应结果
     */
    public String post(String url, Map<String, String> headers, String json) {
        return execute(OkHttpUtils.POST, url, OkHttpUtils.JSON, headers, json);
    }

    /**
     * 发送的参数是Key=Value，即ContentType为application/x-www-form-urlencoded
     *
     * @param url 地址
     * @param map 请求参数
     * @return 响应结果
     */
    public String post(String url, Map<String, String> map) {
        return execute(OkHttpUtils.POST, url, OkHttpUtils.FORM_URLENCODED, null, params(map));
    }

    /**
     * 自定义header
     *
     * @param url     地址
     * @param headers 请求头
     * @param map     键值对参数，application/x-www-form-urlencoded
     * @return 响应结果
     */
    public String post(String url, Map<String, String> headers, Map<String, String> map) {
        return execute(OkHttpUtils.POST, url, OkHttpUtils.FORM_URLENCODED, headers, params(map));
    }

    /**
     * GET请求
     *
     * @param url 地址
     * @return 响应结果
     */
    public String get(String url) {
        return execute(OkHttpUtils.GET, url, null, null, null);
    }

    /**
     * 自定义header
     *
     * @param url     地址
     * @param headers 请求头
     * @return 响应结果
     */
    public String get(String url, Map<String, String> headers) {
        return execute(OkHttpUtils.GET, url, null, headers, null);
    }

    /**
     * 异步post请求
     *
     * @param url      地址
     * @param json     JSON数据
     * @param callback 回调
     */
    public void postAsync(String url, String json, Callback callback) {
        asyncResponseBody(doMethod(OkHttpUtils.POST, url, OkHttpUtils.JSON, null, json), callback);
    }

    /**
     * 异步post请求，键值对参数
     *
     * @param url      地址
     * @param map      键值对参数
     * @param callback 回调
     */
    public void postAsync(String url, Map<String, String> map, Callback callback) {
        asyncResponseBody(doMethod(OkHttpUtils.POST, url, OkHttpUtils.FORM_URLENCODED, null, params(map)), callback);
    }

    /**
     * 异步get请求
     *
     * @param url      地址
     * @param callback 回调
     */
    public void getAsync(String url, Callback callback) {
        asyncResponseBody(doMethod(OkHttpUtils.GET, url, null, null, null), callback);
    }

    /**
     * 直接使用ResponseBody可以获得更底层的流数据，但获取String请使用{@link OkHttpUtils#get(String, Map)}、
     * {@link OkHttpUtils#post(String, String)}等方法。
     * <p>
     * 注意ResponseBody需要关闭。
     *
     * @param url
     * @return
     * @throws IOException
     */
    public ResponseBody body(String url) throws IOException {
        Request request = doMethod(OkHttpUtils.GET, url, null, null, null);
        return responseBody(request);
    }

    /**
     * 执行请求
     *
     * @param method      请求方式
     * @param url         地址
     * @param contentType Content-Type
     * @param json        JSON串
     * @return 响应结果
     */
    private String execute(String method, String url, MediaType contentType, Map<String, String> headers, String json) {
        Request request = doMethod(method, url, contentType, headers, json);
        try (ResponseBody responseBody = responseBody(request)) {
            return responseBody == null ? null : responseBody.string();
        } catch (IOException e) {
            log.error("OkHttpUtil#execute异常", e);
            return null;
        }
    }

    private Request doMethod(String method, String url, MediaType contentType, Map<String, String> headers, String json) {
        Request.Builder builder = new Request.Builder().url(url);

        // 自定义header
        if (headers != null) {
            Set<String> keySet = headers.keySet();
            for (String key : keySet) {
                String value = headers.get(key);
                builder.header(key, value);
            }
        }

        Request request;
        switch (method) {
            case GET:
                request = builder.build();
                break;
            case POST:
                request = builder.post(RequestBody.create(contentType, json)).build();
                break;
            case PUT:
                request = builder.put(RequestBody.create(contentType, json)).build();
                break;
            case DELETE:
                if (Objects.isNull(json) || json.trim().isEmpty()) {
                    request = builder.delete().build();
                } else {
                    request = builder.delete(RequestBody.create(contentType, json)).build();
                }
                break;
            default:
                throw new IllegalArgumentException("还不支持该请求方式，敬请期待！");
        }
        return request;
    }

    /**
     * 同步执行
     *
     * @param request OkHttp请求对象
     * @return 响应结果
     */
    private ResponseBody responseBody(Request request) throws IOException {
        return okHttpClient.newCall(request).execute().body();
    }

    /**
     * 异步执行
     *
     * @param request  请求对象
     * @param callback 回调
     */
    private void asyncResponseBody(Request request, Callback callback) {
        okHttpClient.newCall(request).enqueue(callback);
    }

    /**
     * 拼接参数
     *
     * @param map 参数
     * @return 拼接后的键值对参数
     */
    private String params(Map<String, String> map) {
        StringBuilder sb = new StringBuilder();
        for (final Map.Entry<String, String> entry : map.entrySet()) {
            sb.append(entry.getKey());
            sb.append("=");
            // 特殊字符需要编码
            try {
                sb.append(URLEncoder.encode(entry.getValue(), StandardCharsets.UTF_8.name()));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
                log.error("UnsupportedEncodingException", e);
            }
            sb.append("&");
        }
        sb.deleteCharAt(sb.length() - 1);
        return sb.toString();
    }
}
