package repository;

import com.infnet.assessment.models.Season;
import com.infnet.assessment.models.Episode;
import com.infnet.assessment.repository.ApiRepository;
import org.junit.jupiter.api.*;


import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Episode Controller Tests")
public class RepositoryTest {

    private ApiRepository repo;

    @BeforeEach
    void setUp() {
        repo = ApiRepository.getInstance();
        repo.reloadRepository();
    }

    @Test
    @DisplayName("should return a list of seasons")
    void testGetSeasons() {
        List<Season> seasons = repo.getSeasons();
        assertNotNull(seasons);
        assertFalse(seasons.isEmpty());
    }

    @Test
    @DisplayName("should return a season by number")
    void testGetSeason() {
        Season season = repo.getSeason(1);
        assertNotNull(season);
        assertEquals(1, season.getSeason());
    }

    @Test
    @DisplayName("should return a list of episodes by season number")
    void testGetSeasonEpisodes() {
        List<Season> seasons = repo.getSeasons();
        assertNotNull(seasons);
        assertFalse(seasons.isEmpty());
    }
    @Test
    @DisplayName("should return null, invalid season")
    void testGetSeasonEpisodesInvalid() {
        Season season = repo.getSeason(99);
        assertNull(season);
    }

    @Test
    @DisplayName("should return a episode")
    void testGetEpisode(){
        Episode episode = repo.getEpisode(1, 1);
        assertNotNull(episode);
        assertEquals("The Sopranos", episode.getTitle());
    }

    @Test
    @DisplayName("should return null, invalid episode")
    void testGetEpisodeInvalid(){
        Episode episode = repo.getEpisode(1, 99);
        assertNull(episode);
    }

    @Test
    @DisplayName("should return the new season")
    void testAddSeason(){
        List<Episode> episodes = new ArrayList<>();
        Season season = new Season(99 , episodes);
        Season added = repo.addSeason(season);
        assertNotNull(added);
        assertEquals(99, added.getSeason());
    }

    @Test
    @DisplayName("should return the new episode")
    void testAddEpisode(){
        Episode episode = new Episode(99, "title", "director", "writer", "description", 9.9);
        Episode added = repo.addEpisode(1, episode);
        assertNotNull(added);
        assertEquals(99, added.getEpisodeNumber());
    }

    @Test
    @DisplayName("should return the updated episode")
    void testUpdateEpisode(){
        Episode episode = new Episode(1, "title - updated ", "director - updated", "writer - updated", "description - updated", 9.9);
        Episode updated = repo.updateEpisode(1, 1, episode);
        assertNotNull(updated);
        assertEquals("director - updated", updated.getDirector());
    }

    @Test
    @DisplayName("should return null, invalid episode")
    void testUpdateEpisodeInvalid(){
        Episode episode = new Episode(1, "title - updated ", "director - updated", "writer - updated", "description - updated", 9.9);
        Episode updated = repo.updateEpisode(1, 99, episode);
        assertNull(updated);
    }

    @Test
    @DisplayName("should return the deleted episode")
    void testDeleteEpisode(){
        Episode episode = repo.deleteEpisode(1, 1);
        assertNotNull(episode);
        assertNull(repo.getEpisode(1, 1));
    }

    @Test
    @DisplayName("should true, already exists a episode 1 in season 1")
    void testIsDuplicatedEpisode(){
        Episode episode = new Episode(1, "title", "director", "writer", "description", 9.9);
        assertTrue(repo.isDuplicatedEpisode(repo.getSeason(1), episode));
    }

    @Test
    @DisplayName("should false, episode 99 does not exist in season 1")
    void testIsDuplicatedEpisodeFalse(){
        Episode episode = new Episode(99, "title", "director", "writer", "description", 9.9);
        assertFalse(repo.isDuplicatedEpisode(repo.getSeason(1), episode));
    }

    @Test
    @DisplayName("should return the episode specific info")
    void testGetEpisodeInfoString(){
        Episode episode = repo.getEpisode(1, 1);
        String title = repo.getEpisodeInfoString(episode, "title");
        assertNotNull(title);
        assertEquals("The Sopranos", title);
        String rating = repo.getEpisodeInfoString(episode, "imdb_rating");
        assertNotNull(rating);
        assertEquals("9.2", rating);
        String description = repo.getEpisodeInfoString(episode, "description");
        assertNotNull(description);
        assertEquals("Tony Soprano, a mob boss, seeks therapy with Dr. Jennifer Melfi after suffering from panic attacks, while dealing with mob-related issues and family conflicts.", description);
        String director = repo.getEpisodeInfoString(episode, "director");
        assertNotNull(director);
        assertEquals("David Chase", director);
        String writer = repo.getEpisodeInfoString(episode, "writer");
        assertNotNull(writer);
        assertEquals("David Chase", writer);
    }
}


