package com.foxrain.sheep.whileblack.config.custom.security.oauth2.user.entity;

import com.foxrain.sheep.whileblack.config.configuration.security.oauth2.AuthProvider;
import javax.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Created with intellij IDEA.
 * by 2020 12 2020/12/19 3:55 오후 19
 * User we at 15 55
 * To change this template use File | Settings | File Templates.
 * @author foxrain
 */
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class User {
    @NotNull(message = "userIdx cannot be null.")
    private long userIdx;
    private String userKakaoID;
    private String userName;
    private String userImage;

    private String password;
    private AuthProvider provider;
    private String providerId;
    private String userEmail;

    public long getKakaoIdAsLong() {
        return Long.parseLong(this.getUserKakaoID());
    }

    public void setProviderId(String id) {
        this.providerId = id;
    }
}
