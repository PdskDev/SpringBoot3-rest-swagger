package me.nadetdev.rest_football.model;

import java.time.LocalDate;

public record Player(String id, int jerseyNumber, String name, String position, LocalDate dateOfBirth) {}
