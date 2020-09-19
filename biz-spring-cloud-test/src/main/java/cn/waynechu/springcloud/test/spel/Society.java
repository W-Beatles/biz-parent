package cn.waynechu.springcloud.test.spel;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.*;

/**
 * @author zhuwei
 * @since 2019/3/26 13:20
 */
@Data
public class Society {
    private String name;
    public static String advisors = "advisors";
    public static String president = "president";
    private List<Inventor> members = new ArrayList<>();
    private Map officers = new HashMap();
}

@Data
@NoArgsConstructor
@AllArgsConstructor
class Inventor {
    private String name;
    private String nationality;
    private String[] inventions;
    private Date birthdate;
    private PlaceOfBirth placeOfBirth;

    public Inventor(String name, Date birthdate, String nationality) {
        this.name = name;
        this.birthdate = birthdate;
        this.nationality = nationality;
    }
}

@Data
class PlaceOfBirth {
    private String city;
    private String country;
}
