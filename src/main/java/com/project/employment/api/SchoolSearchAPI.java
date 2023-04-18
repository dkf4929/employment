package com.project.employment.api;

import com.project.employment.dto.SchoolSearchDto;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.XML;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import java.io.*;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
public class SchoolSearchAPI {
    private static final String BASE_URL = "https://open.neis.go.kr/hub/schoolInfo";

    @Value("${school.api-key}")
    private String SERVICE_KEY;

    public ResponseEntity<List<SchoolSearchDto>> searchSchool(String schoolName) {
        List<SchoolSearchDto> list = new ArrayList<>();
        CloseableHttpClient httpclient = HttpClients.createDefault();

        try {
            String encodedSchoolName = URLEncoder.encode(schoolName, StandardCharsets.UTF_8);
            String url = BASE_URL + "?KEY=" + SERVICE_KEY + "&SCHUL_NM=" + encodedSchoolName;

            HttpGet httpGet = new HttpGet(url);
            HttpResponse response = httpclient.execute(httpGet);
            HttpEntity entity = response.getEntity();

            if (entity != null) {
                JSONObject jsonObject = xmlToJson(EntityUtils.toString(entity, StandardCharsets.UTF_8));// xml to json
                JSONArray jsonArray = jsonObject.getJSONObject("schoolInfo").getJSONArray("row");

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject content = jsonArray.getJSONObject(i);

                    list.add(SchoolSearchDto.builder()
                            .name(content.getString("SCHUL_NM"))
                            .address(content.getString("ORG_RDNMA"))
                            .gubun(content.getString("HS_SC_NM"))
                            .telNo(content.getString("ORG_TELNO"))
                            .build());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                httpclient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    private JSONObject xmlToJson(String xml) {
        return XML.toJSONObject(xml);
    }
}
