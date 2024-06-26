package com.infnet.assessment.controllers;

import com.google.gson.Gson;
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
        var episodes = apiRepository.getSeasonEpisodes(Integer.parseInt(seasonId));

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
            res.status(409);
            res.body("Duplicated Season");
            return res.body();
        }

        return gson.toJson(season);
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

    public String updateSeason(Request req, Response res){
        res.type("application/json");
        res.status(405);
        res.body("Method not allowed on Seasons.");
        return res.body();
    }

}
