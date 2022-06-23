package com.yfkyplatform.parkinglotmiddleware.api.web;

import com.yfkyplatform.parkinglotmiddleware.api.manager.configuration.IManagerConfigurationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

/**
 * 测试控制器
 *
 * @author Suhuyuan
 */
@Slf4j
@Api(tags = {"停车场管理配置控制器"})
@RequestMapping(value = "api")
@RestController
public class ManagerConfigurationController {

    IManagerConfigurationService managerConfigurationService;

    public ManagerConfigurationController(IManagerConfigurationService managerConfigurationService){
        this.managerConfigurationService=managerConfigurationService;
    }

    @ApiOperation(value = "添加道尔停车场配置文件")
    @PostMapping("/Daoer/Configuration")
    public Boolean addDaoerConfiguration(Long id, String appName, String parkId, String baseUrl, String description) {
        return managerConfigurationService.addDaoerCongfiguration(id, appName, parkId, baseUrl, description);
    }

    @ApiOperation(value = "修改道尔停车场配置文件")
    @PutMapping("/Daoer/Configuration/{id}")
    public Boolean updateDoerConfiguration(@PathVariable Long id, String appName, String parkId, String baseUrl, String description) {
        return managerConfigurationService.addDaoerCongfiguration(id, appName, parkId, baseUrl, description);
    }
}
