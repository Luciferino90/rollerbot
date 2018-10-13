package it.pathfinder.rollerbot;

import static org.hamcrest.Matchers.equalTo;

import it.pathfinder.rollerbot.service.ParserService;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class HelloControllerTest {

    @Autowired
    private MockMvc mvc;

    @Ignore
    @Test
    public void getHello() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(equalTo("Greetings from Spring Boot!")));
    }

    @Ignore
    @Test
    public void randomize()
    {
        String diceFormula = "2d10";
        ParserService parserService = new ParserService();
        parserService.parseFormula(diceFormula, "internalTest");
    }

    @Ignore
    @Test
    public void multiBracketsSplitterTest()
    {
        ParserService parserService = new ParserService();
        //String formula = "3x10d4+10d6r(1)+$var$";
        String formula = "3*3d6";
        System.out.println(parserService.parseFormula(formula, "internalTest"));
    }

}