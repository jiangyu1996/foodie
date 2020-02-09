package cn.decentchina.controller.center;

import cn.decentchina.CenterUserService;
import cn.decentchina.CommonConstant;
import cn.decentchina.UserService;
import cn.decentchina.bo.center.CenterUserBO;
import cn.decentchina.config.UploadFaceConfig;
import cn.decentchina.entity.SimpleMessage;
import cn.decentchina.exception.ErrorCodeException;
import cn.decentchina.pojo.User;
import cn.decentchina.utils.CookieUtils;
import com.alibaba.fastjson.JSONObject;
import lombok.Cleanup;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.stream.Collectors;

/**
 * @author jiangyu
 * @date 2020/1/30
 */
@RestController
@RequestMapping("userInfo")
public class CenterUserController {

    @Autowired
    private CenterUserService centerUserService;
    @Autowired
    private UploadFaceConfig uploadFaceConfig;
    @Autowired
    private UserService userService;

    private final static String[] FILE_PATTERN = {".png", ".jpg", ".jpeg"};

    @PostMapping("update")
    public SimpleMessage update(@RequestParam String userId, @Valid @RequestBody CenterUserBO centerUserBO,
                                BindingResult bindingResult, HttpServletRequest request, HttpServletResponse response) {
        if (bindingResult.hasErrors()) {
            String errorInfo = bindingResult.getAllErrors().stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.joining(","));
            throw new ErrorCodeException(errorInfo);
        }
        User user = centerUserService.update(userId, centerUserBO);
        setNullProperty(user);
        CookieUtils.setCookie(request, response, CommonConstant.USER, JSONObject.toJSONString(user), true);
        return new SimpleMessage(user);
    }

    @PostMapping("uploadFace")
    public SimpleMessage uploadFace(@RequestParam String userId, MultipartFile file,
                                    HttpServletRequest request, HttpServletResponse response) throws IOException {
        if (file == null) {
            throw new ErrorCodeException("头像文件不能为空");
        }
        // 定义头像保存的地址
        String uploadPath = uploadFaceConfig.getUploadUrl() + File.separator + userId;
        // 在路径上为每一个用户增加一个userId，用于区分不同用户上传
        // 开始文件上传
        @Cleanup FileOutputStream fileOutputStream = null;
        // 获得文件上传的文件名称
        String fileName = file.getOriginalFilename();
        if (StringUtils.isNotBlank(fileName)) {
            // 获取文件的后缀名
            String suffix = StringUtils.substring(fileName, StringUtils.lastIndexOf(fileName, "."));
            if (!ArrayUtils.contains(FILE_PATTERN, suffix)) {
                throw new ErrorCodeException("图片格式不正确");
            }
            // 上传的头像最终保存的位置
            String finalFacePath = uploadPath + File.separator + fileName;
            // 用于提供给web服务访问的地址
            // uploadPathPrefix += ("/" + newFileName);
            File outFile = new File(finalFacePath);
            if (!outFile.getParentFile().exists()) {
                // 创建文件夹
                outFile.getParentFile().mkdirs();
            }
            // 文件输出保存到目录
            fileOutputStream = new FileOutputStream(outFile);
            @Cleanup InputStream inputStream = file.getInputStream();
            IOUtils.copy(inputStream, fileOutputStream);
        }
        String serverImgUrl = uploadFaceConfig.getServerImgUrl() + File.separator + userId + File.separator + fileName + "?" + System.currentTimeMillis();
        User user = userService.updateFaceUrl(userId, serverImgUrl);
        setNullProperty(user);
        CookieUtils.setCookie(request, response, CommonConstant.USER, JSONObject.toJSONString(user), true);
        return new SimpleMessage();
    }

    private void setNullProperty(User user) {
        user.setPassword(null);
        user.setRealname(null);
        user.setBirthday(null);
        user.setEmail(null);
        user.setCreatedTime(null);
        user.setUpdatedTime(null);
    }
}
