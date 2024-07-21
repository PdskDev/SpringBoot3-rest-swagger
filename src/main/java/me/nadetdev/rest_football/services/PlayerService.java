package me.nadetdev.rest_football.services;

import me.nadetdev.rest_football.exceptions.AlreadyExistsException;
import me.nadetdev.rest_football.exceptions.NotFoundException;
import me.nadetdev.rest_football.model.Player;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class PlayerService {

    private final Map<String, Player> players = Map.ofEntries(
            Map.entry("1", new Player("1", 1, "Messi", "Forward", LocalDate.of(1986, 1, 10))),
            Map.entry("2", new Player("2", 2, "Ronaldo", "Forward", LocalDate.of(1995, 3, 5))),
            Map.entry("3", new Player("3", 3, "Neymar", "Forward", LocalDate.of(2000, 7, 20))),
            Map.entry("4", new Player("4", 4, "Mbappe", "Forward", LocalDate.of(2001, 12, 3))),
            Map.entry("6", new Player("6", 6, "Pogba", "Forward", LocalDate.of(1998, 5, 1)))
    );

    public List<Player> listPlayers(){
        return players.values().stream().collect(Collectors.toList());
    }

    public Player getPlayer(String id){
        Player player = players.get(id);
        if(player == null){
            throw new NotFoundException("Player not found");
        }
        return player;
    }

    public Player addPlayer(Player player) {
        if (players.containsKey(player.id())) {
            throw new AlreadyExistsException("Player already exists");
        } else {
            players.put(player.id(), player);
            return player;
        }
    }

    public Player updatePlayer(String id, Player player) {
        if(!players.containsKey(player.id())) {
            throw new NotFoundException("Player not found");
        }
        else {
            players.put(player.id(), player);
            return player;
        }
    }

    public void deletePlayer(String id) {
        if (players.containsKey(id)) {
            players.remove(id);
        }
    }
}
