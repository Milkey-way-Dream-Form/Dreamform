package com.milkyway.dreamform.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;

@Data
@NoArgsConstructor
@Embeddable
public class UploadFile {

    private String imageOriginal; // image 원본 명
    private String imagePath; // image unique name

    public UploadFile(String imageOriginal, String imagePath) {
        this.imageOriginal = imageOriginal;
        this.imagePath = imagePath;
    }
}
