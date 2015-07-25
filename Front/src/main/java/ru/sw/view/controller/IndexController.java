package ru.sw.view.controller;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.TrueFileFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletContext;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

@Controller
@RequestMapping(value = "/")
public class IndexController {

    Logger logger = LoggerFactory.getLogger(IndexController.class);

    @Autowired
    ServletContext servletContext;

    @RequestMapping(method = RequestMethod.GET)
    public String mainPage(Model model) {
        model.addAttribute("scripts" , getScripts());
        return "index";
    }

    public List<String> getScripts() {
        List<String> scripts = new LinkedList<>();
        String path = servletContext.getRealPath("/resources/application/module");
        List<File> files = (List<File>) FileUtils.listFiles(new File(path), TrueFileFilter.INSTANCE, TrueFileFilter.INSTANCE);
        for(File file : files) {
            String filePath = file.getPath();
            if (!filePath.endsWith(".js")) {
                continue;
            }

            int position = filePath.indexOf("\\resources\\");
            scripts.add(filePath.substring(position));
        }
        return scripts;
    }
}
