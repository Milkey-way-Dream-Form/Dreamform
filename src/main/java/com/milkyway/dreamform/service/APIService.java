package com.milkyway.dreamform.service;

import com.milkyway.dreamform.model.JobLists;
import com.milkyway.dreamform.repository.JobListsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import javax.xml.parsers.DocumentBuilderFactory;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class APIService {

    private final JobListsRepository jobListsRepository;
    private String authKey = "WNL0K8OBRYZY5NZNO0OPQ2VR1HK";
//
    public List<Map<String, String>> apiParsing() throws Exception {
        String url = "http://openapi.work.go.kr/opi/opi/opia/jobSrch.do?authKey=" + authKey + "&returnType=XML&target=JOBCD";

        Document document = (Document) DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(url);
        document.getDocumentElement().normalize(); // DOM Tree XML 구조 변경

        Element root = document.getDocumentElement(); // 루트 노드

        NodeList nodeList = document.getElementsByTagName("jobList"); // jobsList의 하위 태그 전체

        List<Map<String, String>> list = new ArrayList<>();

        for (int i = 0, n = nodeList.getLength(); i < n; i++) {
            Map<String, String> map = new HashMap<>();
            Node node = nodeList.item(i);
            Element element = (Element) node;

            map.put("jobGb", getTagValue("jobGb", element));
            map.put("jobClcd", getTagValue("jobClcd", element));
            map.put("jobClcdNM", getTagValue("jobClcdNM", element));
            map.put("jobCd", getTagValue("jobCd", element));
            map.put("jobNm", getTagValue("jobNm", element));

            list.add(map);
        }
        return list;
    }

    public void jobListSave(List<Map<String, String>> list) {
        for (int i = 0, n = list.size(); i < n; i++) {
            String jobGb = list.get(i).get("jobGb");
            String jobClcd = list.get(i).get("jobClcd");
            String jobClcdNM = list.get(i).get("jobClcdNM");
            String jobCd = list.get(i).get("jobCd");
            String jobNm = list.get(i).get("jobNm");

            jobListsRepository.save(new JobLists(jobGb, jobClcd, jobClcdNM, jobCd, jobNm));
        }
    }

    private static String getTagValue(String tag, Element element) {
        NodeList list = null;
        Node value = null;
        try {
            list = element.getElementsByTagName(tag).item(0).getChildNodes();
            value = (Node) list.item(0);
        } catch (Exception e) {
            if (value == null)
                return null;
        }
        return value.getNodeValue();
    }

}
