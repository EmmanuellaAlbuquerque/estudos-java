package com.challenge.events;

import com.challenge.events.domain.dto.EventDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class EventsApplicationTests {

	@Autowired
	private MockMvc mockMvc;

	@Test
	void contextLoads() {
	}

	private String turnDtoToJson(Object object) throws JsonProcessingException {
		return new ObjectMapper()
				.registerModule(new JavaTimeModule())
				// serializa datas como strings legíveis e não como timestamps numéricos
				.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
				.writeValueAsString(object);
	}

	@Test
	@DisplayName("Deve criar um Evento com sucesso")
	void createAnEvent() throws Exception {

		EventDto eventDto = new EventDto(
				"Evento de Programação Java no Nordeste",
				100,
				LocalDateTime.now().plusDays(2),
				LocalDateTime.now().plusDays(4)
		);

		String json = turnDtoToJson(eventDto);

		MvcResult mvcResult = this.mockMvc.perform(
				post("/api/v1/events")
						.contentType(MediaType.APPLICATION_JSON)
						.content(json))
				.andExpect(status().isCreated())
				.andReturn();

		String location = mvcResult.getResponse().getHeader("Location");
		assertThat(location).isNotNull();
		assertThat(location).startsWith("http://localhost/api/v1/events");
		String uuid = location.substring(location.lastIndexOf('/') + 1);
		assertThat(UUID.fromString(uuid)).isInstanceOf(UUID.class);
	}

	@Test
	@DisplayName("Não deve conseguir criar um Evento sem os atributos obrigatórios")
	void createAnEventWithRequiredAttributesMissing() throws Exception {

		EventDto eventDto = EventDto.builder().build();

		String json = turnDtoToJson(eventDto);

		this.mockMvc.perform(
						post("/api/v1/events")
								.contentType(MediaType.APPLICATION_JSON)
								.content(json))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.name").value("O nome do evento é obrigatório"))
				.andExpect(jsonPath("$.vacancies").value("A quantidade de vagas disponíveis para o evento é obrigatória"))
				.andExpect(jsonPath("$.starts_at").value("A data de início do evento é obrigatória"))
				.andExpect(jsonPath("$.ends_at").value("A data de fim do evento é obrigatória"));
	}

	@Test
	@DisplayName("Não deve conseguir criar um Evento com dados inválidos")
	void createAnEventWithInvalidData() throws Exception {

		EventDto eventDto = EventDto.builder()
				.name(String.valueOf("W").repeat(151))
				.vacancies(-10)
				.startsAt(LocalDateTime.now().minusDays(4))
				.endsAt(LocalDateTime.now().minusDays(1))
				.build();

		String json = turnDtoToJson(eventDto);

		this.mockMvc.perform(
						post("/api/v1/events")
								.contentType(MediaType.APPLICATION_JSON)
								.content(json))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.name").value("O nome do evento deve ter no máximo 150 caracteres"))
				.andExpect(jsonPath("$.vacancies").value("O número de vagas do evento deve ser maior que zero"))
				.andExpect(jsonPath("$.starts_at").value("A data de início do evento deve ser uma data no presente ou no futuro"))
				.andExpect(jsonPath("$.ends_at").value("A data de fim do evento deve ser uma data no futuro"));
	}

	@Test
	@DisplayName("Não deve conseguir criar um Evento com a data de fim sendo anterior a data de início")
	void createAnEventWithInvalidDates() throws Exception {

		EventDto eventDto = EventDto.builder()
				.startsAt(LocalDateTime.now().plusDays(2))
				.endsAt(LocalDateTime.now().plusDays(1))
				.build();

		String json = turnDtoToJson(eventDto);

		this.mockMvc.perform(
						post("/api/v1/events")
								.contentType(MediaType.APPLICATION_JSON)
								.content(json))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.ends_at").value("A data de fim deve ser após à data de início"));
	}
}