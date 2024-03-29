package com.infnet.assessment;

import com.infnet.assessment.controllers.EpisodesController;
import com.infnet.assessment.controllers.SeasonsController;

import static spark.Spark.*;

public class Main {
    private static final int PORT = 8081;
    private static final SeasonsController seasonsController = new SeasonsController();
    private static final EpisodesController episodesController = new EpisodesController();

    public static void main(String[] args) {
        port(PORT);

        get("/", (req, res) -> "API is running on port " + PORT);

        path("/seasons", () -> {
            get("", seasonsController::listAllSeasons);

            get("/:season_id/episodes", seasonsController::listAllEpisodes);

            post("", seasonsController::createSeason);

            post("/:season_id/episodes", episodesController::createEpisode);

            delete("/:season_id", seasonsController::deleteSeason);

            put("/:season_id", seasonsController::updateSeason);

            path("/:season_id/episodes/:episode_id", () -> {
                get("", episodesController::getEpisode);

                get("/title", episodesController::getEpisodeTitle);

                get("/rating", episodesController::getEpisodeRating);

                get("/description", episodesController::getEpisodeDescription);

                get("/director", episodesController::getEpisodeDirector );

                get("/writer", episodesController::getEpisodeWriter);

                put("", episodesController::updateEpisode);

                delete("", episodesController::deleteEpisode);
            });
        });
    }
}
