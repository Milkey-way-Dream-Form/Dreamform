package com.milkyway.dreamform.controller;

import com.milkyway.dreamform.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import java.net.MalformedURLException;

@RequiredArgsConstructor
@Controller
public class ImageController {

    private final ImageService imageService;

    @ResponseBody
    @GetMapping("/image/{filename}") // img 태그 src 반환 controller
    public Resource returnImageSrc(@PathVariable String filename) throws MalformedURLException {
        return new UrlResource("file:" + imageService.getFullPath(filename));
    }
}
