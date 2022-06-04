package images.model;

import images.model.enums.ActivityType;
import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Table(name = "shop_banner_Activity_log")
@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ShopBannerActivityLog {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String shopId;
    private Timestamp timestamp;
    private String ipAddress;
    @Enumerated(EnumType.STRING)
    private ActivityType activityType;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        ShopBannerActivityLog that = (ShopBannerActivityLog) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
