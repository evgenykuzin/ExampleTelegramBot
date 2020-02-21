
import bots_variants.BotAbility;
import bots_variants.BotWebhook;
import org.apache.http.HttpHost;

import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.core.Filter;
import org.apache.logging.log4j.core.config.Configurator;
import org.apache.logging.log4j.core.config.builder.api.*;
import org.apache.logging.log4j.core.config.builder.impl.BuiltConfiguration;
import org.telegram.abilitybots.api.bot.AbilityBot;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.WebhookBot;

import java.io.IOException;

public class Main {
    private static String BOT_NAME = "example_kuzin_bot";
    private static String BOT_TOKEN = "828485060:AAFX2dLbpFzchsyufap_rrQEGKQVMsjjptQ";

    private static String PROXY_HOST = "109.197.186.45";
    private static String PROXY_PORT = "58080";
    private static String PROXY_USER = "";
    private static String PROXY_PASS = "";


    public static void main(String[] args) {
//        DefaultBotOptions botOptions = ApiContext.getInstance(DefaultBotOptions.class);
//        botOptions.setProxyHost("127.0.0.1");
//        botOptions.setProxyPort(9150);
//        botOptions.setProxyType(DefaultBotOptions.ProxyType.SOCKS5);
        // HTTP
//        System.setProperty("http.proxyHost", "http://proxy.memorynotfound.com");
//        System.setProperty("http.proxyPort", "80");
//        System.setProperty("http.nonProxyHosts", "localhost|127.0.0.1");
//        // HTTPS
//        System.setProperty("https.proxyHost", "https://proxy.memorynotfound.com");
//        System.setProperty("https.proxyPort", "443");

//        System.getProperties().put( "proxySet", "true" );
//        System.getProperties().put( "socksProxyHost", "127.0.0.1" );
//        System.getProperties().put( "socksProxyPort", "9150" );

        //  Set up Http proxy
        // DefaultBotOptions botOptions = ApiContext.getInstance(DefaultBotOptions.class);
        setLogsConfiguration();
        DefaultBotOptions botOptions = new DefaultBotOptions();
        if (!PROXY_PASS.isEmpty()) {
            CredentialsProvider credsProvider = new BasicCredentialsProvider();
            //socks5
            credsProvider.setCredentials(
                    new AuthScope(PROXY_HOST, Integer.parseInt(PROXY_PORT)),
                    new UsernamePasswordCredentials(PROXY_USER, PROXY_PASS));

            HttpHost httpHost = new HttpHost(PROXY_HOST);
            RequestConfig requestConfig = RequestConfig.custom().setProxy(httpHost).setAuthenticationEnabled(true).build();
            botOptions.setRequestConfig(requestConfig);

        } else {
            HttpHost httpHost = new HttpHost(PROXY_HOST, Integer.parseInt(PROXY_PORT));
            RequestConfig requestConfig = RequestConfig.custom().setProxy(httpHost).setAuthenticationEnabled(false).build();
            botOptions.setRequestConfig(requestConfig);
        }

        botOptions.setProxyHost(PROXY_HOST);
        botOptions.setProxyPort(Integer.parseInt(PROXY_PORT));

        try {

            ApiContextInitializer.init();

            // Create the TelegramBotsApi object to register your bots
            TelegramBotsApi botsApi = new TelegramBotsApi();

            AbilityBot abilityBot = (AbilityBot) new BotAbility(BOT_TOKEN, BOT_NAME, botOptions);
            WebhookBot webhookBot = new BotWebhook(BOT_NAME, BOT_TOKEN);
            botsApi.registerBot(webhookBot);
            System.out.println("registered");
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private static void setLogsConfiguration(){
        ConfigurationBuilder<BuiltConfiguration> builder
                = ConfigurationBuilderFactory.newConfigurationBuilder();
        AppenderComponentBuilder console
                = builder.newAppender("stdout", "Console");

        builder.add(console);

        AppenderComponentBuilder file
                = builder.newAppender("log", "File");
        file.addAttribute("fileName", "target/logging.log");

        builder.add(file);

        FilterComponentBuilder flow = builder.newFilter(
                "MarkerFilter",
                Filter.Result.ACCEPT,
                Filter.Result.DENY);
        flow.addAttribute("marker", "FLOW");

        console.add(flow);

        RootLoggerComponentBuilder rootLogger
                = builder.newRootLogger(Level.ERROR);
        rootLogger.add(builder.newAppenderRef("stdout"));

        builder.add(rootLogger);

        try {
            builder.writeXmlConfiguration(System.out);
        } catch (IOException ioe){
            ioe.printStackTrace();
        }

        Configurator.initialize(builder.build());

    }

}


