package com.github.airbnb.controller.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.airbnb.common.ResponseCode;
import com.github.airbnb.dto.ResponseDTO;
import com.github.airbnb.dto.WhitepinConnectDTO;
import com.github.airbnb.entity.UserEntity;
import com.github.airbnb.repository.UserRepository;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping(value = "/whitepin")
public class WhitepinApiController {

    @Autowired
    private UserRepository userRepository;

    @Value("${whitepin.service-code}")
    private String serviceCode;

    @Value("${whitepin.api.urls.ip}")
    private String whitepinIp;
    
    @Value("${whitepin.api.urls.port}")
    private int whitepinPort;

    @Value("${whitepin.api.urls.joinPartner}")
    private String whitepinJoinPartner;

    private ObjectMapper mapper = new ObjectMapper();

    @PostMapping(value = "/connect")
    public ResponseEntity<ResponseDTO> whitepinLogin(@Valid @RequestBody WhitepinConnectDTO whitepinConnectDTO
    ) throws IOException {
        ResponseDTO responseDTO = new ResponseDTO();

        CredentialsProvider credsProvider = new BasicCredentialsProvider();
        credsProvider.setCredentials(
                new AuthScope(whitepinIp, whitepinPort),
                new UsernamePasswordCredentials(whitepinConnectDTO.getWhitepinId(), whitepinConnectDTO.getWhitepinPassword()));
        CloseableHttpClient httpclient = HttpClients.custom()
                .setDefaultCredentialsProvider(credsProvider)
                .build();
        try {
            HttpPost httpPost = new HttpPost("http://"+whitepinIp+":"+whitepinPort+whitepinJoinPartner+serviceCode);

            System.out.println("Executing request " + httpPost.getRequestLine());
            CloseableHttpResponse response = httpclient.execute(httpPost);
            try {
                String result = EntityUtils.toString(response.getEntity());
                int statusCode = response.getStatusLine().getStatusCode();
                if(statusCode == 200){
                    Map<String, Object> resultMap = mapper.readValue(result, Map.class);
                    String userToken = (String) resultMap.get("userToken");

                    UserEntity userEntity = userRepository.findById(whitepinConnectDTO.getId()).get();
                    userEntity.setToken(userToken);
                    userRepository.save(userEntity);

                    responseDTO.setCode(ResponseCode.SUCCESSFUL);
                    responseDTO.setMessage("완료되었습니다.");
                } else if(statusCode == 401) {
                    responseDTO.setCode(ResponseCode.FAILED);
                    responseDTO.setMessage("올바르지 않은 ID/PASSWORD 입니다.");
                } else if(statusCode == 500) {
                    responseDTO.setCode(ResponseCode.FAILED);
                    responseDTO.setMessage("이미 처리 되었습니다.");
                }
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

        return ResponseEntity.ok().body(responseDTO);
    }
}
