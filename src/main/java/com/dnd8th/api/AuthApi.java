package com.dnd8th.api;

import com.dnd8th.auth.jwt.JwtProviderService;
import com.dnd8th.dto.GoogleOAuthTokenResponse;
import com.dnd8th.dto.GoogleOAuthUserInfoResponse;
import com.dnd8th.error.exception.auth.AccessTokenNotFoundException;
import com.dnd8th.error.exception.auth.ExternalApiFailException;
import com.dnd8th.service.UserService;
import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Slf4j
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class AuthApi {

    private final JwtProviderService jwtProviderService;
    private final UserService userService;

    @Value("${app.auth.redirectUri}")
    private String redirectUri;

    @Value("${app.auth.client-id}")
    private String googleClientId;

    @Value("${app.auth.client-secret}")
    private String googleClientSecret;

    @GetMapping("/login/google")
    public ResponseEntity<String> googleLogin(HttpServletRequest request) {
        // google login URL 생성
        String redirectUrl = "https://accounts.google.com/o/oauth2/v2/auth?" +
                "response_type=code" +
                "&client_id=" + googleClientId +
                "&scope=email%20profile" +
                "&redirect_uri=" + redirectUri;

        return ResponseEntity.status(HttpStatus.FOUND)
                .header(HttpHeaders.LOCATION, redirectUrl)
                .build();
    }

    @GetMapping("/login/callback/google")
    public ResponseEntity<String> googleCallback(@RequestParam String code) {
        // code로 access_token 가져오기
        ResponseEntity<GoogleOAuthTokenResponse> responseEntity = getTokenResponse(code);
        String accessToken = Optional.ofNullable(responseEntity.getBody())
                .orElseThrow(AccessTokenNotFoundException::new)
                .getAccessToken();

        // access_token으로 사용자 정보 가져오기
        ResponseEntity<GoogleOAuthUserInfoResponse> userInfo = getUserInfo(accessToken);
        String email = Optional.ofNullable(userInfo.getBody())
                .orElseThrow(ExternalApiFailException::new)
                .getEmail();

        String givenName = Optional.ofNullable(userInfo.getBody())
                .orElseThrow(ExternalApiFailException::new)
                .getGivenName();

        //email을 통해 회원 가입 여부를 확인 후, 비회원일시 회원가입

        // JWT token 생성
        String jwtToken = jwtProviderService.generateToken(email,
                accessToken);

        // 클라이언트로 JWT token 응답
        return ResponseEntity.ok()
                .body(jwtToken);
    }

    private ResponseEntity<GoogleOAuthTokenResponse> getTokenResponse(String code) {
        // 구글 API를 호출하여 access token 정보 가져오기
        RestTemplate restTemplate = new RestTemplate();
        String url = "https://oauth2.googleapis.com/token";

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("code", code);
        params.add("client_id", googleClientId);
        params.add("client_secret", googleClientSecret);
        params.add("redirect_uri", redirectUri);
        params.add("grant_type", "authorization_code");

        try {
            return restTemplate.postForEntity(url,
                    params, GoogleOAuthTokenResponse.class);
        } catch (HttpClientErrorException exception) {
            throw new ExternalApiFailException();
        }

    }

    private ResponseEntity<GoogleOAuthUserInfoResponse> getUserInfo(String accessToken) {
        // 구글 API를 호출하여 사용자 정보 가져오기
        RestTemplate restTemplate = new RestTemplate();

        String url = "https://www.googleapis.com/oauth2/v2/userinfo";
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken);

        HttpEntity<String> entity = new HttpEntity<>("", headers);

        try {
            return restTemplate.exchange(url,
                    HttpMethod.GET, entity, GoogleOAuthUserInfoResponse.class);
        } catch (HttpClientErrorException exception) {
            throw new ExternalApiFailException();
        }
    }
}
