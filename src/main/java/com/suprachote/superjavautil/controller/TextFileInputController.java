package com.suprachote.superjavautil.controller;

import com.suprachote.superjavautil.service.TextFileInputService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.io.IOException;

@Controller
public class TextFileInputController {
//    @Autowired
//    private TextFileInputService textFileInputService;


    public TextFileInputController() {
        TextFileInputService textFileInputService = new TextFileInputService();
            textFileInputService.runFirst();
    }
}
