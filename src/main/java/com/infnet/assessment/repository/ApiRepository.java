package com.infnet.assessment.repository;
import com.google.gson.Gson;

import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;

import com.google.gson.reflect.TypeToken;
import com.infnet.assessment.models.Episode;
import com.infnet.assessment.models.Season;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ApiRepository {
    private static ApiRepository instance;
    private List<Season> seasons;

    private ApiRepository(){
        this.seasons = loadSeasonsFromFile();
    }

    private @Nullable List<Season> loadSeasonsFromFile() {
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

    public List<Episode> getAllEpisodes(int seasonNumber) {
        Season season = getSeason(seasonNumber);
        if (season == null) {
            return null;
        }
        return season.getEpisodes();
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
        return season.getEpisodes().stream().filter(episode -> episode.getEpisodeNumber() == episodeNumber).findFirst().orElse(null);
    }

    public Season addSeason(Season season) {
        seasons.add(season);
        return season;
    }

    public Episode addEpisode(int seasonNumber, Episode episode) {
        Season season = getSeason(seasonNumber);
        if (season != null) {
            season.getEpisodes().add(episode);
        }
        return episode;
    }

    public void updateEpisode(int seasonNumber, Episode episode) {
        Season season = getSeason(seasonNumber);
        if (season != null) {
            Episode episodeToUpdate = season.getEpisodes().stream().filter(e -> e.getEpisodeNumber() == episode.getEpisodeNumber()).findFirst().orElse(null);
            if (episodeToUpdate != null) {
                episodeToUpdate.setTitle(episode.getTitle());
                episodeToUpdate.setDirector(episode.getDirector());
                episodeToUpdate.setWriter(episode.getWriter());
                episodeToUpdate.setDescription(episode.getDescription());
                episodeToUpdate.setRating(episode.getRating());
            }
        }
    }

    public void deleteEpisode(int seasonNumber, int episodeNumber) {
        Season season = getSeason(seasonNumber);
        if (season != null) {
            season.getEpisodes().removeIf(episode -> episode.getEpisodeNumber() == episodeNumber);
        }
    }

   public void checkDuplicatedEpisode(Episode episode) {
        for (Season season : seasons) {
            for (Episode e : season.getEpisodes()) {
                if (e.getEpisodeNumber() == episode.getEpisodeNumber()) {
                    throw new IllegalArgumentException("Episode already exists.");
                }
            }
        }
    }
}
