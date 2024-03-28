package com.infnet.assessment.controllers;

import com.google.gson.Gson;
import com.infnet.assessment.models.Episode;
import com.infnet.assessment.models.Season;
import com.infnet.assessment.repository.ApiRepository;
import spark.Request;
import spark.Response;

public class SeasonsController {
    private final ApiRepository apiRepository;
    private final Gson gson = new Gson();

    public SeasonsController() {
        this.apiRepository = ApiRepository.getInstance();
    }

    public String listAllSeasons(Request req, Response res) {
        res.type("application/json");
        var seasons = apiRepository.getSeasons();
        if (seasons == null) {
            res.status(404);
            return "No seasons found.";
        }

        return gson.toJson(seasons);
    }

    public String listAllEpisodes(Request req, Response res) {
        String seasonId = req.params("season_id");
        res.type("application/json");
        var episodes = apiRepository.getAllEpisodes(Integer.parseInt(seasonId));

        if (episodes == null) {
            res.status(204);
            return "Season not found, try another one.";
        }

        return gson.toJson(episodes);
    }

    public String createSeason(Request req, Response res) {
        res.type("application/json");
        var bodyString = req.body();
        var body = gson.fromJson(bodyString, Season.class);
        if (body == null) {
            res.status(400);
            return "Invalid body.";
        }

        Season season = apiRepository.addSeason(body);
        if (season == null) {
            res.status(500);
            return "Error creating season.";
        }

        return gson.toJson(season);
    }

    public String createEpisode(Request req, Response res) {
        res.type("application/json");
        String seasonId = req.params("season_id");
        var bodyString = req.body();
        var body = gson.fromJson(bodyString, Episode.class);
        if (body == null) {
            res.status(400);
            return "Invalid body.";
        }

        Episode episode = apiRepository.addEpisode(Integer.parseInt(seasonId), body);
        if (episode == null) {
            res.status(500);
            return "Error creating episode.";
        }

        return gson.toJson(episode);
    }

    public String deleteSeason(Request req, Response res) {
        String seasonId = req.params("season_id");
        res.type("application/json");
        var season = apiRepository.getSeason(Integer.parseInt(seasonId));
        if (season == null) {
            res.status(404);
            return "Season not found.";
        }

        apiRepository.getSeasons().remove(season);
        return gson.toJson(season);
    }

}
