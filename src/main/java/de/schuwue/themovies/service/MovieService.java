package de.schuwue.themovies.service;

import com.github.kokorin.jaffree.ffprobe.FFprobe;
import com.github.kokorin.jaffree.ffprobe.FFprobeResult;
import com.github.kokorin.jaffree.ffprobe.Stream;
import de.schuwue.themovies.entity.Movie;
import de.schuwue.themovies.repository.MovieRepository;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class MovieService {

    private final Path root = Paths.get("/Users/jschultewu/Movies/");

    @Autowired
    private MovieRepository movieRepository;

    @Transactional
    public String createMovie(Movie movie){
        try {
            if (!movieRepository.existsByTmdbId(movie.getTmdbId())){
                movie.setId(null == movieRepository.findMaxId() ? 0 : movieRepository.findMaxId() + 1);
                movieRepository.save(movie);
                return "Movie record created successfully.";
            } else {
                return "Movie already exists in the database.";
            }
        } catch (Exception e){
            throw e;
        }
    }

    public Page<Movie> readMovies(PageRequest pageRequest){
        return movieRepository.findAll(pageRequest);

    }

    public Movie showMovieByTmdbId(Integer tmdbId) {
        return movieRepository.findByTmdbId(tmdbId);
    }

    @Transactional
    public String updateMovie(Movie movie){
        if (movieRepository.existsById(movie.getId())){
            try {
                Movie movieToBeUpdate = movieRepository.findById(movie.getId()).get();
                movieToBeUpdate.setTmdbId(movie.getTmdbId());
                movieRepository.save(movieToBeUpdate);

                return "Movie record updated.";
            } catch (Exception e){
                throw e;
            }
        } else {
            return "Movie does not exists in the database.";
        }
    }

    @Transactional
    public String deleteMovie(Movie movie){
        if (movieRepository.existsById(movie.getId())){
            try {
                Movie movieToBeDeleted = movieRepository.findById(movie.getId()).get();
                movieRepository.delete(movieToBeDeleted);
                return "Movie record deleted successfully.";
            } catch (Exception e){
                throw e;
            }

        } else {
            return "Movie does not exist";
        }
    }

    @Transactional
    public String uploadMovie(InputStream uploadedStream, String foldername, String filename, int tmdbId) throws IOException {
        try {
            if (!movieRepository.existsByTmdbId(tmdbId)) {
                Path path = this.root.resolve(foldername);
                Files.createDirectory(path);

                OutputStream out = new FileOutputStream(path.resolve(filename).toString());
                IOUtils.copy(uploadedStream, out);

                Movie movie = new Movie();
                movie.setId(null == movieRepository.findMaxId() ? 0 : movieRepository.findMaxId() + 1);
                movie.setFilename(foldername + "/" + filename);
                movie.setTmdbId(tmdbId);

                FFprobe ffprobe = FFprobe.atPath();
                FFprobeResult result = ffprobe
                        .setShowStreams(true)
                        .setShowFormat(true)
                        .setInput(path.resolve(filename))
                        .execute();
                movie.setFormat(result.getFormat().getFormatName());
                movie.setBitrate(Math.toIntExact(result.getFormat().getBitRate()));
                movie.setDuration(result.getFormat().getDuration());
                for (Stream stream : result.getStreams()) {
                    if (stream.getCodecType().name().equals("VIDEO") && movie.getVideoCodec() == null) {
                        movie.setVideoCodec(stream.getCodecName());
                        movie.setVideoWidth(stream.getWidth());
                        movie.setVideoHeight(stream.getHeight());
                    }
                    if (stream.getCodecType().name().equals("AUDIO") && movie.getAudioCodec() == null) {
                        movie.setAudioCodec(stream.getCodecName());
                    }
                }

                movieRepository.save(movie);

                return "Movie record created successfully.";
            } else {
                return "Movie already exists in the database.";
            }
        } catch (IOException e) {
            throw e;
        }
    }
}
