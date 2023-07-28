package com.delete.blogg.vo.params;

import com.delete.blogg.vo.Result;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginParams {
    private String account;
    private String password;
    private String nickname;
}
