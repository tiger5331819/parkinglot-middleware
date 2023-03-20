package com.yfkyplatform.parkinglotmiddleware.carpark.hongmen.client.domin.resp;

import lombok.Data;

/**
 * 红门基础返回结果
 *
 * @author Suhuyuan
 */
@Data
public class HongmenBaseResp<TResponseData> {

    /**
     * 请求类型，原样返回
     */
    private String type;

    /**
     * 表示请求操作是否成功，为'SUCCESS'表示与云平台的交互成功，为其它字符串时表示错误代码，客户端通过该错误代码识别错误类别
     */
    private String code;

    /**
     * 原请求 ID，原样返回
     */
    private String requestId;

    /**
     * 当 code 不为‘SUCCESS"时，可以对错误代码所表达的错误信息进行补充说明
     * <p>
     * SUCCESS 成功
     * ARGS_MISSING 缺少参数
     * ARGS_ERROR 参数错误 参数值不正确
     * AUTH_ERROR 认证失败 appId+sign 验证未通过
     * SIGN_ERROR 签名错误 request 请求中的 sign 属性不正确
     * TYPE_UNAUTHORIZED type 未授权 request 中的 type 未授权，禁止访问。
     * TYPE_NOT_FOUND 不存在的请求类型
     * request 中的 type 属性不正确
     * JSON_PARSE_ERROR json 格式错误
     * INTERNAL_ERROR 内部错误 服务器内部错误，当此类错发生时请再次请求，如果持续出现此类错误，请及时联系技术支持。
     * CALL_DENIED 调用被拒绝 调用某个接口次数过多，被限制调用。
     * APP_DISABLED APP 被禁用
     * LOCK_FAILED 获取锁失败 并发访问或请求过于频繁时，某项资源已被其它线程加锁处理，暂时不能访问，可以稍等片刻再试。
     * EXCEPTION 运行异常
     * RECORD_NOT_FOUND 记录未找到 未找到给定参数中的记录
     * NON_TEMP_VEHICLE 非临时车
     * PARKING_INTERNAL_ERROR 停车场内部处理错误 将指令下发到停车场时，停车场接口出现内部错误。
     * ORDER_EXCEPTION 订单异常 订单数据有异常
     * ORDER_REPEATED 订单重复 重复提交订单
     * ORDER_NOT_FOUND 未找到订单
     * VEHICLE_HAS_EXITED 车辆已出场
     * PARKING_OFFLINE 停车场不在线
     * TIMEOUT 超时 一般指接收方无应答导致超时的情况。
     * NO_PARKING_SPACE_LEFT 无剩余车位
     * PLATE_NO_UNBOUND 车牌未绑定 车牌未与相关服务（如无感支付）绑定、车牌未与手机号关联等等。
     */
    private String msg;

    /**
     * 数据
     */
    private TResponseData body;
}
