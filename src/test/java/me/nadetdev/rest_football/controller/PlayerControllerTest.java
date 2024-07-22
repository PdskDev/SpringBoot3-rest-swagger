package me.nadetdev.rest_football.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import me.nadetdev.rest_football.exceptions.NotFoundException;
import me.nadetdev.rest_football.model.Player;
import me.nadetdev.rest_football.services.PlayerService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDate;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(value = PlayerController.class)
public class PlayerControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private PlayerService playerService;

    @Test
    public void testListPlayers() throws Exception {

        List<Player> players = generatePlayersList();

        given(playerService.listPlayers()).willReturn(players);

        MvcResult result = mvc.perform(MockMvcRequestBuilders
                .get("/players")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(2)))
                .andReturn();

        String json = result.getResponse().getContentAsString();

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());

        List<Player> returnedPlayers = mapper.readValue(
                json,
                mapper.getTypeFactory().constructCollectionType(
                List.class, Player.class)
        );

        assertArrayEquals(players.toArray(), returnedPlayers.toArray());
    }

    @Test
    public void testReadPlayer_doesNotExist() throws Exception {
        String id = "25";
        given(playerService.getPlayer(id)).willThrow(new NotFoundException("Player not found"));

        mvc.perform(MockMvcRequestBuilders.get("/players/" + id)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    private List<Player> generatePlayersList(){
        Player player1 = new Player("1", 1, "Messi", "Forward", LocalDate.of(1986, 1, 10));
        Player player2 = new Player("2", 2, "Ronaldo", "Forward", LocalDate.of(1995, 3, 5));
        return List.of(player1, player2);
    }
}
