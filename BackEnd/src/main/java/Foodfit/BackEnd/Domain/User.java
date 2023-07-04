package Foodfit.BackEnd.Domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "users")
@NoArgsConstructor
@Getter
public class User {


    @Builder
    public User(Long id, String name, int age, Gender gender, Long uid, byte[] profileImage) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.uid = uid;
        this.profileImage = profileImage;
    }


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(nullable = false)
    private String name;

    private int age;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    // oAuth2에서 DB에 중복 저장 체크를 막기 위해 제공하는 id값
    private Long uid;


    @Lob
    @Column(nullable = true)
    private byte[] profileImage;

    public String getProfileImage() {
        if (profileImage != null) {
            return new String(profileImage);
        } else {
            return ""; // 또는 null 값을 대체할 다른 기본값을 반환할 수도 있습니다.
        }
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }
}

