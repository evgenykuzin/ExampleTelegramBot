
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.meta.ApiContext;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.LongPollingBot;

public class Main {
    private static String BOT_NAME = "example_kuzin_bot";
    private static String BOT_TOKEN = "828485060:AAFX2dLbpFzchsyufap_rrQEGKQVMsjjptQ";
    private static String PROXY_HOST = "localhost|127.0.0.1";
    private static String PROXY_PORT = "80";



    public static void main(String[] args) {
//        DefaultBotOptions botOptions = ApiContext.getInstance(DefaultBotOptions.class);
//        botOptions.setProxyHost("127.0.0.1");
//        botOptions.setProxyPort(9150);
//        botOptions.setProxyType(DefaultBotOptions.ProxyType.SOCKS5);
        // HTTP
        System.setProperty("http.proxyHost", "http://proxy.memorynotfound.com");
        System.setProperty("http.proxyPort", "80");
        System.setProperty("http.nonProxyHosts", "localhost|127.0.0.1");
//        // HTTPS
//        System.setProperty("https.proxyHost", "https://proxy.memorynotfound.com");
//        System.setProperty("https.proxyPort", "443");


        try {

            ApiContextInitializer.init();

            // Create the TelegramBotsApi object to register your bots
            TelegramBotsApi botsApi = new TelegramBotsApi();

           //  Set up Http proxy
            //DefaultBotOptions botOptions = ApiContext.getInstance(DefaultBotOptions.class);
            DefaultBotOptions botOptions = new DefaultBotOptions();

           // CredentialsProvider credsProvider = new BasicCredentialsProvider();
//            credsProvider.setCredentials(
//                    new AuthScope(PROXY_HOST, PROXY_PORT),
//                    new UsernamePasswordCredentials(PROXY_USER, PROXY_PASSWORD));

            HttpHost httpHost = new HttpHost(PROXY_HOST);

            RequestConfig requestConfig = RequestConfig.custom().setProxy(httpHost).setAuthenticationEnabled(true).build();
            botOptions.setRequestConfig(requestConfig);
            botOptions.setProxyHost(PROXY_HOST);
            botOptions.setProxyPort(Integer.parseInt(PROXY_PORT));
            botsApi.registerBot(new Bot2(BOT_TOKEN, BOT_NAME, botOptions));
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

}


