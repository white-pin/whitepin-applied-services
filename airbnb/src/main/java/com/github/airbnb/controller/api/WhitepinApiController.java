package com.github.airbnb.controller.api;

import com.github.airbnb.dto.ResponseDTO;
import com.github.airbnb.dto.TradeDTO;
import com.github.airbnb.dto.UserDTO;
import com.github.airbnb.dto.WhitepinConnectDTO;
import io.swagger.annotations.ApiParam;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;

@RestController
@RequestMapping(value = "/whitepin")
public class WhitepinApiController {

    @Value("${whitepin.service-code}")
    private String serviceCode;

    @Value("${whitepin.api.urls.base}")
    private String whitepinBaseUrl;

    @Value("${whitepin.api.urls.joinPartner}")
    private String whitepinJoinPartner;


    @PostMapping(value = "/connect")
    public ResponseEntity<ResponseDTO> whitepinLogin(@Valid @RequestBody WhitepinConnectDTO whitepinConnectDTO
    ) throws IOException {
        CredentialsProvider credsProvider = new BasicCredentialsProvider();
        credsProvider.setCredentials(
                new AuthScope("localhost", 3030),
                new UsernamePasswordCredentials(whitepinConnectDTO.getWhitepinId(), whitepinConnectDTO.getWhitepinPassword()));
        CloseableHttpClient httpclient = HttpClients.custom()
                .setDefaultCredentialsProvider(credsProvider)
                .build();
        try {
            HttpPost httpPost = new HttpPost(whitepinBaseUrl+whitepinJoinPartner+serviceCode);

            System.out.println("Executing request " + httpPost.getRequestLine());
            CloseableHttpResponse response = httpclient.execute(httpPost);
            try {
                System.out.println("----------------------------------------");
                System.out.println(response.getStatusLine());
                System.out.println(EntityUtils.toString(response.getEntity()));
            } finally {
                response.close();
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            httpclient.close();
        }

        return ResponseEntity.ok().body(new ResponseDTO());
    }
}
