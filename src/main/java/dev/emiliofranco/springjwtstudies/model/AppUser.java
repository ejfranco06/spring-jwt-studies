package dev.emiliofranco.springjwtstudies.model;


import lombok.*;


@ToString
@Getter
@AllArgsConstructor
@RequiredArgsConstructor
public class AppUser {
    private int id;

    @NonNull
    private String userName;
    @NonNull
    private String password;
}
