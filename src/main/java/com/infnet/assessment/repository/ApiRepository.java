package com.infnet.assessment.repository;
import com.google.gson.Gson;

import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;

import com.google.gson.reflect.TypeToken;
import com.infnet.assessment.models.Episode;
import com.infnet.assessment.models.Season;
import spark.Request;
import spark.Response;

import java.util.List;

public class ApiRepository {
    private static ApiRepository instance;
    private List<Season> seasons;

    private ApiRepository(){
        this.seasons = loadSeasonsFromFile();
    }

    private List<Season> loadSeasonsFromFile() {
        Gson gson = new Gson();
        try {
            FileReader fileReader = new FileReader("src/main/resources/data.json");
            Type seasonListType = new TypeToken<List<Season>>() {}.getType();
            return gson.fromJson(fileReader, seasonListType);
        } catch (IOException e) {
            return null;
        }
    }

    public static ApiRepository getInstance() {
        if (instance == null) {
            instance = new ApiRepository();
        }
        return instance;
    }

    public List<Season> getSeasons() {
        return seasons;
    }

    public Season getSeason(int seasonNumber) {
        for (Season season : seasons) {
            if (season.getSeason() == seasonNumber) {
                return season;
            }
        }
        return null;
    }

    public List<Episode> getSeasonEpisodes(int seasonNumber) {
        Season season = getSeason(seasonNumber);
        if (season == null) {
            return null;
        }
        return season.getEpisodes();
    }

    public Episode getEpisode(int seasonNumber, int episodeNumber) {
        Season season = getSeason(seasonNumber);
        if (season == null) {
            return null;
        }

        for (Episode episode : season.getEpisodes()) {
            if (episode.getEpisodeNumber() == episodeNumber) {
                return episode;
            }
        }

        return null;
    }

    public Season addSeason(Season season) {
        if (season == null || isDuplicatedSeason(season)) {
            return null;
        }
        seasons.add(season);
        return season;
    }

    public Episode addEpisode(int seasonNumber, Episode episode) {
        Season season = getSeason(seasonNumber);
        if (season == null || isDuplicatedEpisode(season, episode)) {
            return null;
        }

        season.getEpisodes().add(episode);
        return episode;
    }

    public Episode updateEpisode(int season, int episode, Episode newEpisode) {
        Episode oldEpisode = getEpisode(season, episode);
        if (oldEpisode == null) {
            return null;
        }

        oldEpisode.updateEpisode(newEpisode);

        return newEpisode;
    }

    public Episode deleteEpisode(int seasonNumber, int episodeNumber) {
        Season season = getSeason(seasonNumber);
        if ( season == null) {
            return null;
        }

        Episode episode = getEpisode(seasonNumber, episodeNumber);
        if (episode == null) {
            return null;
        }

        season.getEpisodes().remove(episode);
        return episode;
    }

   public Boolean isDuplicatedEpisode(Season season, Episode episode) {
        for (Episode e : season.getEpisodes()) {
            if (e.getEpisodeNumber() == episode.getEpisodeNumber()) {
                return true;
            }
        }
        return false;
    }

    public Boolean isDuplicatedSeason(Season season) {
        for (Season s : seasons) {
            if (s.getSeason() == season.getSeason()) {
                return true;
            }
        }
        return false;
    }

    public String getEpisodeInfoString(Episode episode, String info) {
        return switch (info) {
            case "title" -> episode.getTitle();
            case "imdb_rating" -> Double.toString(episode.getRating());
            case "description" -> episode.getDescription();
            case "director" -> episode.getDirector();
            case "writer" -> episode.getWriter();
            default -> "Invalid info.";
        };
    }

    public String getFromParams(Request req, Response res, String info) {
        var season_num = Integer.parseInt(req.params("season_id"));
        var episode_num = Integer.parseInt(req.params("episode_id"));

        Episode episode = getEpisode(season_num, episode_num);
        if (episode == null) {
            res.status(404);
            return "Episode not found.";
        }
        return getEpisodeInfoString(episode, info);
    }

    public void reloadRepository() {
        this.seasons = loadSeasonsFromFile();
    }
}
