package com.kamixiya.icm.controller.crpto;

import com.kamixiya.icm.core.crypto.RsaUtil;
import com.kamixiya.icm.model.common.SimpleDataDTO;
import com.kamixiya.icm.service.common.service.crypto.RsaService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.validation.constraints.NotNull;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Map;

/**
 * @author Zhu Jie
 * @date 2020/4/5
 */
@Api(
    tags = {"密钥管理"}
)
@RestController
@RequestMapping({"/api/crypt"})
@Validated
public class RsaController {
    private final RsaService rsaService;

    @Autowired
    public RsaController(RsaService rsaService) {
        this.rsaService = rsaService;
    }

    @ApiOperation("根据客户端ID，生成公钥")
    @GetMapping({"/key"})
    public SimpleDataDTO<String> getRsaPublicKey(@ApiParam(value = "客户端ID",required = true) @RequestParam @NotNull String clientId) throws NoSuchAlgorithmException {
        Map<String, Key> keyMap = this.rsaService.getRsaKeyMap(clientId);
        return new SimpleDataDTO(RsaUtil.getPublicKey(keyMap));
    }

    @ApiOperation("取数据加密的KEY，用于传输数据的加解密")
    @PostMapping({"/key"})
    public SimpleDataDTO<String> getAesEncryptKey(@ApiParam(value = "客户端ID",required = true) @RequestParam @NotNull String clientId, @ApiParam(value = "客户端公钥",required = true) @RequestBody SimpleDataDTO<String> clientPublicKey) throws NoSuchAlgorithmException, IllegalBlockSizeException, InvalidKeyException, BadPaddingException, InvalidKeySpecException, NoSuchPaddingException {
        Map<String, Key> keyMap = this.rsaService.getRsaKeyMap(clientId);
        String clientPubKey = RsaUtil.decryptByPrivateKey((String)clientPublicKey.getData(), RsaUtil.getPrivateKey(keyMap));
        String aesKey = this.rsaService.getAesKey(clientId);
        return new SimpleDataDTO(RsaUtil.encryptByPublicKey(aesKey, clientPubKey));
    }
}
