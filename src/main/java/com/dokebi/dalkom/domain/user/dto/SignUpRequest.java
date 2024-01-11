package com.dokebi.dalkom.domain.user.dto;



import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignUpRequest {
    @NotBlank(message = "사용할 id를 입력해주세요.")
    private String userId;

    @NotBlank (message = "비밀번호를 입력해주세요.")
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,}$",
            message = "비밀번호는 최소 8자리이면서 1개 이상의 알파벳,숫자,특수문자를 포함해야 한다.")
    private String password;

    @Email(message = "이메일 형식을 맞춰주세요.")
    @NotBlank(message = "이메일을 입력해주세요.")
    private String email;

    @NotBlank(message = "사용자 닉네임을 입력해주세요.")
    @Size(min = 2, message = "사용자 닉네임이 너무 짧습니다.")
    @Pattern(regexp = "^[A-Za-z가-힣]+$", message = "닉네임은 한글 또는 알파벳만 입력해주세요.")
    private String nickname;

    @NotBlank(message = "프로필 이미지를 넣어주세요.")
    private String profileImgSeq;

    // public static User toEntity(SignUpRequest req, PasswordEncoder encoder) {
    //     return new User(req.userId, encoder.encode(req.password), req.nickname, req.email, req.profileImgSeq);
    // }
}
