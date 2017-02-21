# User Agent Parser

## Description

A simple User-Agent parser and detector.

The regex of predefined detector is from [device-detector](https://github.com/piwik/device-detector).

## Usage
```java
UserAgent userAgent = new UserAgent("Mozilla/5.0 (iPhone; CPU iPhone OS 9_1 like Mac OS X) AppleWebKit/601.1.46 (KHTML, like Gecko) Version/9.0 Mobile/13B143 Safari/601.1");
System.out.println(userAgent);
// Mozilla/5.0 (iPhone; CPU iPhone OS 9_1 like Mac OS X) AppleWebKit/601.1.46 (KHTML, like Gecko) Version/9.0 Mobile/13B143 Safari/601.1

System.out.println(userAgent.getProducts());
// [Mozilla/5.0 (iPhone; CPU iPhone OS 9_1 like Mac OS X), AppleWebKit/601.1.46 (KHTML, like Gecko), Version/9.0, Mobile/13B143, Safari/601.1]

UserAgentDetection userAgentDetection = new UserAgentDetection(userAgent);
System.out.println(userAgentDetection);
// userAgent.name: Safari
// userAgent.version: 601.1
// os.name: iOS
// os.version: 9_1
// device.vendor: Apple
// device.model: iPhone
// device.device: smartphone
// browser.name: Mobile Safari
// browser.version: 9.0
// renderingEngine.name: WebKit

System.out.println(userAgentDetection.getOsName());
// iOS
System.out.println(userAgentDetection.getOsVersion());
// 9_1
System.out.println(userAgentDetection.getBrowserName());
// Mobile Safari
System.out.println(userAgentDetection.getBrowserVersion());
// 9.0
System.out.println(userAgentDetection.getUserAgentName());
// Safari
System.out.println(userAgentDetection.getUserAgentVersion());
// 601.1
```

## License

* LGPL

* MIT