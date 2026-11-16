package projet.dsi31.usersmicroservice.security;

public interface SecParams {
    long EXP_TIME = 10 * 24 * 60 * 60 * 1000;
    String SECRET = "nissaf.hass@gmail.com";
    String PREFIX = "Bearer ";
}

