package com.infnet.assessment.controllers;

import com.infnet.assessment.repository.ApiRepository;
import spark.Request;
import spark.Response;

public class EpisodesController {

    private final ApiRepository apiRepository;

    public EpisodesController() {
        this.apiRepository = ApiRepository.getInstance();
    }

    public String getEpisode(Request req, Response res){
        res.type("application/json");
        String seasonId = req.params("season_id");
        String episodeId = req.params("episode_id");
        return "Get episode " + episodeId + " from season " + seasonId;
    }

    public String getEpisodeTitle(Request req, Response res){
        String seasonId = req.params("season_id");
        String episodeId = req.params("episode_id");
        return "Get episode " + episodeId + " title from season " + seasonId;
    }

    public String getEpisodeRating(Request req, Response res){
        String seasonId = req.params("season_id");
        String episodeId = req.params("episode_id");
        return "Get episode " + episodeId + " rating from season " + seasonId;
    }

    public String getEpisodeDescription(Request req, Response res){
        String seasonId = req.params("season_id");
        String episodeId = req.params("episode_id");
        return "Get episode " + episodeId + " description from season " + seasonId;
    }

    public String getEpisodeDirector(Request req, Response res){
        String seasonId = req.params("season_id");
        String episodeId = req.params("episode_id");
        return "Get episode " + episodeId + " director from season " + seasonId;
    }

    public String getEpisodeWriter(Request req, Response res){
        String seasonId = req.params("season_id");
        String episodeId = req.params("episode_id");
        return "Get episode " + episodeId + " writer from season " + seasonId;
    }

    public String updateEpisode(Request req, Response res){
        String seasonId = req.params("season_id");
        String episodeId = req.params("episode_id");
        return "Update episode " + episodeId + " from season " + seasonId;
    }

    public String deleteEpisode(Request req, Response res){
        String seasonId = req.params("season_id");
        String episodeId = req.params("episode_id");
        return "Delete episode " + episodeId + " from season " + seasonId;
    }

}