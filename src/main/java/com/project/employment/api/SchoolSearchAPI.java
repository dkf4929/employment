package com.project.employment.api;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.project.employment.dto.SchoolSearchDto;
import lombok.extern.slf4j.Slf4j;
import net.minidev.json.parser.JSONParser;
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
    @Value("${school.api-key}")
    private String apiKey;

    private static final String BASE_URL = "https://www.career.go.kr/cnet/openapi/getOpenApi";

    public ResponseEntity<List<SchoolSearchDto>> searchSchool(String schoolName, String schoolType) {
        List<SchoolSearchDto> list = new ArrayList<>();
        CloseableHttpClient httpclient = HttpClients.createDefault();

        try {
//            String encodedSchoolName = URLEncoder.encode(schoolName, StandardCharsets.UTF_8);
            String url = BASE_URL + "?apiKey=" + apiKey + "&svcType=api&svcCode=SCHOOL&contentType=json&gubun=" + schoolType + "&searchSchulNm=" + schoolName;

            HttpGet httpGet = new HttpGet(url);
            HttpResponse response = httpclient.execute(httpGet);
            HttpEntity entity = response.getEntity();

            if (entity != null) {
                JsonParser parser = new JsonParser();
                JsonObject jsonObj = parser.parse(EntityUtils.toString(entity, StandardCharsets.UTF_8)).getAsJsonObject();
                JsonArray jsonArray = jsonObj.getAsJsonObject("dataSearch").getAsJsonArray("content");

                if (jsonArray.size() > 0) {
                    for (int i = 0; i < jsonArray.size(); i++) {
                        if (schoolType.equals("univ_list")) {
                            list.add(SchoolSearchDto.builder()
                                    .name(jsonArray.get(i).getAsJsonObject().get("schoolName").getAsString())
                                    .address(jsonArray.get(i).getAsJsonObject().get("adres").getAsString())
                                    .gubun(jsonArray.get(i).getAsJsonObject().get("schoolGubun").getAsString())
                                    .campusGubun(jsonArray.get(i).getAsJsonObject().get("campusName").getAsString())
                                    .build());
                        } else {
                            list.add(SchoolSearchDto.builder()
                                    .name(jsonArray.get(i).getAsJsonObject().get("schoolName").getAsString())
                                    .address(jsonArray.get(i).getAsJsonObject().get("adres").getAsString())
                                    .build());
                        }
                    }
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

//    private JSONObject xmlToJson(String xml) {
//        return XML.toJSONObject(xml);
//    }
}
