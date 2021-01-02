package de.schuwue.themovies.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.util.UriUtils;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;


@Entity
public class Movie {

    @Id
    private int id;
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(pattern="yyyy-MM-dd' 'HH:mm:ss")
    private Date createdAt;
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(pattern="yyyy-MM-dd' 'HH:mm:ss")
    private Date updatedAt;
    private String filename;
    private String subtitleFilename;
    private int tmdbId;
    private String format;
    private int bitrate;
    private double duration;
    private String videoCodec;
    private int videoWidth;
    private int videoHeight;
    private String audioCodec;
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(pattern="yyyy-MM-dd' 'HH:mm:ss")
    private Date watched;

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public void setWatched(Date watched) {
        this.watched = watched;
    }

    public Movie() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getSubtitleFilename() {
        return subtitleFilename;
    }

    public void setSubtitleFilename(String subtitleFilename) {
        this.subtitleFilename = subtitleFilename;
    }

    public int getTmdbId() {
        return tmdbId;
    }

    public void setTmdbId(int tmdbId) {
        this.tmdbId = tmdbId;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public int getBitrate() {
        return bitrate;
    }

    public void setBitrate(int bitrate) {
        this.bitrate = bitrate;
    }

    public double getDuration() {
        return duration;
    }

    public void setDuration(double duration) {
        this.duration = duration;
    }

    public String getVideoCodec() {
        return videoCodec;
    }

    public void setVideoCodec(String videoCodec) {
        this.videoCodec = videoCodec;
    }

    public int getVideoWidth() {
        return videoWidth;
    }

    public void setVideoWidth(int videoWidth) {
        this.videoWidth = videoWidth;
    }

    public int getVideoHeight() {
        return videoHeight;
    }

    public void setVideoHeight(int videoHeight) {
        this.videoHeight = videoHeight;
    }

    public String getAudioCodec() {
        return audioCodec;
    }

    public void setAudioCodec(String audioCodec) {
        this.audioCodec = audioCodec;
    }

    @Override
    public String toString() {
        return "Movie{" +
                "id=" + id +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", filename='" + filename + '\'' +
                ", subtitleFilename='" + subtitleFilename + '\'' +
                ", tmdbId=" + tmdbId +
                ", format='" + format + '\'' +
                ", bitrate=" + bitrate +
                ", duration=" + duration +
                ", videoCodec='" + videoCodec + '\'' +
                ", videoWidth=" + videoWidth +
                ", videoHeight=" + videoHeight +
                ", audioCodec='" + audioCodec + '\'' +
                ", watched=" + watched +
                '}';
    }

    public URL getDownloadUrl() {
        URL downloadUrl = null;
        final String baseUrl = ServletUriComponentsBuilder.fromCurrentContextPath().build().toUriString();
        try {
            downloadUrl = new URL(baseUrl + "/files/" + UriUtils.encodePath(filename, "UTF-8"));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return downloadUrl;
    }
}
