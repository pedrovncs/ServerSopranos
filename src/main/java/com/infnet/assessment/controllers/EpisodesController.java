package com.infnet.assessment.controllers;

import com.google.gson.Gson;
import com.infnet.assessment.models.Episode;
import com.infnet.assessment.models.Season;
import com.infnet.assessment.repository.ApiRepository;
import spark.Request;
import spark.Response;

public class EpisodesController {

    private final ApiRepository apiRepository;

    private static final Gson gson = new Gson();

    public EpisodesController() {
        this.apiRepository = ApiRepository.getInstance();
    }

    public String getEpisode(Request req, Response res){
        res.type("application/json");
        String seasonId = req.params("season_id");
        String episodeId = req.params("episode_id");

        Episode episode = apiRepository.getEpisode(Integer.parseInt(seasonId), Integer.parseInt(episodeId));
        if (episode == null) {
            res.status(404);
            return "Episode not found.";
        }

        return gson.toJson(episode);
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
            Season season = apiRepository.getSeason(Integer.parseInt(seasonId));
            if (season == null) {
                res.status(404);
                return "Season not found.";
            } else {
                res.status(409);
                return "Duplicate episode.";
            }
        }

        return gson.toJson(episode);
    }

    public String getEpisodeTitle(Request req, Response res){

        return apiRepository.getFromParams(req, res, "title");
    }

    public String getEpisodeRating(Request req, Response res){

        return apiRepository.getFromParams(req, res, "imdb_rating");

    }

    public String getEpisodeDescription(Request req, Response res){

        return apiRepository.getFromParams(req, res, "description");
    }

    public String getEpisodeDirector(Request req, Response res){

        return apiRepository.getFromParams(req, res, "director");
    }

    public String getEpisodeWriter(Request req, Response res){

        return apiRepository.getFromParams(req, res, "writer");
    }

    public String updateEpisode(Request req, Response res){
        res.type("application/json");
        String seasonId = req.params("season_id");
        String episodeId = req.params("episode_id");

        var bodyStr = req.body();
        var body = gson.fromJson(bodyStr, Episode.class);
        if(body == null){
            res.status(400);
            return "Invalid body.";
        }

        Episode episode = apiRepository.updateEpisode(Integer.parseInt(seasonId), Integer.parseInt(episodeId), body);
        if(episode == null){
            res.status(404);
            return "Episode not found.";
        }

        return gson.toJson(episode);
    }

    public String deleteEpisode(Request req, Response res){
        String seasonId = req.params("season_id");
        String episodeId = req.params("episode_id");

        Episode episode = apiRepository.deleteEpisode(Integer.parseInt(seasonId), Integer.parseInt(episodeId));
        if( episode == null ){
            res.status(404);
            return "Episode not found.";
        }

        return gson.toJson(episode);
    }
}