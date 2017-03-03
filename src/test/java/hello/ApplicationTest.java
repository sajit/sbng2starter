package hello;



import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.quicktheories.quicktheories.QuickTheory.qt;
import static org.quicktheories.quicktheories.generators.SourceDSL.arbitrary;
import static org.quicktheories.quicktheories.generators.SourceDSL.arrays;
import static org.quicktheories.quicktheories.generators.SourceDSL.strings;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import hello.util.SortUtil;
import org.assertj.core.util.Lists;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.quicktheories.quicktheories.core.Source;
import org.quicktheories.quicktheories.impl.TheoryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ApplicationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void shouldReturnValidLimit() throws Exception {
        this.mockMvc.perform(get("/limits?gradeLevel=0&awardType=gold")).andExpect(status().isOk())
                .andExpect(content().string(containsString("5500")));
    }
    @Test
    public void shouldReturnNotFound() throws Exception {
        this.mockMvc.perform(get("/limits?gradeLevel=0&awardType=silver")).andExpect(status().isNotFound());
    }

    @Test
    public void shouldReturnBadArgs() throws Exception {
        this.mockMvc.perform(get("/limits?gradeLevel=0&awardType=silverish")).andExpect(status().isBadRequest());
    }

    @Test
    @Ignore
    public void addingTwoPositiveIntegersAlwaysGivesAPositiveInteger(){
        Source<String> gradeLevelSource = strings().ascii().ofLength(1);
        Source<GreetingController.AwardType> awardTypeSource = arbitrary().enumValues(GreetingController.AwardType.class);
        qt().forAll(gradeLevelSource,awardTypeSource).check((grade,award) -> {
            List<Integer> validResponses = Lists.newArrayList(200, 404,400);

            try {

                ResultActions resultActions = this.mockMvc.perform(get("/limits?gradeLevel="+grade+"0&awardType="+award));
                MvcResult result = resultActions.andReturn();

                return validResponses.contains(result.getResponse().getStatus());
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        });



    }

}