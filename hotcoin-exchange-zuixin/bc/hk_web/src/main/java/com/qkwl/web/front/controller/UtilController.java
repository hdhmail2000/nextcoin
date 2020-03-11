package com.qkwl.web.front.controller;

import com.qkwl.common.Enum.validate.PlatformEnum;
import com.qkwl.common.framework.validate.ValidateHelper;
import com.qkwl.common.util.ReturnResult;
import com.qkwl.web.front.controller.base.JsonBaseController;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Base64;
import java.util.logging.Logger;

@Controller
public class UtilController extends JsonBaseController {
    @Autowired
    private ValidateHelper validateHelper;

    @ResponseBody
    @RequestMapping("/v1/mail")
    public ReturnResult mail(
            @RequestParam(value = "mail", required = true) String mail,
            @RequestParam(value = "title", required = true) String title,
            @RequestParam(value = "context", required = true) String context) throws Exception {
        try {
            byte[] asBytes  = Base64.getDecoder().decode(context);
            if (validateHelper.mailDrawBits(mail, PlatformEnum.BC, new String(asBytes, "utf-8"), title)) {
                return ReturnResult.SUCCESS(GetR18nMsg("common.succeed.200"));
            }
        } catch (Exception e) {
            System.out.println("error UtilController::mail");
        }
        return ReturnResult.FAILUER(GetR18nMsg("common.error.400"));
    }
}
