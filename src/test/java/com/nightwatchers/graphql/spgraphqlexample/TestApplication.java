package com.nightwatchers.graphql.spgraphqlexample;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Collections;
import java.util.Map;

import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@SpringBootTest(classes = SpGraphqlExampleApplication.class)
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
public class TestApplication {

	private MockMvc mockMvc;

	@Autowired
	private WebApplicationContext webApplicationContext;
	
	@Before
	public void setUp() throws Exception {
		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
	}
		
	@Test
	public void getBooks() throws Exception{	
        String query = "{\n\tallBooks{\n\t\tisn\n\t\ttitle\n\t}\n}";
        
        ResultActions postResult = performGraphQlPost(query);
        
        
        postResult.andExpect(status().isOk())
        		.andExpect(jsonPath("$.data.allBooks[0].isn").value("123"))
        		.andExpect(jsonPath("$.data.allBooks[0].title").value("Book of Clouds"))
        		.andExpect(jsonPath("$.data.allBooks[1].isn").value("124"))
        		.andExpect(jsonPath("$.data.allBooks[1].title").value("Cloud architeccture and engg."))
        		.andExpect(jsonPath("$.data.allBooks[2].isn").value("125"))
        		.andExpect(jsonPath("$.data.allBooks[2].title").value("Java 9 Programming"))
        		.andExpect(jsonPath("$.extensions").isEmpty())
        		.andExpect(jsonPath("$.errors").isArray());
	}

    private ResultActions performGraphQlPost(String query) throws Exception {
        return mockMvc.perform(post("/rest/books")
        		.contentType(MediaType.APPLICATION_JSON)
                //.content(generateRequest(query, null))
        		.content(query)
        );
    }

//    private String generateRequest(String query, Map variables) throws Exception {
//    //private String generateRequest(String query) throws Exception {
//        JSONObject jsonObject = new JSONObject();
//
//        jsonObject.put("query", query);
//
//        if (variables != null) {
//            jsonObject.put("variables", Collections.singletonMap("input", variables));
//        }
//
//        return jsonObject.toString();
//    }
}