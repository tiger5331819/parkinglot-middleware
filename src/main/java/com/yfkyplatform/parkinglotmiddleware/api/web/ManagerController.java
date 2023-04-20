package com.yfkyplatform.parkinglotmiddleware.api.web;

import cn.hutool.core.util.StrUtil;
import com.yfkyplatform.parkinglotmiddleware.api.manager.IManagerService;
import com.yfkyplatform.parkinglotmiddleware.api.manager.response.ParkingLotCfgRpcResp;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 测试控制器
 *
 * @author Suhuyuan
 */
@Slf4j
@Api(tags = {"停车场管理控制器"})
@RequestMapping(value = "api")
@RestController
public class ManagerController {

    private final IManagerService managerService;

    public ManagerController(IManagerService managerService){
        this.managerService=managerService;
    }

    @ApiOperation(value = "获取支持的管理器列表")
    @GetMapping("/manager/support")
    public Set<Integer> getManagerSupport() {
        return managerService.managerSupport();
    }

    @ApiOperation(value = "获取所有配置文件")
    @GetMapping("/manager/configuration")
    public List<ParkingLotCfgRpcResp> getAllManagerConfiguartion(String parkingLotName) {
        List<ParkingLotCfgRpcResp> rpcRespList = managerService.parkingMangerConfiguration(null, null);
        if (StrUtil.isNotBlank(parkingLotName)) {
            return rpcRespList.stream().filter(item -> item.getDescription().contains(parkingLotName)).collect(Collectors.toList());
        } else {
            return rpcRespList;
        }

    }

    @ApiOperation(value = "获取指定停车场管理的所有停车场配置文件")
    @GetMapping("/{parkingLotManager}/configuration")
    public List<ParkingLotCfgRpcResp> getManagerConfiguartion(@PathVariable Integer parkingLotManager) {
        return managerService.parkingMangerConfiguration(parkingLotManager, null);
    }

    @ApiOperation(value = "获取指定停车场管理的指定停车场配置文件")
    @GetMapping("/{parkingLotManager}/{parkingLotId}/configuration")
    public List<ParkingLotCfgRpcResp> getManagerConfiguartion(@PathVariable Integer parkingLotManager, @PathVariable String parkingLotId) {
        return managerService.parkingMangerConfiguration(parkingLotManager, parkingLotId);
    }

    @ApiOperation(value = "获取指定停车场配置文件")
    @GetMapping("/manager/{parkingLotId}/configuration")
    public List<ParkingLotCfgRpcResp> getParkingLotConfiguartion(@PathVariable String parkingLotId) {
        return managerService.parkingMangerConfiguration(null, parkingLotId);
    }

    @ApiOperation(value = "所有健康检查")
    @GetMapping("/manager/healthcheck")
    public Map<Integer, Map<Long, Boolean>> getAllManagerhealthCheck() {
        return managerService.parkingManagerHealthCheck(null, null);
    }

    @ApiOperation(value = "指定停车场健康检查")
    @GetMapping("/manager/{parkingLotId}/healthcheck")
    public Map<Integer, Map<Long, Boolean>> getParkingLotHealthCheck(@PathVariable String parkingLotId) {
        return managerService.parkingManagerHealthCheck(null, parkingLotId);
    }

    @ApiOperation(value = "指定停车场健康检查")
    @GetMapping("/{parkingLotManager}/{parkingLotId}/healthcheck")
    public Map<Integer, Map<Long, Boolean>> getManagerHealthCheck(@PathVariable Integer parkingLotManager, @PathVariable String parkingLotId) {
        return managerService.parkingManagerHealthCheck(parkingLotManager, parkingLotId);
    }

    @ApiOperation(value = "指定停车场管理健康检查")
    @GetMapping("/{parkingLotManager}/healthcheck")
    public Map<Integer, Map<Long, Boolean>> getManagerHealthCheck(@PathVariable Integer parkingLotManager) {
        return managerService.parkingManagerHealthCheck(parkingLotManager, null);
    }
}
