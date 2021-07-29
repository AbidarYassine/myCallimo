package com.rest.ai.myCallimo.config;

import org.springframework.beans.factory.annotation.Value;

//@Configuration
public class FlickrConfiguration {
    @Value("${flickr.apiKey}")
    private String apiKey;

    @Value("${flickr.apiSecret}")
    private String apiSecret;

    @Value("${flickr.appKey}")
    private String appKey;

    @Value("${flickr.appSecret}")
    private String appSecret;

    //    @Bean
    /*public Flickr getFlickr() throws InterruptedException, ExecutionException, IOException, FlickrException {
        Flickr flickr = new
                Flickr(apiKey, apiSecret, new REST());

        OAuth10aService service = new ServiceBuilder(apiKey).apiSecret(apiSecret).build(FlickrApi.instance(FlickrApi.FlickrPerm.DELETE));

        final Scanner scanner = new Scanner(System.in);

        final OAuth1RequestToken request = service.getRequestToken();

        final String authUrl = service.getAuthorizationUrl(request);

        System.out.println(authUrl);
        System.out.println("Paste it here >> ");

        final String authVerifier = scanner.nextLine();

        OAuth1AccessToken accessToken = service.getAccessToken(request, authVerifier);

        System.out.println(accessToken.getToken());
        System.out.println(accessToken.getTokenSecret());

        Auth auth = flickr.getAuthInterface().checkToken(accessToken);

        System.out.println("---------------------------");
        System.out.println(auth.getToken());
        System.out.println(auth.getTokenSecret());

        return flickr;
    }*/

}
