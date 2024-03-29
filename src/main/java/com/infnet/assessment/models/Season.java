package com.infnet.assessment.models;

import java.util.List;

public class Season {
    private int season;

    private List<Episode> episodes;

    public Season(int season, List<Episode> episodes) {
        this.season = season;
        this.episodes = episodes;
    }

    public int getSeason() {
        return this.season;
    }

    public void setSeason(int season_number) {
        this.season = season_number;
    }

    public List<Episode> getEpisodes() {
        return episodes;
    }

    public voidsetEpisodes(List<Episode> episodes) {
        this.episodes = episodes;
    }
}
