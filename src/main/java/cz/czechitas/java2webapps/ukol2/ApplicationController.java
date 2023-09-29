package cz.czechitas.java2webapps.ukol2;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Controller
public class ApplicationController {

    Random random;
    List<String> quotes;
    List<String> pictures;

    public ApplicationController() throws IOException {
        random = new Random();
        quotes = readAllLines("dataFiles/citaty.txt");
        pictures = readAllLines("dataFiles/pictures.txt");
    }

    @GetMapping("/")
    public ModelAndView showOutTheQuote() {
        ModelAndView modelAndView = new ModelAndView("index");
        modelAndView.addObject("quote", quotes.get(random.nextInt(quotes.size())));
        modelAndView.addObject("picture", pictures.get(random.nextInt(pictures.size())));
        return modelAndView;
    }

    private static List<String> readAllLines(String resource) throws IOException {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        try (InputStream inputStream = classLoader.getResourceAsStream(resource);
             BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
            return reader
                    .lines()
                    .collect(Collectors.toList());
        }
    }
}
