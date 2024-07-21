package me.nadetdev.rest_football.services;

import me.nadetdev.rest_football.exceptions.AlreadyExistsException;
import me.nadetdev.rest_football.exceptions.NotFoundException;
import me.nadetdev.rest_football.model.Player;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class PlayerService {

    private Map<String, Player> players = new HashMap<>();

    public PlayerService(Map<String, Player> players) {
        generateDummyPlayersList(players);
    }

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

    private Map<String, Player> generateDummyPlayersList(Map<String, Player> listOfPlayers){
        Player messi = new Player("1", 1, "Messi", "Forward", LocalDate.of(1986, 1, 10));
        Player ronaldo = new Player("2", 2, "Ronaldo", "Forward", LocalDate.of(1995, 3, 5));
        Player neymar = new Player("3", 3, "Neymar", "Forward", LocalDate.of(2000, 7, 20));
        Player mbappe = new Player("4", 4, "Mbappe", "Forward", LocalDate.of(2001, 12, 3));
        Player kante = new Player("5", 5, "Kante", "Forward", LocalDate.of(2011, 10, 8));
        Player pogba = new Player("6", 6, "Pogba", "Forward", LocalDate.of(1998, 5, 1));

        players.put(messi.id(), messi);
        players.put(ronaldo.id(), ronaldo);
        players.put(neymar.id(), neymar);
        players.put(mbappe.id(), mbappe);
        players.put(kante.id(), kante);
        players.put(pogba.id(), pogba);

        return players;
    }
}
