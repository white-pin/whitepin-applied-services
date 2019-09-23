package com.github.airbnb;

import java.util.HashSet;
import java.util.Set;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@SpringBootApplication
public class AirbnbApplication {

    public static void main(String[] args) {
        beforeApplicationSetup(args);
        SpringApplication.run(AirbnbApplication.class, args);
    }

    private static void beforeApplicationSetup(String[] args) {
        checkActiveProfile();
        overrideConfigs(args);
    }

    /**
     * profile을 설정하지 않은 경우 기본 값 "local"로 설정
     */
    private static void checkActiveProfile() {
        final String defaultProfile = "local";
        String profileValues = System.getProperty("spring.profiles.active");

        if (!StringUtils.hasText(profileValues)) {
            logger.info("Setting default profile because empty active profile. default : [{}]", defaultProfile);
            System.setProperty("spring.profiles.active", defaultProfile);
            return;
        }

        Set<String> requiredProfileSet = new HashSet<>();
        requiredProfileSet.add("local");
        requiredProfileSet.add("dev");

        String[] profiles = profileValues.split(",");
        for (String profile : profiles) {
            // local , dev, real 중 하나가 있는 경우
            if (requiredProfileSet.contains(profile)) {
                return;
            }
        }

        profileValues = profileValues + "," + defaultProfile;
        System.setProperty("spring.profiles.active", profileValues);
    }

    /**
     * args 중 외부 config 파일이 존재하면 기존 설정에 오버라이딩
     *
     * e.g)
     * --spring.config.location=/home/app/application.yaml일 경우
     * --spring.config.location=classpath:/application.yaml,/home/app/application.yaml 로 변경
     */
    static void overrideConfigs(String[] args) {
        if (args == null || args.length == 0) {
            return;
        }

        final String prefix = "--spring.config.location=";
        for (int i = 0; i < args.length; i++) {
            String arg = args[i];

            if (!arg.startsWith(prefix)) {
                continue;
            }

            args[i] = prefix + "classpath:/application.yaml," + arg.substring(prefix.length());
            return;
        }
    }

    @GetMapping("/")
    public String rootPage() {
        logger.info("Request index page");
        return "index";
    }

    @GetMapping("/index")
    public String indexPage() {
        logger.info("Request index page");
        return "index";
    }

    @GetMapping("/siteLogin")
    public String loginPage() {
        logger.info("Request login page");
        return "login";
    }

    @GetMapping("/myPage")
    public String myPagePage() {
        logger.info("Request myPage page");
        return "myPage";
    }

    @GetMapping("/cart")
    public String cartPage() {
        logger.info("Request cart page");
        return "cart";
    }

    @GetMapping("/tracking")
    public String trackingPage() {
        logger.info("Request tracking page");
        return "tracking";
    }

    @GetMapping("/contact")
    public String contactPage() {
        logger.info("Request contact page");
        return "contact";
    }

    @GetMapping("/category")
    public String categoryPage() {
        logger.info("Request category page");
        return "category";
    }

    @GetMapping("/single-product")
    public String singleProductPage() {
        logger.info("Request single-product page");
        return "single-product";
    }

    @GetMapping("/checkout")
    public String checkoutPage() {
        logger.info("Request checkout page");
        return "checkout";
    }


    @GetMapping("/blog")
    public String blogPage() {
        logger.info("Request blog page");
        return "blog";
    }

    @GetMapping("/single-blog")
    public String singleBlogPage() {
        logger.info("Request single-blog page");
        return "single-blog";
    }

    @GetMapping("/elements")
    public String elementsPage() {
        logger.info("Request elements page");
        return "elements";
    }
}
