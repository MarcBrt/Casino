package com.casino.entity;

public enum Messages {

    LOGIN_SUCCESS("Utilisateur connecté"),
    LOGIN_ERROR("Utilisateur n'existe pas"),
    WRONG_PASSWORD("Mot de passe incorrect"),
    REGISTER_ERROR("Utilisateur déjà existant"),
    REGISTER_SUCCESS("Utilisateur enregistré");

    String msg;
    Messages(String msg)
    {
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }
}