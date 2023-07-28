package com.jty.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 *
 * </p>
 *
 * @author OR
 * @since 2023-06-03
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@TableName("token")
@Schema(description = "Token")
public class Token implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "id")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @Schema(description = "token")
    private String token;

    @Schema(description = "token type")
    private TokenType tokenType = TokenType.BEARER;

    @Schema(description = "revoked")
    private Boolean revoked;

    @Schema(description = "expired")
    private Boolean expired;

    @Schema(description = "user id")
    private Long userId;
}
