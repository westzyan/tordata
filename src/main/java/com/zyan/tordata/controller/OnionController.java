package com.zyan.tordata.controller;


import com.zyan.tordata.result.Result;
import com.zyan.tordata.service.OnionServiceTrafficService;
import com.zyan.tordata.service.OnionUniqueAddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

@Controller
@RequestMapping(value = "/onion")
public class OnionController {
    @Autowired
    private OnionServiceTrafficService onionServiceTrafficService;
    @Autowired
    private OnionUniqueAddressService onionUniqueAddressService;

    @RequestMapping("/fillOnionServiceTraffic")
    @ResponseBody
    public Result<Integer> fillOnionServiceTraffic() throws NoSuchAlgorithmException, KeyManagementException {
        int number = onionServiceTrafficService.fillOnionServiceTraffic();
        return Result.success(number);
    }

    @RequestMapping("/fillOnionUniqueAddress")
    @ResponseBody
    public Result<Integer> fillOnionUniqueAddress() throws NoSuchAlgorithmException, KeyManagementException {
        int number = onionUniqueAddressService.fillOnionUniqueAddress();
        return Result.success(number);
    }
}
