package com.milkyway.dreamform.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Setter
@Getter
@NoArgsConstructor
@Entity
public class JobLists {

    public JobLists(String jobGb, String jobClcd, String jobClcdNM, String jobCd, String jobNm) {
        this.jobGb = jobGb;
        this.jobClcd = jobClcd;
        this.jobClcdNM = jobClcdNM;
        this.jobCd = jobCd;
        this.jobNm = jobNm;
    }

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;
    @Column(nullable = true)
    private String jobGb; //직업구분코드 3:이색직업별, 4:테마별, 6:신직업/창직
    @Column(nullable = false)
    private String jobClcd; //직업분류코드 B:이색, D:테마별, 숫자:신직업/창직
    @Column(nullable = false)
    private String jobClcdNM; //직업분류명
    @Column(nullable = false)
    private String jobCd; //직업코드
    @Column(nullable = true)
    private String jobNm; //직업명

}
