package com.example.wael.mycart;

import android.net.Uri;

import com.google.firebase.firestore.GeoPoint;

public class Livreur {
    private String user_id;
    private String user_email;
    private String nom;
    private String prenom;
    private String cin;
    private Boolean disponible;
    private GeoPoint location;
    private int rating_times;
    private float rating_total;
    private String image;

    public Livreur(){}

    public Livreur(String userId,String userEmail, String nom, String prenom, String cin, Boolean disponible, GeoPoint location, int ratingTimes, float ratingTotal, String image) {
        this.user_id = userId;
        this.user_email = userEmail;
        this.nom = nom;
        this.prenom = prenom;
        this.cin = cin;
        this.disponible = disponible;
        this.location = location;
        this.rating_times = ratingTimes;
        this.rating_total = ratingTotal;
        this.image = image;
    }

    public String getUser_id() {
        return user_id;
    }

    public String getUser_email() { return user_email; }

    public void setUser_email(String user_email) { this.user_email = user_email; }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public int getRating_times() {
        return rating_times;
    }

    public void setRating_times(int rating_times) {
        this.rating_times = rating_times;
    }

    public float getRating_total() {
        return rating_total;
    }

    public void setRating_total(float rating_total) {
        this.rating_total = rating_total;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getCin() {
        return cin;
    }

    public void setCin(String cin) {
        this.cin = cin;
    }

    public Boolean getDisponible() {
        return disponible;
    }

    public void setDisponible(Boolean disponible) {
        this.disponible = disponible;
    }

    public GeoPoint getLocation() {
        return location;
    }

    public void setLocation(GeoPoint location) {
        this.location = location;
    }

    public float getRating() {
        return this.rating_total /this.rating_times;
    }

    public Uri getImage() {
        return Uri.parse(this.image);
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "Livreur{" +
                "user_id='" + user_id + '\'' +
                ", nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                ", cin='" + cin + '\'' +
                ", disponible=" + disponible +
                ", location=" + location +
                ", rating_times=" + rating_times +
                ", rating_total=" + rating_total +
                ", image='" + image + '\'' +
                '}';
    }
}
