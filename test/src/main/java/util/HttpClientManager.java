package util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.config.ConnectionConfig;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.config.SocketConfig;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.LayeredConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContexts;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;

import javax.net.ssl.SSLContext;
import java.nio.charset.CodingErrorAction;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

/**
 * 生产CloseableHttpClient实例<br/>
 */
public class HttpClientManager {

    private static Log log = LogFactory.getLog(HttpClientManager.class);

    /**
     * 证书信任策略
     */
    private static class AnyTrustStrategy implements TrustStrategy {
        public boolean isTrusted(X509Certificate[] chain, String authType) throws CertificateException {
            return true;
        }
    }


    private static PoolingHttpClientConnectionManager connManager = null;
    private static CloseableHttpClient httpClient = null;

    static {
        init();
    }

    /**
     * 初始化连接池管理器
     */
    private static void init() {
        try {
            //免证书信息
            ConnectionConfig connectionConfig = ConnectionConfig.custom().setMalformedInputAction(CodingErrorAction.IGNORE).setUnmappableInputAction(CodingErrorAction.IGNORE).build();
            SocketConfig socketConfig = SocketConfig.custom().setTcpNoDelay(true).setSoTimeout(100000).build();
            RegistryBuilder<ConnectionSocketFactory> registryBuilder = RegistryBuilder.create();
            ConnectionSocketFactory plainSF = new PlainConnectionSocketFactory();
            registryBuilder.register("http", plainSF);
            //指定信任密钥存储对象和连接套接字工厂
            KeyStore trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
            SSLContext sslContext = SSLContexts.custom().useTLS().loadTrustMaterial(trustStore, new AnyTrustStrategy()).build();
            LayeredConnectionSocketFactory sslSF = new SSLConnectionSocketFactory(sslContext, SSLConnectionSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
            registryBuilder.register("https", sslSF);
            Registry<ConnectionSocketFactory> socketFactoryRegistry = registryBuilder.build();

            connManager = new PoolingHttpClientConnectionManager(socketFactoryRegistry);
            connManager.setDefaultSocketConfig(socketConfig);
            connManager.setDefaultConnectionConfig(connectionConfig);
            connManager.setMaxTotal(2000);
            connManager.setDefaultMaxPerRoute(100);
            httpClient = HttpClients.custom().setConnectionManager(connManager).build();

        } catch (KeyManagementException e) {
            log.error("KeyManagementException", e);
        } catch (NoSuchAlgorithmException e) {
            log.error("NoSuchAlgorithmException", e);
        } catch (KeyStoreException e) {
            log.error("KeyStoreException", e);
        }
    }

    /**
     * @return
     */
    public static CloseableHttpClient getCloseableHttpClient() {
        if (httpClient == null) {
            init();
        }
        return httpClient;
    }
}
