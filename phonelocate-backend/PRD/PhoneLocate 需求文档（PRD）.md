# PhoneLocate 需求文档（PRD）

**项目名称：** PhoneLocate

**版本号：** v1.0

**发布日期：** 2024/4/27

**文档历史：**

| 版本号 | 日期      | 描述               | 作者   |
| ------ | --------- | ------------------ | ------ |
| v2.0   | 2024/4/30 | 创意性功能开发完成 | 许骞龙 |
| v1.0   | 2024/4/27 | 初稿完成           | 许骞龙 |

## 1. 项目概述：

PhoneLocate 旨在为前端提供手机号归属地和供应商信息的查询服务。该服务将通过调用第三方API获取手机号相关信息，并提供缓存、超时、重试和错误码字典等功能，以提高系统的稳定性、性能和用户体验。

## 2. 功能列表：

1. 查询手机号归属地信息
2. 查询手机号供应商信息

## 3. 功能描述：

### 3.1 查询手机号供应商信息：

- 输入：手机号前三位
- 输出：供应商信息（运营商）
- 描述：用户输入手机号前三位后，系统将查询该手机号的供应商信息，并返回给用户。

### 3.2 查询手机号归属地信息：

- 输入：手机号码
- 输出：归属地信息（省、市）
- 描述：用户输入手机号码后，点击查询键将查询该手机号码的归属地信息，并返回给用户。

### 3.3 *标记不良手机号*

- 输入：手机号码
- 描述：用户输入手机号码后，点击标记键可以选择将手机号标记为骚扰电话、诈骗电话或广告推销，系统将数据存储可展示被查询手机号的标记信息。
- 补充：为了防止用户恶意的多次标记同一手机号，可以选择登录账号之后才能标注并且同账号对相同的手机号标注的机会只有一次，**因时间原因暂未实现该功能，后期补充。**

### 3.4 *查询手机号被标记信息*

- 输入：手机号码
- 描述：用户输入手机号码后，点击查询键查询所属地信息的同是会查询该手机号被多少个用户标记为某种不良类型的手机号。

## 4. 非功能性描述：

### 4.1 入参检验：

- 描述：在前端请求之前进行参数验证，后端接收请求后再次进行参数校验以防止脏读等。

```js
// 入参检验
phoneRules: {
    phoneNumber: [
          { required: true, message: '请输入手机号', trigger: 'blur' },
          { pattern: /^1\d{10}$/, message: '手机号格式不正确', trigger: 'blur' },
          { pattern: /^[0-9]*$/, message: '手机号只能包含数字', trigger: 'change' }
        ]
      },

```

```java
// 参数校验
int code = phoneOperatorInfoService.validPhoneTopThreeNumber(phoneTopThreeNumber);
```

### 4.2 缓存机制：

- 描述：缓存查询过的手机号归属地和供应商信息，以减少重复查询和提高系统性能。

```java
// 先从Redis缓存中查找数据
PhoneOperatorInfo phoneOperatorInfo = (PhoneOperatorInfo) redisUtil.get(phoneTopThreeNumber);
```

### 4.3 超时机制：

- 描述：设置请求的超时时间，防止长时间等待响应，以提高系统的稳定性和用户体验。

```js
axios.get('http://localhost:8081/phone/getLocate', {
    params: {
          phoneNumber: this.formData.phoneNumber
        },
        // 设置超时时间为5秒
        timeout: 5000
      })
```

### **4.4 重试机制：**

- 描述：在查询失败时进行重试，以应对网络波动或第三方服务不稳定的情况，提高查询成功率。

```js
.catch(error => {
	// 请求失败
    console.error('Error:', error);

    if (retries > 0) {
    	// 如果重试次数大于0，则进行重试
        this.queryOperator(retries - 1);
    } else {
        console.error('Max retries exceeded');
    }
}
```

```java
// 通过 @Retryable 注解实现重试
@Retryable(value = {Exception.class}, maxAttempts = 3, backoff = @Backoff(delay = 1000))
```

### 4.5 错误码字典：	

- 描述：定义一套错误码字典，用于标识不同类型的错误，并提供相应的错误信息。

```java
/**
 * 错误码字典
 *
 */
public enum ErrorCode {

    SUCCESS(0, "ok"),
    PHONE_NUM_ERROR(40000, "请输入正确形式的手机号"),
    UNKNOWN_OPERATOR (40001, "未知运营商"),
    LOCATION_NOT_FOUND(40002, "未知归属地"),
    PHONE_NUMBER_NOT_NUMERIC(40003, "手机号只能包含数字"),
    PHONE_NUMBER_REQUIRED(40004, "手机号不能为空" );

    /**
     * 状态码
     */
    private final int code;

    /**
     * 信息
     */
    private final String message;

    ErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }
```

