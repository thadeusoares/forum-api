package br.com.thadeu.forum.api.controller;

import java.net.URI;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
/*
 * 
 * Ao testar camada Controller deve-se utilizar a anotação @WebMvcTest, 
 * porém como queremos testar todo ecossistema, precisa-se utilizar a anotação padrão
 */
@SpringBootTest()
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class AutenticacaoControllerTest {

	@Autowired
	private MockMvc mockMvc;
	
	@Test
	public void deveriaDevolver400ComLoginErrado() throws Exception {
		URI uri = new URI("/auth");
		String json = "{\"email\":\"moderador@email.com\",\"senha\":\"123456\"}";
		//String json = "{\"email\":\"invalido@email.com\", \"senha\":\"123456\"}";
		System.out.println(json);
		mockMvc.perform(MockMvcRequestBuilders
				.post(uri)
				.content(json)
				.contentType(MediaType.APPLICATION_JSON))
			.andExpect(MockMvcResultMatchers.status().is(400));
		
	}

}
