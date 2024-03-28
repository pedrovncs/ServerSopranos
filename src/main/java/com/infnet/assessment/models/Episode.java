package com.infnet.assessment.models;

public class Episode {
        private int episode_number;
        private String title;
        private String director;
        private String writer;
        private String description;
        private double imdb_rating;

        public Episode(int episode_number, String title, String director, String writer, String description, double imdb_rating) {
            this.episode_number = episode_number;
            this.title = title;
            this.director = director;
            this.writer = writer;
            this.description = description;
            this.imdb_rating = imdb_rating;
        }

    public int getEpisodeNumber() {
        return episode_number;
    }

    public void setEpisodeNumber(int episode_number) {
        this.episode_number = episode_number;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getWriter() {
        return writer;
    }

    public void setWriter(String writer) {
        this.writer = writer;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getRating() {
        return imdb_rating;
    }

    public void setRating(double imdb_rating) {
        this.imdb_rating = imdb_rating;
    }
}