## 5. 用例场景：

- 用户输入手机号前三位，查询供应商信息
- 用户输入手机号码，查询归属地信息以及不良标记
- 用户输入手机号码，标注某手机号为不良
- 系统缓存查询结果，提高系统性能
- 系统设置超时时间，防止长时间等待响应
- 查询失败时进行重试，提高查询成功率

### 用例图

![](D:\project\手机号码归属地查询\phonelocate-backend\phonelocate-backend\PRD\uml\phonelocate用例图.png)

### 类图

![](D:\project\手机号码归属地查询\phonelocate-backend\phonelocate-backend\PRD\uml\plonelocate类图.png)

### 时序图

![](D:\project\手机号码归属地查询\phonelocate-backend\phonelocate-backend\PRD\uml\phonelocate时序图.png)

### 活动图

![](D:\project\手机号码归属地查询\phonelocate-backend\phonelocate-backend\PRD\uml\phonelocate活动图.png)

## 6. MySQL数据库设计

### 数据库架构

手机号被标记信息表**phone_marker_info** ，用于存储被标记的手机号信息

- `id`: 主键，用于唯一标识每条记录。
- `phone_number`: 手机号，用于标识被标记信息的手机号。
- `harassment_count`: 被标记为骚扰电话的次数。
- `fraud_count`: 被标记为诈骗电话的次数。
- `advertisement_count`: 被标记为广告推销的次数。
- `create_time`: 创建时间，记录每条记录的创建时间。

### 表设计

列出所有表，包括表名、字段、数据类型、约束等信息。

| 表名              | 字段                  | 数据类型     | 约束                      | 说明                   |
| ----------------- | --------------------- | ------------ | ------------------------- | ---------------------- |
| phone_marker_info | id (PK)               | BIGINT       | 主键                      | 主键                   |
|                   | phone_number (Unique) | VARCHAR(256) | 唯一约束                  | 手机号                 |
|                   | harassment_count      | BIGINT       | DEFAULT 0 NOT NULL        | 被标记为骚扰电话的次数 |
|                   | fraud_count           | BIGINT       | DEFAULT 0 NOT NULL        | 被标记为诈骗电话的次数 |
|                   | advertisement_count   | BIGINT       | DEFAULT 0 NOT NULL        | 被标记为广告推销的次数 |
|                   | create_time           | DATETIME     | DEFAULT CURRENT_TIMESTAMP | 创建时间               |



### 索引设计

- 索引1：phone_marker_info.id
- 索引2：phone_marker_info.phone_number

## 7. Redis缓存设计

### 缓存策略

- 缓存读取策略：当用户查询手机号地址和供应商时缓存手机号归属地和供应商数据。
- 缓存更新策略：缓存数据过期后再次查询时更新缓存。

### 缓存键设计

- 缓存键1：手机号的前三位数，值为手机号前三位对应的供应商 JSON  对象。
- 缓存键2：手机号，值为手机号的归属地 JSON  对象。

### 缓存过期策略

- 过期时间设置：24小时

  

## 8. 其他说明

数据库sql以及模拟信息

```mysql
use phone;

SET NAMES utf8;

CREATE TABLE IF NOT EXISTS phone.`phone_marker_info`
(
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键' PRIMARY KEY,
    `phone_number` VARCHAR(256) NOT NULL COMMENT '手机号',
    `harassment_count` BIGINT DEFAULT 0 NOT NULL COMMENT '被标记为骚扰电话的次数',
    `fraud_count` BIGINT DEFAULT 0 NOT NULL COMMENT '被标记为诈骗电话的次数',
    `advertisement_count` BIGINT DEFAULT 0 NOT NULL COMMENT '被标记为广告推销的次数',
    `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP NOT NULL COMMENT '创建时间',
    UNIQUE (`phone_number`),
    INDEX idx_phone_number (`phone_number`)
) COMMENT '手机号标注信息';



INSERT INTO phone.`marker_info` (`phone_number`, `harassment_count`, `fraud`, `advertisement`)
VALUES
    ('17735878881', 5, 25, 35),
    ('15697564307', 25, 10, 50),
    ('15340963494', 30, 20, 5),
    ('13206417142', 20, 30, 40),
    ('15736514295', 10, 5, 45),
    ('17032213646', 50, 40, 30),
    ('15393150004', 40, 15, 25),
    ('15259259992', 5, 50, 20),
    ('17522009147', 35, 25, 15),
    ('17506469657', 5, 25, 30),
    ('17509388900', 25, 40, 50),
    ('14738899978', 20, 50, 50);
```

