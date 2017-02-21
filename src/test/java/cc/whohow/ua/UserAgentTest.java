package cc.whohow.ua;

public class UserAgentTest {
    public static void main(String[] args) {
//        UserAgent[] list = {
//                new UserAgent("Mozilla/5.0 (iPhone; CPU iPhone OS 9_1 like Mac OS X) AppleWebKit/601.1.46 (KHTML, like Gecko) Version/9.0 Mobile/13B143 Safari/601.1"),
//                new UserAgent("Mozilla/5.0 (Linux; Android 5.0; SM-G900P Build/LRX21T) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/48.0.2564.23 Mobile Safari/537.36")};
//        for (UserAgent userAgent : list) {
//            System.out.println(userAgent);
//            System.out.println(userAgent.getProducts());
//            System.out.println(new UserAgentDetection(userAgent));
//        }

        UserAgent userAgent = new UserAgent("Mozilla/5.0 (iPhone; CPU iPhone OS 9_1 like Mac OS X) AppleWebKit/601.1.46 (KHTML, like Gecko) Version/9.0 Mobile/13B143 Safari/601.1");
        System.out.println(userAgent);
        System.out.println(userAgent.getProducts());

        UserAgentDetection userAgentDetection = new UserAgentDetection(userAgent);
        System.out.println(userAgentDetection);
        System.out.println(userAgentDetection.getOsName());
        System.out.println(userAgentDetection.getOsVersion());
        System.out.println(userAgentDetection.getBrowserName());
        System.out.println(userAgentDetection.getBrowserVersion());
        System.out.println(userAgentDetection.getUserAgentName());
        System.out.println(userAgentDetection.getUserAgentVersion());
    }
}
